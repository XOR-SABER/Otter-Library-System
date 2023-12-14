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
import com.example.project2.databinding.LibraryActivityBinding;
import com.example.project2.dialogs.LoginAccountDialog;
import com.example.project2.dialogs.NotifyDialog;
import com.example.project2.dialogs.ReserveDialog;
import com.example.project2.dialogs.SearchBooksDialog;
import com.example.project2.models.Book;

import java.util.List;

public class LibraryActivity extends AppCompatActivity implements SearchBooksDialog.SearchBooksDialogListener,
        LoginAccountDialog.LoginAccountListener, NotifyDialog.NotifyDialogListener {
    private LibraryActivityBinding binding;
    private LibaryDatabase db;

    private List<Book> allBooks;
    private ListView bookListView;

    private Book currentBook;
    private boolean heldIsShown = false;

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = LibraryActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        db = LibaryDatabase.getInstance(this);
        allBooks = db.books().getBooksWithoutHolder();
        bookListView = binding.allbooks;
        generateAdapter(allBooks);
        binding.byGenre.setOnClickListener(v -> {
            DialogFragment dialogFragment = SearchBooksDialog.newInstance("Genre");
            dialogFragment.show(getSupportFragmentManager(), "");
        });
        binding.byAuthor.setOnClickListener(v -> {
            DialogFragment dialogFragment = SearchBooksDialog.newInstance("Author");
            dialogFragment.show(getSupportFragmentManager(), "");
        });
        bookListView.setOnItemClickListener((parent, view, position, id) -> {
            currentBook = (Book) bookListView.getItemAtPosition(position);
            if(currentBook.getHolder() == null) {
                DialogFragment dialogFragment = LoginAccountDialog.newInstance();
                dialogFragment.show(getSupportFragmentManager(), "");
            } else {
                DialogFragment dialogFragment = NotifyDialog.newInstance("Book Reserved please pick another");
                dialogFragment.show(getSupportFragmentManager(), "");
            }
        });
    }

    // We only need to create this when we need it..
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.manage_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.back_button) {
            finish();
            return true;
        }
        if (item.getItemId() == R.id.show_held) {
            if(heldIsShown) {
                heldIsShown = false;
                allBooks = db.books().getBooksWithoutHolder();
                generateAdapter(allBooks);
            } else {
                heldIsShown = true;
                allBooks = db.books().getAllBooks();
                generateAdapter(allBooks);
            }
        }
        return super.onOptionsItemSelected(item);

    }

    private void generateAdapter(List<Book> bookList) {
        ArrayAdapter<Book> bookArrayAdapter = new ArrayAdapter<>(this, R.layout.item_book, R.id.book_item, bookList);
        bookListView.setAdapter(bookArrayAdapter);
    }

    public void refreshListView() {
        generateAdapter(db.books().getBooksWithoutHolder());
    }

    @Override
    public void onSearchClicked(String searchType, String searchString) {
        List<Book> list;
        if(searchType.equals("Author")) list = db.books().getBooksByAuthor(searchString);
        else list = db.books().getBooksByGenre(searchString);
        if(list.isEmpty()) {
            DialogFragment dialogFragment = NotifyDialog.newInstance("Cannot find " + searchType);
            dialogFragment.show(getSupportFragmentManager(), "");
            return;
        }
        generateAdapter(list);
    }

    @Override
    public void onLoginClicked(String userName, String passWord) {
        Log.i("It work", userName);
        int UserID = db.users().getUserIdByUsernameAndPassword(userName,passWord);
        int bookID = db.books().getIdByBook(currentBook.getTitle(),currentBook.getAuthor(),currentBook.getGenre());
        DialogFragment dialogFragment = ReserveDialog.newInstance(UserID, bookID);
        dialogFragment.show(getSupportFragmentManager(), "");
    }

    @Override
    public void onReturnToMain() {
        finish();
    }
}
