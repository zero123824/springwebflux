package idv.jaime.springwebflux.controller;

import idv.jaime.springwebflux.persistence.User;
import idv.jaime.springwebflux.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.DirectProcessor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxProcessor;
import reactor.core.publisher.FluxSink;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/userSSE")
public class UserSSEController {

    @Autowired
    private UserServiceImpl userServiceImpl;

    final FluxProcessor processor = DirectProcessor.create().serialize();
    final FluxSink sink = processor.sink();
    final AtomicLong inputCounter = new AtomicLong(1);
    final AtomicLong deleteCounter = new AtomicLong(1);

    @GetMapping("/get")
    public Flux<ServerSentEvent> randomNumbers() {
        return Flux.interval(Duration.ofSeconds(1))
                .map(id -> userServiceImpl.list())
//                .map(seq -> Tuples.of(seq, ThreadLocalRandom.current().nextInt()))
                .map(user -> ServerSentEvent.builder()
                        .event("userID")
//                        .id(Long.toString(data.getT1()))
                        .data(user)
                        .build());
    }

    @PostMapping("/{name}/{gender}")
    public void insertUser(@PathVariable("name") String name, @PathVariable("gender") String gender) {
        User user = new User(name, gender);
        userServiceImpl.createOrUpdate(user);
        try{
            sink.next("Post User #" + inputCounter.getAndIncrement() + user);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @DeleteMapping("/{name}")
    public void deleteUser(@PathVariable("name") String name){
        String gender = "M";
        User user = new User(name, gender);
        try {
            userServiceImpl.getByName(name).hasElement().block();
            userServiceImpl.delete(name);
            sink.next("Delete User # " + deleteCounter.getAndIncrement() + user);
        } catch (Exception e){
            System.out.println(e.getMessage());
            return;
        }
    }

    // http://localhost:8080/userSSE/event
    @RequestMapping(value = "/event",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ServerSentEvent> userEvent(){
        return processor.map(e -> ServerSentEvent.builder(e)
                .event("使用者事件:")
                .build());
    }

    //查詢全部
    @GetMapping("")
    public Flux<User> list() {
        return this.userServiceImpl.list();
    }

}
