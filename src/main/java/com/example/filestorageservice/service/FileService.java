package com.example.filestorageservice.service;

import com.example.filestorageservice.entity.FileStorageEntity;
import com.example.filestorageservice.repository.FileStorageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

@Service
public class FileService {
    @Autowired
    private FileStorageRepository fileRepository;

    public FileStorageEntity saveFile(FileStorageEntity fileEntity) {
        return fileRepository.save(fileEntity);
    }

    public Optional<FileStorageEntity> getFileBase64ById(Long id) {
        return fileRepository.findById(id);
    }

    public Page<FileStorageEntity> getAllFiles(Pageable pageable) {
        return fileRepository.findAll(pageable);
    }

}
