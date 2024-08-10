package com.example.filestorageservice.controller;

import com.example.filestorageservice.entity.FileStorageEntity;
import com.example.filestorageservice.service.FileService;
import com.example.filestorageservice.repository.FileStorageRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(FileStorageController.class)
@ActiveProfiles("test")
public class FileStorageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FileService fileService;

    @MockBean
    private FileStorageRepository fileRepository;

    @Test
    public void testSaveFile() throws Exception {
        FileStorageEntity file = new FileStorageEntity();
        file.setId(1L);
        file.setTitle("Test File");
        file.setCreationDate(new Date());
        file.setDescription("Test Description");
        file.setFileBase64("Test File Content");

        Mockito.when(fileRepository.save(Mockito.any(FileStorageEntity.class))).thenReturn(file);

        mockMvc.perform(post("/api/files")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"Test File\", \"creationDate\": \"2024-08-08T11:10:10\", \"description\": " +
                                "\"Test Description\", \"fileBase64\": \"c2Rmc2ZzZnNkZnNkZnNkZgo=\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }


    @Test
    public void testGetFileById() throws Exception {
        FileStorageEntity file = new FileStorageEntity();
        file.setId(1L);
        file.setTitle("Test File!");
        file.setCreationDate(new Date());
        file.setDescription("Test Description!");
        file.setFileBase64("aGpuaWtkZm1vc2wuO2ZwaW8=");

        Mockito.when(fileRepository.findById(1L)).thenReturn(Optional.of(file));

        mockMvc.perform(get("/api/files/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("Test File!"))
                .andExpect(jsonPath("$.creationDate").exists())
                .andExpect(jsonPath("$.description").value("Test Description!"))
                .andExpect(jsonPath("$.fileBase64").value("aGpuaWtkZm1vc2wuO2ZwaW8="));
    }

    @Test
    public void testGetAllFiles() throws Exception {
        FileStorageEntity file1 = new FileStorageEntity();
        file1.setTitle("Test File 1");
        file1.setCreationDate(new Date());
        file1.setDescription("Test Description 1");
        file1.setFileBase64("aGVyaml3b3Jbd2VycQ==");

        FileStorageEntity file2 = new FileStorageEntity();
        file2.setTitle("Test File 2");
        file2.setCreationDate(new Date());
        file2.setDescription("Test Description 2");
        file2.setFileBase64("amZud29lZm53aWVmbm93ZQ==");

        Mockito.when(fileService.getAllFiles(Mockito.any(PageRequest.class)))
                .thenReturn(new PageImpl<>(List.of(file1, file2)));

        mockMvc.perform(get("/api/files")
                        .param("page", "0")
                        .param("size", "10")
                        .param("sortBy", "creationDate"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].fileBase64").value("aGVyaml3b3Jbd2VycQ=="))
                .andExpect(jsonPath("$.content[1].fileBase64").value("amZud29lZm53aWVmbm93ZQ=="));
    }
}
