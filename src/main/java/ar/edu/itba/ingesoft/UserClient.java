package ar.edu.itba.ingesoft;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by traie_000 on 10/24/2017.
 */
public class UserClient implements Observer<Message> {
    private static final Logger logger = LoggerFactory.getLogger(UserClient.class);
    private List<Message> currentStrings = new ArrayList<>();

    @Override
    public void onSubscribe(@NonNull Disposable disposable) {

    }

    @Override
    public void onNext(@NonNull Message message) {
        currentStrings.add(message);
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
}
