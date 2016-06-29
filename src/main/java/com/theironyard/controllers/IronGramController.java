package com.theironyard.controllers;

import com.theironyard.entities.Photo;
import com.theironyard.entities.User;
import com.theironyard.services.PhotoRepository;
import com.theironyard.services.UserRepository;
import org.h2.tools.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.SQLException;

/**
 * Created by will on 6/28/16.
 */
@Controller
public class IronGramController {
    @Autowired
    UserRepository users;

    @Autowired
    PhotoRepository photos;

    @PostConstruct
    public void init() throws SQLException {
        Server.createWebServer().start();
    }


    @RequestMapping(path = "/upload", method = RequestMethod.POST)
    public String upload(MultipartFile file, String receiver, HttpSession session, HttpServletRequest request) throws Exception {
        String durationStr = request.getParameter("duration");
        int duration = Integer.valueOf(durationStr);

        String username = (String) session.getAttribute("username");
        User sender = users.findFirstByName(username);
        User rec = users.findFirstByName(receiver);
        if (sender == null || rec == null) {
            throw new Exception("Can't find sender or receiver");
        }

        String pub = request.getParameter("pub");
        boolean isPublic;
        if (pub == null) {
            isPublic = false;
        }
        else {
            isPublic = true;
        }

        File dir = new File("public/photos");
        dir.mkdirs();

        String fileStr = file.getOriginalFilename();
        String[] columns = fileStr.split("\\.");
        String fileType = columns[1];

        // use getContentType.contains("image")
        if (fileType.equals("jpg")) {
            File photoFile = File.createTempFile("photo", file.getOriginalFilename(), dir); // prefix and suffix.. then directory --read about this
            FileOutputStream fos = new FileOutputStream(photoFile);
            fos.write(file.getBytes());


            Photo photo = new Photo(sender, rec, photoFile.getName(), duration, isPublic);
            photos.save(photo);
        }
        else {
            throw new Exception("Not an image!");
        }

        return "redirect:/";
    }
//        if (LocalTime.now() == timeExpire) {
//            LocalDateTime time = LocalDateTime.now();
//            photos.delete(photo)
//        }
//        if (photo.getTimeToDelete() == LocalTime.now()) {
//
//        }

    // first idea: ongoing method that checks if files have been uploaded for 10 secs: not working

//    public String delete() {
//        Iterable<Photo> currentPhotos = photos.findByOrderByIdAsc();
//        if (currentPhotos.iterator().hasNext()) {
//            Photo p = currentPhotos.iterator().next();
//            if (LocalTime.now() == p.getTimeToDelete()) {
//                photos.delete(p.getId());
//                File fileName = new File("public/files/" + p.getFilename());
//                fileName.delete();
//
//                return "redirect:/";
//            }
//            else {
//                System.out.println("Nothing to delete");
//            }
}
