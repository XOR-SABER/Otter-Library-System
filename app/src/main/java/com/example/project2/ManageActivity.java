package com.example.project2;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.example.project2.database.LibaryDatabase;
import com.example.project2.databinding.ManageActivityBinding;
import com.example.project2.dialogs.AddBookDialog;
import com.example.project2.dialogs.BookDialog;
import com.example.project2.dialogs.SearchBooksDialog;
import com.example.project2.models.Book;
import com.example.project2.models.LogEntry;

import java.util.ArrayList;
import java.util.List;

public class ManageActivity extends AppCompatActivity implements AddBookDialog.BookDialogListener {

    private LibaryDatabase db;
    private ManageActivityBinding binding;
    private String currentState = "ShowAllLogs";
    private ListView manageListView;

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = LibaryDatabase.getInstance(this);
        binding = ManageActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        manageListView = binding.showAll;
        binding.ShowLogs.setOnClickListener(v -> {
            // Just set up listview all the logs
            if(currentState.equals("ShowAllLogs")) {
                // Toggle
                currentState = "";
                Log.i("current state is : " , currentState);
                updateList();
            } else {
                currentState = "ShowAllLogs";
                Log.i("current state is : " , currentState);
                updateList();
            }
        });
        binding.showHolds.setOnClickListener(v -> {
            // Just set up listview all the holds. to clear
            if(currentState.equals("ShowAllHolds")) {
                // Toggle
                currentState = "";
                Log.i("current state is : " , currentState);
                updateList();
            } else {
                currentState = "ShowAllHolds";
                Log.i("current state is : " , currentState);
                updateList();
            }
        });
        updateList();
    }

    // We only need to create this when we need it..
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.admin_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.back_button) {
            finish();
        }
        if (item.getItemId() == R.id.add_book) {
            DialogFragment dialogFragment = AddBookDialog.newInstance();
            dialogFragment.show(getSupportFragmentManager(), "");
        }
        return super.onOptionsItemSelected(item);
    }

    public void updateList() {
        if(currentState.equals("ShowAllLogs")) {
            generateLogAdapter();
            return;
        }
        else if (currentState.equals("ShowAllHolds")) {
            generateHoldsAdapter();
            return;
        }
        ArrayAdapter<Book> bookArrayAdapter = new ArrayAdapter<>(this, R.layout.item_book, R.id.book_item, new ArrayList<>());
        manageListView.setAdapter(bookArrayAdapter);
        // Show nothing..
    }

    private void generateLogAdapter() {
        List<LogEntry> heldLogs = new ArrayList<>();
        try {
            heldLogs = db.logs().getAllLogs();
        }
        catch (NullPointerException e) {
            Toast.makeText(this, "No logs to show", Toast.LENGTH_SHORT).show();
            Log.e("Log: ", e.toString());
        }
        if(heldLogs.isEmpty()) return;
        ArrayAdapter<LogEntry> bookArrayAdapter = new ArrayAdapter<>(this, R.layout.item_book, R.id.book_item, heldLogs);
        manageListView.setAdapter(bookArrayAdapter);
    }
    private void generateHoldsAdapter() {
        List<Book> heldBooks = new ArrayList<>();
        try {
            heldBooks = db.books().getBooksWithHolder();
        }
        catch (NullPointerException e) {
            Toast.makeText(this, "No books to show", Toast.LENGTH_SHORT).show();
            Log.e("Books: ", e.toString());
        }
        if(heldBooks.isEmpty()) return;
        ArrayAdapter<Book> bookArrayAdapter = new ArrayAdapter<>(this, R.layout.item_book, R.id.book_item, heldBooks);
        manageListView.setAdapter(bookArrayAdapter);
    }

    @Override
    public void onBookCreation(String title, String author, String genre) {
        DialogFragment dialogFragment = BookDialog.newInstance(title, author, genre);
        dialogFragment.show(getSupportFragmentManager(), "");
        return;
    }
}
