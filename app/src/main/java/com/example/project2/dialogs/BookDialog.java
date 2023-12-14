package com.example.project2.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.project2.ManageActivity;
import com.example.project2.database.LibaryDatabase;
import com.example.project2.databinding.DialogBookBinding;
import com.example.project2.models.Book;
import com.example.project2.models.LogEntry;

public class BookDialog extends DialogFragment {
    private DialogBookBinding binding;
    private LibaryDatabase db;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        binding = DialogBookBinding.inflate(LayoutInflater.from(getContext()));
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        db = LibaryDatabase.getInstance(getContext());

        Book newBook = new Book(getArguments().getString("newTitle"),getArguments().getString("newAuthor"),getArguments().getString("newGenre"));
        binding.titleName.setText("Title: " + newBook.getTitle());
        binding.userName.setText("Author: " + newBook.getAuthor());
        binding.ReservationNum.setText("genre: " + newBook.getGenre());
        builder.setView(binding.getRoot()).setTitle("Add book").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                LogEntry temp = new LogEntry("Book Added: ", newBook.toString());
                db.logs().insertLog(temp);
                db.books().insert(newBook);
                Toast.makeText(getContext(), "Add book Success", Toast.LENGTH_SHORT).show();
                ((ManageActivity) getActivity()).updateList();
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Just get rid of the Dialog..
                Toast.makeText(getContext(), "Add book Canceled", Toast.LENGTH_SHORT).show();
                return;
            }
        });
        return builder.create();
    }
    public static BookDialog newInstance(String title, String author, String genre) {
        BookDialog etd = new BookDialog();
        Bundle args = new Bundle();
        args.putString("newTitle", title);
        args.putString("newAuthor", author);
        args.putString("newGenre", genre);
        etd.setArguments(args);
        return etd;
    }
}
