package com.tp.dao.interfaceImpl;

import com.tp.dao.DAOFactory;
import com.tp.dao.interfaces.BookDAO;
import com.tp.model.Book;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookDAOImpl implements BookDAO {
    private final DAOFactory daoFactory;

    public BookDAOImpl(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @Override
    public void AddBook(Book book) {
        String sql = "INSERT INTO books(book_id, title, author, year, image, category, description, status, loan_count) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = daoFactory.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, book.getId_Book());
            stmt.setString(2, book.getTitle());
            stmt.setString(3, book.getAuthor());
            stmt.setInt(4, book.getYear());
            stmt.setString(5, book.getImage());
            stmt.setString(6, book.getCategory());
            stmt.setString(7, book.getDescription());
            stmt.setString(8, book.getStatus());
            stmt.setInt(9, book.getLoan_count());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'enregistrement du livre : " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public List<Book> findByTitle(String title) {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM books WHERE title = ?";
        try (Connection connection = daoFactory.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, title);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()){
                    Book book = new Book(
                            rs.getString("book_id"),
                            rs.getString("title"),
                            rs.getString("author"),
                            rs.getInt("year"),
                            rs.getString("image"),
                            rs.getString("category"),
                            rs.getString("description"),
                            rs.getString("status"),
                            rs.getInt("loan_count"),
                            rs.getTimestamp("created_at").toLocalDateTime()
                    );
                    books.add(book);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la recherche par titre : " + e.getMessage());
            e.printStackTrace();
        }
        return books;
    }

    @Override
    public List<Book> findByYear(int year) {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM books WHERE year = ?";
        try (Connection connection = daoFactory.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, year);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()){
                    Book book = new Book(
                            rs.getString("book_id"),
                            rs.getString("title"),
                            rs.getString("author"),
                            rs.getInt("year"),
                            rs.getString("image"),
                            rs.getString("category"),
                            rs.getString("description"),
                            rs.getString("status"),
                            rs.getInt("loan_count"),
                            rs.getTimestamp("created_at").toLocalDateTime()
                    );
                    books.add(book);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la recherche par année : " + e.getMessage());
            e.printStackTrace();
        }
        return books;
    }

    @Override
    public List<Book> findByAuthor(String author) {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM books WHERE author = ?";
        try (Connection connection = daoFactory.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, author);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()){
                    Book book = new Book(
                            rs.getString("book_id"),
                            rs.getString("title"),
                            rs.getString("author"),
                            rs.getInt("year"),
                            rs.getString("image"),
                            rs.getString("category"),
                            rs.getString("description"),
                            rs.getString("status"),
                            rs.getInt("loan_count"),
                            rs.getTimestamp("created_at").toLocalDateTime()
                    );
                    books.add(book);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la recherche par auteur : " + e.getMessage());
            e.printStackTrace();
        }
        return books;
    }

    @Override
    public List<Book> findByCategory(String category) {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM books WHERE category = ?";
        try (Connection connection = daoFactory.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, category);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()){
                    Book book = new Book(
                            rs.getString("book_id"),
                            rs.getString("title"),
                            rs.getString("author"),
                            rs.getInt("year"),
                            rs.getString("image"),
                            rs.getString("category"),
                            rs.getString("description"),
                            rs.getString("status"),
                            rs.getInt("loan_count"),
                            rs.getTimestamp("created_at").toLocalDateTime()
                    );
                    books.add(book);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la recherche par catégorie : " + e.getMessage());
            e.printStackTrace();
        }
        return books;
    }

    @Override
    public List<Book> findByDisponible() {
        List<Book> books = new ArrayList<>();
        String Rendu = "disponible";
        String sql = "SELECT * FROM books WHERE status = ?";
        try (Connection connection = daoFactory.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, Rendu);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()){
                    Book book = new Book(
                            rs.getString("book_id"),
                            rs.getString("title"),
                            rs.getString("author"),
                            rs.getInt("year"),
                            rs.getString("image"),
                            rs.getString("category"),
                            rs.getString("description"),
                            rs.getString("status"),
                            rs.getInt("loan_count"),
                            rs.getTimestamp("created_at").toLocalDateTime()
                    );
                    books.add(book);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la recherche des livres disponibles : " + e.getMessage());
            e.printStackTrace();
        }
        return books;
    }

    @Override
    public List<Book> findByEmprunter() {
        List<Book> books = new ArrayList<>();
        String Encour = "emprunté";
        String sql = "SELECT * FROM books WHERE status = ?";
        try (Connection connection = daoFactory.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, Encour);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()){
                    Book book = new Book(
                            rs.getString("book_id"),
                            rs.getString("title"),
                            rs.getString("author"),
                            rs.getInt("year"),
                            rs.getString("image"),
                            rs.getString("category"),
                            rs.getString("description"),
                            rs.getString("status"),
                            rs.getInt("loan_count"),
                            rs.getTimestamp("created_at").toLocalDateTime()
                    );
                    books.add(book);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la recherche des livres empruntés : " + e.getMessage());
            e.printStackTrace();
        }
        return books;
    }

    @Override
    public List<Book> findByPopularity() {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM books ORDER BY loan_count DESC";
        try (Connection connection = daoFactory.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()){
                Book book = new Book(
                        rs.getString("book_id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getInt("year"),
                        rs.getString("image"),
                        rs.getString("category"),
                        rs.getString("description"),
                        rs.getString("status"),
                        rs.getInt("loan_count"),
                        rs.getTimestamp("created_at").toLocalDateTime()
                );
                books.add(book);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des livres par popularité : " + e.getMessage());
            e.printStackTrace();
        }
        return books;
    }

    @Override
    public List<Book> findByRecent() {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM books ORDER BY year DESC ";
        try (Connection connection = daoFactory.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()){
                Book book = new Book(
                        rs.getString("book_id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getInt("year"),
                        rs.getString("image"),
                        rs.getString("category"),
                        rs.getString("description"),
                        rs.getString("status"),
                        rs.getInt("loan_count"),
                        rs.getTimestamp("created_at").toLocalDateTime()
                );
                books.add(book);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des livres récents : " + e.getMessage());
            e.printStackTrace();
        }
        return books;
    }

    @Override
    public List<Book> findByOld() {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM books ORDER BY year ASC";
        try (Connection connection = daoFactory.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()){
                Book book = new Book(
                        rs.getString("book_id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getInt("year"),
                        rs.getString("image"),
                        rs.getString("category"),
                        rs.getString("description"),
                        rs.getString("status"),
                        rs.getInt("loan_count"),
                        rs.getTimestamp("created_at").toLocalDateTime()
                );
                books.add(book);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des livres anciens : " + e.getMessage());
            e.printStackTrace();
        }
        return books;
    }

    @Override
    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM books ORDER BY created_at DESC";
        try (Connection connection = daoFactory.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()){
                Book book = new Book(
                        rs.getString("book_id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getInt("year"),
                        rs.getString("image"),
                        rs.getString("category"),
                        rs.getString("description"),
                        rs.getString("status"),
                        rs.getInt("loan_count"),
                        rs.getTimestamp("created_at").toLocalDateTime()
                );
                books.add(book);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération de tous les livres : " + e.getMessage());
            e.printStackTrace();
        }
        return books;
    }

    @Override
    public void DeleteBook(String book_id) {
        String sql = "DELETE FROM books WHERE book_id = ?";
        try (Connection connection = daoFactory.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, book_id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression du livre : " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void updateBook(Book book) {
        String sql = "UPDATE books SET title = ?, author = ?, year = ?, image = ?, category = ?, description = ?, status = ?, loan_count = ? WHERE book_id = ?";
        try (Connection connection = daoFactory.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, book.getTitle());
            stmt.setString(2, book.getAuthor());
            stmt.setInt(3, book.getYear());
            stmt.setString(4, book.getImage());
            stmt.setString(5, book.getCategory());
            stmt.setString(6, book.getDescription());
            stmt.setString(7, book.getStatus());
            stmt.setInt(8, book.getLoan_count());
            stmt.setString(9, book.getId_Book());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erreur lors de la modification du livre : " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public Book getBook(String book_id) {
        Book book = null;
        String sql = "SELECT * FROM books WHERE book_id = ?";
        try (Connection connection = daoFactory.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, book_id);
            try (ResultSet rs = stmt.executeQuery()) {
                if(rs.next()){
                    book = new Book(
                            rs.getString("book_id"),
                            rs.getString("title"),
                            rs.getString("author"),
                            rs.getInt("year"),
                            rs.getString("image"),
                            rs.getString("category"),
                            rs.getString("description"),
                            rs.getString("status"),
                            rs.getInt("loan_count"),
                            rs.getTimestamp("created_at").toLocalDateTime()
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération du livre par ID : " + e.getMessage());
            e.printStackTrace();
        }
        return book;
    }

    @Override
    public boolean updateBookStatus(String bookId, String status) {
        String query = "UPDATE books SET status = ? WHERE book_id = ?";
        try (Connection connexion = daoFactory.getConnection();
             PreparedStatement stmt = connexion.prepareStatement(query)) {
            stmt.setString(1, status);
            stmt.setString(2, bookId);
            int updated = stmt.executeUpdate();
            return updated > 0;
        } catch (SQLException e) {
            System.err.println("Erreur lors de la mise à jour du statut du livre : " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void AddLoanCountOfBook(String book_id) {
        String sql = "UPDATE books SET loan_count = loan_count + 1 WHERE book_id = ?";
        try (Connection connection = daoFactory.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, book_id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'incrémentation du nombre de prêts : " + e.getMessage());
            e.printStackTrace();
        }
    }
}