package com.example.filestorageservice.controller;

import com.example.filestorageservice.entity.FileStorageEntity;
import com.example.filestorageservice.exception.FileNotFoundException;
import com.example.filestorageservice.exception.InvalidRequestException;
import com.example.filestorageservice.service.FileService;
import com.example.filestorageservice.repository.FileStorageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/files")
public class FileStorageController {

    @Autowired
    private FileStorageRepository fileRepository;

    @Autowired
    private FileService fileService;

    @PostMapping
    public ResponseEntity<Map<String, Long>> saveFile(@RequestBody FileStorageEntity fileEntity) {
        if (fileEntity.getTitle() == null || fileEntity.getFileBase64() == null) {
            throw new InvalidRequestException("Название и файл не могут быть пустыми");
        }
        FileStorageEntity savedFile = fileRepository.save(fileEntity);
        Map<String, Long> response = new HashMap<>();
        response.put("id", savedFile.getId());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FileStorageEntity> getFileBase64(@PathVariable Long id) {
        return fileRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new FileNotFoundException("Файл с заданным id: " + id + " не найден"));
    }

    @GetMapping
    public ResponseEntity<Page<FileStorageEntity>> getFilesBase64(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sortBy", defaultValue = "creationDate") String sortBy) {

        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, sortBy));
        Page<FileStorageEntity> filePage = fileService.getAllFiles(pageRequest);
        return ResponseEntity.ok(filePage);
    }
}
