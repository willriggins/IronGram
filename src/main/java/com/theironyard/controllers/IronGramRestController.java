package com.theironyard.controllers;

import com.theironyard.entities.Photo;
import com.theironyard.entities.User;
import com.theironyard.services.PhotoRepository;
import com.theironyard.services.UserRepository;
import com.theironyard.utils.PasswordStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.time.LocalDateTime;

/**
 * Created by will on 6/28/16.
 */
@RestController
public class IronGramRestController {
    @Autowired
    UserRepository users;

    @Autowired
    PhotoRepository photos;

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public User login(@RequestBody User user, HttpSession session) throws Exception {
        User userFromDB = users.findFirstByName(user.getName());
        if (userFromDB == null) {
            user.setPassword(PasswordStorage.createHash(user.getPassword()));
            users.save(user);
        }
        else if (!PasswordStorage.verifyPassword(user.getPassword(), userFromDB.getPassword())) {
            throw new Exception("Wrong password!");
        }

        session.setAttribute("username", user.getName());
        //when you do json routes, you don't redirect. you let the front end handle that. you just return
        return user;
    }

    @RequestMapping(path = "/logout", method = RequestMethod.POST)
    public void logout (HttpSession session) {
        session.invalidate();
    }

    @RequestMapping(path = "/photos", method = RequestMethod.GET)
    public Iterable<Photo> getPhotos(HttpSession session) {
        String username = (String) session.getAttribute("username");
        User user = users.findFirstByName(username);

        LocalDateTime time = LocalDateTime.now();
        Iterable<Photo> allPhotos = photos.findByRecipient(user);

        for (Photo photo : allPhotos) {
            if (photo.getTime() == null) {
                photo.setTime(LocalDateTime.now());
                photos.save(photo);
            }

            if (time.isAfter(photo.getTime().plusSeconds(photo.getDuration()))) {
                photos.delete(photo);
                File f = new File("public/photos/" + photo.getFilename());
                f.delete();
            }
        }
        return photos.findByRecipient(user);
    }

    @RequestMapping(path = "/public-photos", method = RequestMethod.GET)
    public Iterable<Photo> getPublicPhotos(HttpSession session) {
        String username = (String) session.getAttribute("username");
        User user = users.findFirstByName(username);

        return photos.findBySenderAndPub(user, true);
    }

    @RequestMapping(path = "/user", method = RequestMethod.GET)
    public User getUser(HttpSession session) {
        String username = (String) session.getAttribute("username");
        if (username == null) {
            return null;
        }
        else {
            return users.findFirstByName(username);
        }
    }


}
