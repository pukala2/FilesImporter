package com.example.files.importer.controller;

import com.example.files.importer.entity.Tag;
import com.example.files.importer.repository.TagRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TagControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TagRepository tagRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void cleanUp() {
        tagRepository.deleteAll();
    }

    @Test
    void shouldSaveTagsFromFile() throws Exception {
        var path = "src/test/resources/tags.csv";

        mockMvc.perform(MockMvcRequestBuilders.multipart("/tag")
                        .file("file", createMultipartFile(path).getBytes())
                        .characterEncoding("UTF-8"))
                .andExpect(status().isCreated());

        var mvcResult = mockMvc.perform(get("/tag"))
                .andExpect(status().isOk())
                .andReturn();

        var tags = Arrays.asList(objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Tag[].class));

        var expectedSize = 3683;
        Assertions.assertEquals(expectedSize, tags.size());
    }

    @Test
    void shouldGiveMaxSizeTagException() throws Exception {
        var path = "src/test/resources/tags_latest.csv";

        mockMvc.perform(MockMvcRequestBuilders.multipart("/tag")
                        .file("file", createMultipartFile(path).getBytes())
                        .characterEncoding("UTF-8"))
                .andExpect(status().isBadRequest());
    }

    private MultipartFile createMultipartFile(String path) throws IOException {
        var file = new File(path);
        var fileInputStream = new FileInputStream(file);
        var mockMultipartFile = new MockMultipartFile(
                "upload.csv", file.getName(), "multipart/form-data", fileInputStream);
        mockMultipartFile.getOriginalFilename().replace(mockMultipartFile.getOriginalFilename(), "tags.csv");
        return mockMultipartFile;
    }
}