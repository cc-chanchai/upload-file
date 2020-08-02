package com.example.uploadFile.controllers;

import com.example.uploadFile.models.User;
import com.example.uploadFile.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;


@RestController
public class Upload {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/upload")
    public ResponseEntity handleUpload(@RequestParam("file")MultipartFile file, @RequestParam("name")String name){
        User user = new User();
        user.setName(name);
        Path path = Paths.get("./upload");
        try {
            if (!Files.isDirectory(path)){
                Files.createDirectories(path);
            }
            Files.copy(file.getInputStream(), path.resolve(file.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
            user.setPictureFile(file.getOriginalFilename());
            userRepository.save(user);
        }catch (Exception e) {
            e.printStackTrace();

        }
        return ResponseEntity.ok(file.getOriginalFilename());
    }
}
