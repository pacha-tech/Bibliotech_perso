package com.tp.service;

import java.io.File;
import java.io.IOException;
import java.util.UUID;
import javax.servlet.http.*;

    public class FileUploader {

        private final String uploadDirectory;

        public FileUploader(String uploadDirectory) {
            this.uploadDirectory = uploadDirectory;
        }

        public String handleFileUpload(Part filePart, String realPath) throws IOException {
            if (filePart == null || filePart.getSize() <= 0) {
                return null;
            }

            String originalFileName = extractFileName(filePart);
            if (originalFileName.isEmpty()) {
                return null;
            }

            String uniqueFileName = UUID.randomUUID().toString().substring(0, 6) + "_" + originalFileName;
            String uploadPath = realPath + File.separator + uploadDirectory;
            File uploadDir = new File(uploadPath);

            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }

            filePart.write(uploadPath + File.separator + uniqueFileName);

            return uploadDirectory + "/" + uniqueFileName;
        }

        private String extractFileName(Part part) {
            String contentDisposition = part.getHeader("content-disposition");
            for (String s : contentDisposition.split(";")) {
                if (s.trim().startsWith("filename")) {
                    return s.substring(s.indexOf('=') + 2, s.length() - 1);
                }
            }
            return "";
        }
    }

