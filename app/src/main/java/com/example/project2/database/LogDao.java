package com.example.project2.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.project2.models.LogEntry;

import java.util.List;

@Dao
public interface LogDao {

    @Insert
    void insertLog(LogEntry log);

    @Query("SELECT * FROM logs")
    List<LogEntry> getAllLogs();

    @Query("SELECT * FROM logs WHERE id = :logId")
    LogEntry getLogById(int logId);
}