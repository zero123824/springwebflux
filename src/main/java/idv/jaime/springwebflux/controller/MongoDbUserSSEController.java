package idv.jaime.springwebflux.controller;

import idv.jaime.springwebflux.persistence.User;
import idv.jaime.springwebflux.persistence.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.CollectionOptions;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.time.Duration;

@RestController
@RequestMapping("/mongoUserSSE")
public class MongoDbUserSSEController {

    @Autowired
    private UserService userService;

    @Autowired
    private ReactiveMongoTemplate reactiveMongoTemplate;

    @PostConstruct
    public void init(){
        reactiveMongoTemplate.dropCollection("user").then(reactiveMongoTemplate.createCollection("user", CollectionOptions.empty().capped().size(20000000000L).maxDocuments(20000))).subscribe();
    }

    @PostMapping("/save")
    public Mono<User> save(@RequestBody User user) {
        return this.userService.save(user);
    }

    // http://localhost:8080/mongoUserSSE/list
    @GetMapping(value = "/list", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<User> findAll() {
        return this.userService.findAll().delayElements(Duration.ofSeconds(1));
    }

    // http://localhost:8080/mongoUserSSE/gender2pushing?gender=M
    @GetMapping(value = "/gender2pushing", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<User> userAdded(@RequestParam String gender){
        return userService.findGenderContinues(gender);
    }


}
