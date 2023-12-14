package com.project.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import com.project.entities.Publication;
import com.project.service.PublicationService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
public class PublicationController {

    private final PublicationService publicationService;


    @PostMapping("/publications")
    public ResponseEntity<String> createPublication(@RequestBody Publication publication) {
        publicationService.createPublication(publication);
        return new ResponseEntity<>("your request is in progress.", HttpStatus.OK);
    }
}
