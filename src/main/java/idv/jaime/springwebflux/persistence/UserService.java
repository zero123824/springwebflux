package idv.jaime.springwebflux.persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service("userServiceImpl2")
public class UserService {

    @Autowired
    private UserRepository userRepo;

    /**
     * 保存或更新。
     * 如果传入的user没有id属性，由于username是unique的，在重复的情况下有可能报错，
     * 这时找到以保存的user记录用传入的user更新它。
     */
    public Mono<User> save(User user) {
        Mono<User> saved = userRepo.save(user)
                .onErrorResume(e ->     // 1
                        userRepo.findByUsername(user.getUsername()).publishNext()   // 2
                                .flatMap(originalUser -> {      // 4
                                    user.setId(originalUser.getId());
                                    return userRepo.save(user);   // 3
                                }));
        saved.subscribe(System.out::println);
        return saved;
    }

    public Mono<Long> deleteByUsername(String username) {
        return userRepo.deleteByUsername(username);
    }

//    public Mono<User> findByUsername(String username) {
//        return userRepo.findByUsername(username);
//    }

    public Flux<User> findAll() {
        return userRepo.findAll();
    }

    public Flux<User> findGenderContinues(String gender) {
        return userRepo.findByGender(gender);
    }
}
