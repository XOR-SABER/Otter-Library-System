package com.example.project2.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.project2.database.LibaryDatabase;
import com.example.project2.databinding.DialogAddbookBinding;
import com.example.project2.databinding.DialogLoginBinding;

public class AddBookDialog extends DialogFragment {
    private LibaryDatabase db;
    private DialogAddbookBinding binding;

    public interface BookDialogListener {
        void onBookCreation(String title, String author, String genre);
    }
    AddBookDialog.BookDialogListener listener;
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (AddBookDialog.BookDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString() + " must implement BookDialogListener");
        }

    }
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        db = LibaryDatabase.getInstance(getContext());
        binding = DialogAddbookBinding.inflate(LayoutInflater.from(getContext()));
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setView(binding.getRoot()).setPositiveButton("Add Book", new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which) {
                String title = binding.titleSec.getText().toString();
                String author = binding.authorSec.getText().toString();
                String genre = binding.genreSec.getText().toString();
                if(db.books().getIdByBook(title, author, genre) != 0) {
                    Toast.makeText(getContext(), "That book is already in the database", Toast.LENGTH_LONG).show();
                    return;
                }
                // Okay send the book back
                listener.onBookCreation(title, author, genre);
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                return;
            }
        });

        return builder.create();
    }

    public static AddBookDialog newInstance() {
        AddBookDialog etd = new AddBookDialog();
        return etd;
    }
}
