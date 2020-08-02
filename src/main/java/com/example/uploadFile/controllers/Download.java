package com.example.uploadFile.controllers;

import com.example.uploadFile.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
public class Download {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/download/{fileName}")
    public ResponseEntity handleDownload(@PathVariable String fileName){
        Path path = Paths.get("./upload");
        path = path.resolve(fileName);
        UrlResource resource = null;
        try{
            resource = new UrlResource(path.toUri());
        }catch (Exception error){
            error.printStackTrace();
        }
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename = \"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @GetMapping("/getAll")
    public ResponseEntity handleUser(){
        return ResponseEntity.ok(userRepository.findAll());
    }
}
