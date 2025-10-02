package com.tp.model;

import java.time.LocalDateTime;

public class Book {


    private String book_id;
    private String title;
    private String author;
    private int year;
    private String image;
    private String category;
    private String description;
    private String status;
    private int loan_count;
    private LocalDateTime created_at;

    public Book(){
        this.book_id = "";
        this.title = "";
        this.author = "";
        this.year = 0;
        this.image = "";
        this.category = "";
        this.description = "";
        this.status = "";
        this.loan_count = 0;
        this.created_at = LocalDateTime.now();
    }

    public Book(String id_book , String title , String author , int year_publication , String image, String category , String description , String status , int loan_count , LocalDateTime created_at){
        this.book_id = id_book;
        this.title = title;
        this.author = author;
        this.year = year_publication;
        this.image = image;
        this.category = category;
        this.description = description;
        this.status = status;
        this.loan_count = loan_count;
        this.created_at = created_at;
    }

    public String getId_Book(){return this.book_id;}
    public String getTitle(){return this.title;}
    public String getAuthor(){return this.author;}
    public int getYear(){return this.year;}
    public String getImage(){ return this.image; }
    public String getCategory(){return this.category;}
    public String getDescription(){return this.description;}
    public String getStatus(){ return this.status; }
    public int getLoan_count(){ return this.loan_count; }
    public LocalDateTime getCreated_at(){return this.created_at; }
    public void setId_Book(String id_book){this.book_id = id_book;}
    public void setTitle(String title){this.title = title;}
    public void setAuthor(String author){this.author = author;}
    public  void setYear(int year_Publication){this.year = year_Publication;}
    public void setImage(String image ){ this.image = image; }
    public void setCategory(String category){this.category = category;}
    public void setDescription(String description){this.description = description;}
    public void setStatus(String status ){ this.status = status; }
    public void setLoan_count(int loan_count ){ this.loan_count = loan_count; }

}
