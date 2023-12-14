package com.example.project2.database;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.project2.models.Book;

import java.util.List;

@Dao
public interface BookDao {

    @Insert
    void insert(Book book);

    @Update
    void update(Book book);

    @Delete
    void delete(Book book);

    @Query("SELECT * FROM books")
    List<Book> getAllBooks();

    @Query("SELECT * FROM books WHERE title = :title")
    List<Book> getBooksByTitle(String title);

    @Query("SELECT * FROM books WHERE author = :author")
    List<Book> getBooksByAuthor(String author);

    @Query("SELECT * FROM books WHERE genre = :genre")
    List<Book> getBooksByGenre(String genre);

    @Query("SELECT * FROM books WHERE holder = :holder")
    List<Book> getBooksByHolder(String holder);

    @Query("SELECT * FROM books WHERE holder IS NULL OR holder = ''")
    List<Book> getBooksWithoutHolder();

    @Query("SELECT * FROM books WHERE holder IS NOT NULL AND holder != ''")
    List<Book> getBooksWithHolder();

    @Query("SELECT COUNT(*) FROM books")
    int getBookCount();

    @Query("SELECT id FROM books WHERE title = :title AND author = :author AND genre = :genre")
    int getIdByBook(String title, String author, String genre);

    @Query("SELECT * FROM books WHERE id = :id")
    Book getBookById(int id);


}