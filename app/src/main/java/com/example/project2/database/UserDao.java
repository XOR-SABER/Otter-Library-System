package com.example.project2.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.project2.models.User;

import java.util.List;

@Dao
public interface UserDao {
    @Insert
    void insert(User user);

    @Delete
    void delete(User user);

    @Update
    void update(User user);

    @Query("SELECT * FROM userTable WHERE id = :userId")
    User getUserById(int userId);

    @Query("SELECT id FROM userTable WHERE username = :username AND password = :password")
    int getUserIdByUsernameAndPassword(String username, String password);

    @Query("SELECT * FROM userTable WHERE Username = :username")
    User getUserByUsername(String username);

    @Query("SELECT * FROM userTable WHERE Username = :username AND Password = :password")
    User getUserByUsernameAndPassword(String username, String password);

    @Query("SELECT * FROM userTable")
    List<User> getAllUsers();

    @Query("SELECT COUNT(*) FROM userTable WHERE Username = :username")
    int getUsernameCount(String username);

    // Am only using this for testing!
    @Query("DELETE FROM userTable")
    void clearAllUsers();
}