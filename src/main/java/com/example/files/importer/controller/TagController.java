package com.example.files.importer.controller;

import com.example.files.importer.entity.Tag;
import com.example.files.importer.dto.ResponseMessage;
import com.example.files.importer.service.TagService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/tag")
public class TagController {

    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @PostMapping()
    public ResponseEntity<ResponseMessage> createTags(@RequestParam("file") MultipartFile file) {
        return new ResponseEntity<>(tagService.loadDataFromFileToDatabase(file), HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<List<Tag>> getTags() {
        return new ResponseEntity<>(tagService.getTags(), HttpStatus.OK);
    }
}
