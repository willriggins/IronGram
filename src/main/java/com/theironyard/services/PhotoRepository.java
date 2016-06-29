package com.theironyard.services;

import com.theironyard.entities.Photo;
import com.theironyard.entities.User;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by will on 6/28/16.
 */
public interface PhotoRepository extends CrudRepository<Photo, Integer>{
    public Iterable<Photo> findByRecipient(User receiver);
    public Iterable<Photo> findByOrderByIdAsc();
    public Iterable<Photo> findBySenderAndPub(User sender, boolean pub);
}
