package idv.jaime.springwebflux.controller;

import idv.jaime.springwebflux.persistence.User;
import idv.jaime.springwebflux.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

//@RestController
//@RequestMapping("/user")
//public class UserController {
//
//    @Autowired
//    private UserServiceImpl userServiceImpl;
//
//    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Resource not found")
//    @ExceptionHandler(Exception.class)
//    public void notFound() {
//    }
//
//    @GetMapping("")
//    public Flux<User> list() {
//        return this.userServiceImpl.list();
//    }
//
//    @GetMapping("/{name}")
//    public Mono<User> getById(@PathVariable("name") final String name) {
//        return this.userServiceImpl.getByName(name);
//    }
//
//    @PostMapping("")
//    public Mono<User> create(@RequestBody final User user) {
//        return this.userService.createOrUpdate(user);
//    }
//
//    @PutMapping("/{id}")
//    public Mono<User>  update(@PathVariable("id") final String id, @RequestBody final User user) {
//        Objects.requireNonNull(user);
//        user.setId(id);
//        return this.userService.createOrUpdate(user);
//    }
//
//    @DeleteMapping("/{id}")
//    public Mono<User>  delete(@PathVariable("id") final String id) {
//        return this.userServiceImpl.delete(id);
//    }
//}
