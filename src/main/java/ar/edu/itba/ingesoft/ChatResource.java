package ar.edu.itba.ingesoft;

import io.reactivex.Observer;
import io.reactivex.subjects.ReplaySubject;
import io.reactivex.subjects.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.inject.Singleton;
import javax.print.attribute.standard.Media;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


/**
 * Created by traies on 10/23/2017.
 */

@Path("/")
@Singleton
public class ChatResource {
    private static final Logger logger = LoggerFactory.getLogger(ChatResource.class);
    private Map<Integer, Subject<Message>> channels;
    private Map<Integer, UserClient> clients;

    @PostConstruct
    public void init() {
        channels = new HashMap<>();
        Subject<Message> ch = ReplaySubject.create(5);
        channels.put(0, ch);
        for (int i = 0; i < 100; i++) {
            ch.onNext(new Message("gato", "jil"+i));
        }
        clients = new HashMap<>();
        for (int i = 0; i < 10; i++) {
            UserClient obs = new UserClient();
            ch.subscribe(obs);
            clients.put(i, obs);
        }

    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    public InputStream main() {
        try {
            logger.info(System.getProperty("user.dir"));
            File f = new File("resources/simple-page.html");
            return new FileInputStream(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    @GET
    @Path("chat")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Message> chat(@QueryParam("sessionid") int sessionid) {
        return Optional.ofNullable(clients.get(sessionid))
//                .map(x -> x.getMessages().stream().map(Message::toString).collect(Collectors.toList()))
//                .map(List::toString)
                .map(x -> x.getMessages())
                .orElseThrow(IllegalArgumentException::new);
    }

    @GET
    @Path("sendmessage")
    @Produces(MediaType.APPLICATION_JSON)
    public String chat(@QueryParam("chatid") int chatid, @QueryParam("user") String user, @QueryParam("text") String text) {
        Subject<Message> ch = Optional.ofNullable(channels.get(chatid)).orElseThrow(IllegalArgumentException::new);
        ch.onNext(new Message(user, text));
        return "ok";
    }
}
