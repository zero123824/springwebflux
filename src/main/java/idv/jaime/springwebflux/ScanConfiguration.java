package idv.jaime.springwebflux;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ComponentScan(basePackages = {"mySpringwebflux", "mySpringwebflux.persistence"})
@Configuration
public class ScanConfiguration {
}
