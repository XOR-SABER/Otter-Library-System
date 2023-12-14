package com.example.project2.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.project2.models.Book;
import com.example.project2.models.LogEntry;
import com.example.project2.models.User;

@Database(entities = {User.class, Book.class, LogEntry.class}, version = 1, exportSchema = false)
public abstract class LibaryDatabase extends RoomDatabase {
    public abstract UserDao users();
    public abstract BookDao books();
    public abstract LogDao logs();

    private static LibaryDatabase sInstance;
    public static synchronized LibaryDatabase getInstance(Context context) {
        if(sInstance == null) {
            sInstance = Room.databaseBuilder(context.getApplicationContext(),
                            LibaryDatabase.class, "libraryDatabase.db")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return sInstance;
    }
    public void populateInitialData() {
        runInTransaction(() -> {
            users().insert(new User("hShuard", "m@thl3t3"));
            users().insert(new User("bMishra", "bioN@no"));
            users().insert(new User("shirleyBee", "Carmel2Chicago"));
            users().insert(new User("!admin2", "!admin2"));
            books().insert(new Book("A Heartbreaking Work of Staggering Genius", "Dave Eggers", "Memoir"));
            books().insert(new Book("The IDA Pro Book", "Chris Eagle", "Computer Science"));
            books().insert(new Book("Frankenstein", "Mary Shelley", "Fiction"));

            // Charles Dickens books to test search by genre and by author
            books().insert(new Book("A Tale of Two Cities", "Charles Dickens", "Classic"));
            books().insert(new Book("Great Expectations", "Charles Dickens", "Classic"));
            books().insert(new Book("Oliver Twist", "Charles Dickens", "Classic"));
            books().insert(new Book("David Copperfield", "Charles Dickens", "Classic"));
        });
    }

}
