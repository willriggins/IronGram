package com.theironyard.services;

import com.theironyard.entities.User;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by will on 6/28/16.
 */
public interface UserRepository extends CrudRepository<User, Integer>{
    public User findFirstByName(String name);
}
