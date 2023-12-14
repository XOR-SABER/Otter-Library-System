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
import com.example.project2.databinding.DialogReturnBinding;
import com.example.project2.models.Book;
import com.example.project2.models.LogEntry;

public class returnDialog extends DialogFragment {
    private DialogReturnBinding binding;
    private LibaryDatabase db;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        binding = DialogReturnBinding.inflate(LayoutInflater.from(getContext()));
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        db = LibaryDatabase.getInstance(getContext());
        Book returnedBook = db.books().getBookById(getArguments().getInt("bookID"));
        builder.setView(binding.getRoot()).setTitle("Return book").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                returnedBook.setHolder(null);
                db.books().update(returnedBook);
                Toast.makeText(getContext(), "Book return Success", Toast.LENGTH_SHORT).show();
                ((ManageActivity) getActivity()).updateList();
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Just get rid of the Dialog..
                Toast.makeText(getContext(), "Book return Canceled", Toast.LENGTH_SHORT).show();
                return;
            }
        });
        return builder.create();
    }
    public static returnDialog newInstance(int bookID) {
        returnDialog etd = new returnDialog();
        Bundle args = new Bundle();
        args.putInt("bookID", bookID);
        etd.setArguments(args);
        return etd;
    }
}
