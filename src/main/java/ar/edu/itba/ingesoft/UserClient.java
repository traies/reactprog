package ar.edu.itba.ingesoft;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import org.eclipse.jetty.websocket.api.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.EncodeException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by traie_000 on 10/24/2017.
 */
public class UserClient implements Observer<Message> {
    private static final Logger logger = LoggerFactory.getLogger(UserClient.class);
    private List<Message> currentStrings = new ArrayList<>();
    private Session session;
    private String username;
    private int chatId;
    private Disposable disposable ;


    public UserClient(Session session,String username,int chatId){
        this.session = session;
        this.username = username;
        this.chatId = chatId;
    }

    @Override
    public void onSubscribe(@NonNull Disposable disposable) {
        this.disposable = disposable;

    }

    @Override
    public void onNext(@NonNull Message message) {
        currentStrings.add(message);
        try {
            ObjectMapper mapper = new ObjectMapper();
            String messageJson = mapper.writeValueAsString(message);
            session.getRemote().sendString(messageJson);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onError(@NonNull Throwable throwable) {

    }

    @Override
    public void onComplete() {

    }

    public List<Message> getMessages() {
        List<Message> ans = currentStrings;
        currentStrings = new ArrayList<>();
        logger.info(currentStrings.toString());
        return ans;
    }

    public Disposable getDisposable() {
        return disposable;
    }

    public String getUsername() {
        return username;
    }

    public int getChatId() {
        return chatId;
    }
}
