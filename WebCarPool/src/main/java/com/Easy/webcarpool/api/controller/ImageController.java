package com.Easy.webcarpool.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/image")
public class ImageController {

    /*
    * 프로필 이미지 조회 api
    * */
    @GetMapping("/profile")
    public ResponseEntity<FileSystemResource> getProfileImage(@RequestParam String email) throws IOException {

        String savePath = System.getProperty("user.dir") + "/profile/"+email+"_profile.jpg";
        Path path = Paths.get(savePath);
        String contentType = Files.probeContentType(path);

        FileSystemResource resource = new FileSystemResource(savePath);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", contentType);

        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
    }

    /*
    * 차량 이미지 조회 api
    * */
    @GetMapping("/car")
    public ResponseEntity<FileSystemResource> getCarImage(@RequestParam String email) throws IOException {

        String savePath = System.getProperty("user.dir") + "/car/"+email+"_car.jpg";
        Path path = Paths.get(savePath);
        String contentType = Files.probeContentType(path);

        FileSystemResource resource = new FileSystemResource(savePath);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", contentType);

        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
    }
}
