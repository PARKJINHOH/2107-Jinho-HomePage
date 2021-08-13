package com.jinho.homepage.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class UploadFileEntity {

    @Id
    @Column(name = "board_seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fileSeq;

    @Column(name = "fileName")
    private String fileName;

    @Column(name = "saveFileName")
    private String saveFileName;

    @Column(name = "filePath")
    private String filePath;

    @Column(name = "contentType")
    private String contentType;

    private long size;
}
