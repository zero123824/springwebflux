package idv.jaime.springwebflux.persistence;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.math.BigInteger;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
public class User {

    public User(String username, String gender){
        this.username = username;
        this.gender = gender;
    }

    @Indexed(unique = true) // 注解属性username为索引，并且不能重复
    private String username;

    @Id
    private BigInteger id;

    private String gender;
}
