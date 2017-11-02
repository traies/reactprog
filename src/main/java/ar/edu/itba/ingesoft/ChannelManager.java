package ar.edu.itba.ingesoft;

import io.reactivex.Observable;
import io.reactivex.subjects.ReplaySubject;
import io.reactivex.subjects.Subject;
import org.eclipse.jetty.websocket.api.Session;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class ChannelManager {


    private static ChannelManager instance;

    private Map<Integer, Subject<Message>> channels;
    private Map<Integer, Observable<Message>> observables;
    private Map<Session, Integer> clientsChannels;
    private Map<Session,UserClient> clients;

    public  synchronized  static ChannelManager getInstance(){
        if (instance == null){
            instance = new ChannelManager();
        }
        return instance;

    }

    private ChannelManager(){
        channels = new HashMap<>();
        observables = new HashMap<>();

        Subject<Message> subject1 = ReplaySubject.create(5);
        channels.put(1, subject1);
        observables.put(1, subject1);

        Subject<Message> subject2 = ReplaySubject.create(5);
        channels.put(2, subject2);
        observables.put(2, subject2);

        Subject<Message> subject3 = ReplaySubject.create(5);
        channels.put(3, subject3);
        Observable<Message> observable3 = Observable.merge(subject1, subject2, subject3);
        observables.put(3, observable3);

        Subject<Message> subject4 = ReplaySubject.create(5);
        channels.put(4, subject4);
        Observable<Message> observable4 = Observable.merge(observable3, subject4)
                .map( x -> {
                    return new Message(x.getAuthor(), x.getText().toUpperCase());
                });
        observables.put(4, observable4);

        Subject<Message> subject5 = ReplaySubject.create(5);
        channels.put(5, subject5);
        Observable<Message> observable5 = Observable.merge(observable3, subject5)
                .filter( x ->
                        !x.getAuthor().toLowerCase().equals("ricardo")
                );
        observables.put(5, observable5);

        Subject<Message> subject6 = ReplaySubject.create(5);
        channels.put(6, subject6);
        Observable<Message> observable6 = Observable.merge(observable3, subject6)
                .map(x -> {
                    return new Message(x.getAuthor(), String.valueOf(x.getText().length()));
                });

        observables.put(6, observable6);

        Subject<Message> subject7 = ReplaySubject.create(5);
        channels.put(7, subject7);
        Observable<Message> observable7 = Observable.zip(subject1, subject2, (x, y) -> {
           return new Message(x.getAuthor() + ":" + y.getAuthor(), x.getText() + "::" + y.getText());
        });
        observables.put(7, observable7);

        clients = new HashMap<>();
        clientsChannels = new HashMap<>();
    }

    public Subject<Message> getChannel(int channelId){
        return Optional.ofNullable(channels.get(channelId)).orElseThrow(IllegalArgumentException::new);
    }

    public Observable<Message> getObservable(int channelId){
        return Optional.ofNullable(observables.get(channelId)).orElseThrow(IllegalArgumentException::new);
    }

    public Map<Session,UserClient> getClients(){
        return clients;
    }

    public Map<Session,Integer> getClientsChannels(){
        return clientsChannels;
    }


}
