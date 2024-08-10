package com.example.filestorageservice.repository;

import com.example.filestorageservice.entity.FileStorageEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class FileStorageRepositoryTest {

    String testTitle = "Test File";
    Date testCreationDate = new Date();
    String testDescription = "Test Description";
    String testFile = "Test File Content";

    @Autowired
    private FileStorageRepository fileRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void testSaveFile() {
        FileStorageEntity file = new FileStorageEntity();
        file.setTitle(testTitle);
        file.setCreationDate(testCreationDate);
        file.setDescription(testDescription);
        file.setFileBase64(testFile);

        FileStorageEntity savedFile = fileRepository.save(file);

        assertThat(savedFile).isNotNull();
        assertThat(savedFile.getTitle()).isEqualTo(testTitle);
        assertThat(savedFile.getCreationDate()).isEqualTo(testCreationDate);
        assertThat(savedFile.getDescription()).isEqualTo(testDescription);
        assertThat(savedFile.getFileBase64()).isEqualTo(testFile);
    }

    @Test
    public void testFindById() {
        FileStorageEntity file = new FileStorageEntity();
        file.setTitle(testTitle);
        file.setCreationDate(testCreationDate);
        file.setDescription(testDescription);
        file.setFileBase64(testFile);

        FileStorageEntity savedFile = fileRepository.save(file);
        Optional<FileStorageEntity> foundFile = fileRepository.findById(savedFile.getId());

        assertThat(foundFile).isPresent();
        assertThat(foundFile.get().getTitle()).isEqualTo(testTitle);
        assertThat(foundFile.get().getCreationDate()).isEqualTo(testCreationDate);
        assertThat(foundFile.get().getDescription()).isEqualTo(testDescription);
        assertThat(foundFile.get().getFileBase64()).isEqualTo(testFile);
    }

    @Test
    public void testFindAll() {
        FileStorageEntity file1 = new FileStorageEntity();
        file1.setTitle("Test File 1");
        file1.setCreationDate(new Date());
        file1.setDescription("Test Description 1");
        file1.setFileBase64("Test File Content 1");

        FileStorageEntity file2 = new FileStorageEntity();
        file2.setTitle("Test File 2");
        file2.setCreationDate(new Date());
        file2.setDescription("Test Description 2");
        file2.setFileBase64("Test File Content 2");

        fileRepository.save(file1);
        fileRepository.save(file2);

        var pageable = PageRequest.of(0, 10);
        var allFiles = fileRepository.findAll(pageable);

        assertThat(allFiles.getTotalElements()).isEqualTo(2);
        assertThat(allFiles.getContent().get(0).getTitle()).isEqualTo("Test File 1");
        assertThat(allFiles.getContent().get(1).getTitle()).isEqualTo("Test File 2");
    }
}
