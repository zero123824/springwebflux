package idv.jaime.springwebflux;

import org.springframework.http.codec.ServerSentEvent;
import reactor.core.publisher.Flux;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.ThreadLocalRandom;

public class ReactorDemo {

    public static void main(String[] args) {
        Flux.just("Hello", "World").subscribe(System.out::println);
        Flux.fromArray(new Integer[] {1, 2, 3}).subscribe(System.out::println);
        Flux.empty().subscribe(System.out::println);
        Flux.range(1, 10).subscribe(System.out::println);
        Flux.range(1, 10)
                .filter(i -> i % 3 == 0)
                .subscribe(System.out::println);
        Flux.interval(Duration.of(10, ChronoUnit.SECONDS))
                .map(tick -> Tuples.of(tick, ThreadLocalRandom.current().nextLong()))
                .map(ReactorDemo::randomNumberEvent)
                .subscribe(System.out::println);
//        Flux.intervalMillis(1000).subscribe(System.out::println);

        Flux.create(sink -> {
            for (int i = 0; i < 10; i++) {
                sink.next(i);
            }
            sink.complete();
        }).subscribe(System.out::println);
    }

    public static ServerSentEvent<Long> randomNumberEvent(Tuple2<Long, Long> data) {
        return ServerSentEvent.<Long>builder()
                .event("randomNumber")
                .id(Long.toString(data.getT1()))
                .data(data.getT2())
                .build();
    }

}
