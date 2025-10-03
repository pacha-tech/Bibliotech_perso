package com.tp.service;

import com.tp.dao.DAOFactory;
import javax.servlet.ServletContext;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;
import java.sql.SQLException;

public class DeleteImage {

    private BookService bookService;

    public DeleteImage() {
        DAOFactory daoFactory = DAOFactory.getInstance();
        this.bookService = new BookService(daoFactory);
    }

    public boolean delete(String bookId, ServletContext servletContext) throws SQLException {
        String relativePath = bookService.getPathBookImage(bookId);

        if (relativePath != null && !relativePath.isEmpty()) {
            try {

                String absolutePath = servletContext.getRealPath(relativePath);
                Path filePath = Paths.get(absolutePath);

                return Files.deleteIfExists(filePath);

            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }
}