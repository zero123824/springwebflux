package idv.jaime.springwebflux.persistence;

import org.springframework.data.mongodb.repository.Tailable;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigInteger;

@Repository("userRepo")
@EnableReactiveMongoRepositories
public interface UserRepository extends ReactiveCrudRepository<User, BigInteger> {

    @Tailable
    Flux<User> findByUsername(String username);
    Mono<Long> deleteByUsername(String username);

    @Tailable
    Flux<User> findByGender(String gender);

}
