package com.tekitng.workman.repositories;

import com.tekitng.workman.entities.User;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

/**
 * @author Amusuo Paschal C.
 * @since 10/27/2020 11:12 PM
 */

@ApplicationScoped
public class UserRepository implements PanacheRepository<User> {

    public void saveUser(User user) {
        persist(user);
    }

    public User findUserByEmail(String email) {
        return find("email", email).firstResult();
    }

    public List<User> findAllUsers() {
        return findAll().list();
    }


}
