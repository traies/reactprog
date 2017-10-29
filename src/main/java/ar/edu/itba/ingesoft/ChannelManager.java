package ar.edu.itba.ingesoft;

import io.reactivex.subjects.ReplaySubject;
import io.reactivex.subjects.Subject;
import org.eclipse.jetty.websocket.api.Session;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ChannelManager {


    private static ChannelManager instance;

    private Map<Integer, Subject<Message>> channels;
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
        for( int i = 1; i < 11 ; i++){
            channels.put(i, ReplaySubject.create(5));
        }
        clients = new HashMap<>();
        clientsChannels = new HashMap<>();
    }

    public Subject<Message> getChannel(int channelId){
        return Optional.ofNullable(channels.get(channelId)).orElseThrow(IllegalArgumentException::new);
    }

    public Map<Session,UserClient> getClients(){
        return clients;
    }

    public Map<Session,Integer> getClientsChannels(){
        return clientsChannels;
    }


}
