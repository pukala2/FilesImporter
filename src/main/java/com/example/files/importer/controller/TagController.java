package com.example.files.importer.controller;

import com.example.files.importer.entity.Tag;
import com.example.files.importer.message.ResponseMessage;
import com.example.files.importer.service.TagService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/tags")
public class TagController {

    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @PostMapping()
    public ResponseEntity<ResponseMessage> tags(@RequestParam("file") MultipartFile file) {
        return new ResponseEntity<>(tagService.loadDataFromFileToDatabase(file), HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<Tag>> tags() {
        return new ResponseEntity<>(tagService.getTags(), HttpStatus.OK);
    }
}
