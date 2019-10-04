package idv.jaime.springwebflux.service;

import idv.jaime.springwebflux.persistence.User;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigInteger;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class UserServiceImpl {
    private final Map<String, User> data = new ConcurrentHashMap<>();

    public Flux<User> list() {
        return Flux.fromIterable(this.data.values());
    }

    public Flux<User> getByName(final Flux<String> names) {
        return names.flatMap(name -> Mono.justOrEmpty(this.data.get(name)));
    }

    public Mono<User> getByName(final String name) {
        return Mono.justOrEmpty(this.data.get(name))
                .switchIfEmpty(Mono.error(new Exception("查無資料")));
    }

    public Mono<User> createOrUpdate(final User user) {
        this.data.put(user.getUsername(), user);
        return Mono.just(user);
    }

    public Mono<User> delete(final String id) {
        return Mono.justOrEmpty(this.data.remove(id));
    }
}
