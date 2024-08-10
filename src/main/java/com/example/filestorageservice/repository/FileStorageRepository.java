package com.example.filestorageservice.repository;

import com.example.filestorageservice.entity.FileStorageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileStorageRepository extends JpaRepository<FileStorageEntity, Long> {
}
