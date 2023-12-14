package com.example.project2.models;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "logs")
public class LogEntry {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String logMessage;
    private String location;

    public LogEntry(String location, String logMessage) {
        this.location = location;
        this.logMessage = logMessage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogMessage() {
        return logMessage;
    }

    public void setLogMessage(String logMessage) {
        this.logMessage = logMessage;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return location + ": " + logMessage;
    }
}
