package com.jinho.homepage.repository;

import com.jinho.homepage.entity.UploadFileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<UploadFileEntity, Long> {
}
