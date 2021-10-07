package com.jinho.homepage.service;

import com.jinho.homepage.entity.UploadFileEntity;
import com.jinho.homepage.repository.ImageRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@Slf4j
public class ImageService {

    private final ImageRepository imageRepository;

    private final Path rootLocation; // d:/image/

    public ImageService(ImageRepository imageRepository, String uploadPath) {
        this.imageRepository = imageRepository;
        this.rootLocation = Paths.get(uploadPath);
    }

    public UploadFileEntity store(MultipartFile file) throws Exception {
        try {
            if (file.isEmpty()) {
                throw new Exception("Failed to store empty file " + file.getOriginalFilename());
            }

            String saveFileName = fileSave(rootLocation.toString(), file);
            UploadFileEntity saveFile = new UploadFileEntity();
            saveFile.setFileName(file.getOriginalFilename());
            saveFile.setSaveFileName(saveFileName);
            saveFile.setContentType(file.getContentType());
            saveFile.setSize(file.getResource().contentLength());
//            saveFile.RegisterDate(LocalDateTime.now());
            saveFile.setFilePath(rootLocation.toString().replace(File.separatorChar, '/') + '/' + saveFileName);
            imageRepository.save(saveFile);
            return saveFile;
        } catch (IOException e) {
            throw new Exception("Failed to store file " + file.getOriginalFilename(), e);
        }

    }

    public UploadFileEntity load(Long fileSeq) {
        return imageRepository.findById(fileSeq).get();
    }

    private String fileSave(String rootLocation, MultipartFile file) throws IOException {
        File uploadDir = new File(rootLocation);

        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        //saveFileName 생성
        UUID uuid = UUID.randomUUID();
        String saveFileName = uuid.toString() + file.getOriginalFilename();
        File saveFile = new File(rootLocation, saveFileName);
        FileCopyUtils.copy(file.getBytes(), saveFile);

        return saveFileName;
    }


}
