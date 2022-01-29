package com.example.files.importer.parser;

import com.example.files.importer.entity.Tag;

import java.io.InputStream;
import java.util.List;

public interface TagReader {
    List<Tag> read(InputStream initialStream);
}
