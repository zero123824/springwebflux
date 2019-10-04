package idv.jaime.springwebflux.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "/api")
public class BasicController {

    @GetMapping("/helloWorld")
    public Mono<String> sayHelloWorld() {
        return Mono.just("Hello World");
    }

}
