package com.example.files.importer.service;

import com.example.files.importer.entity.Tag;
import com.example.files.importer.dto.ResponseMessage;
import com.example.files.importer.parser.TagReader;
import com.example.files.importer.repository.TagRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class TagService {
    private final TagRepository tagRepository;
    private final TagReader csvTagReader;

    public TagService(TagRepository tagRepository, TagReader csvTagReader) {
        this.tagRepository = tagRepository;
        this.csvTagReader = csvTagReader;
    }

    public ResponseMessage loadDataFromFileToDatabase(MultipartFile file) {
        try {
            var numberOfRecords = tagRepository.saveAll(csvTagReader.read(file.getInputStream())).size();
            return new ResponseMessage("Added " + numberOfRecords + " tags to database.");

        } catch (IOException e) {
            throw new RuntimeException("Fail to store csv data: " + e.getMessage());
        }
    }

    public List<Tag> getTags() {
        return tagRepository.findAll();
    }
}
