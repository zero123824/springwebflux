package idv.jaime.springwebflux;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import java.time.Duration;
import java.util.concurrent.ThreadLocalRandom;

@SpringBootApplication
@RestController
public class SpringwebfluxApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringwebfluxApplication.class, args);
        System.out.println("hello world");
    }


    @GetMapping("/randomNumber")
    public Flux<ServerSentEvent<Long>> randomNumbers() {
        return Flux.interval(Duration.ofSeconds(1))
                .map(tick -> Tuples.of(tick, ThreadLocalRandom.current().nextLong()))
                .map(this::randomNumberEvent)
                .take(5);
        }

    public ServerSentEvent<Long> randomNumberEvent(Tuple2<Long, Long> data) {
        return ServerSentEvent.<Long>builder()
                .event("randomNumber")
                .id(Long.toString(data.getT1()))
                .data(data.getT2())
                .build();
    }

    @GetMapping("/hello/{name}")
    public Mono<User> hello(@PathVariable("name") String name) {
        return Mono.just(new User(name)).filter(user -> user.getName().equals("bob"));
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    protected class User {
        private String name;
    }
}
