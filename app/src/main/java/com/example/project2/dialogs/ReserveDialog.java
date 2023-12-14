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

import com.example.project2.LibraryActivity;
import com.example.project2.database.LibaryDatabase;
import com.example.project2.databinding.DialogReserveBinding;
import com.example.project2.models.Book;
import com.example.project2.models.LogEntry;
import com.example.project2.models.User;

public class ReserveDialog extends DialogFragment {
    private DialogReserveBinding binding;
    private LibaryDatabase db;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        binding = DialogReserveBinding.inflate(LayoutInflater.from(getContext()));
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        db = LibaryDatabase.getInstance(getContext());
        int bookID = getArguments().getInt("bookID");
        int userID = getArguments().getInt("UserID");

        Book currentBook = db.books().getBookById(bookID);
        User currentUser = db.users().getUserById(userID);
        binding.titleName.setText("Title: " + currentBook.getTitle());
        binding.userName.setText("User: " + currentUser.getUsername());
        int reservationNum = (currentUser.getUsername() + currentBook.getTitle()).hashCode();
        binding.ReservationNum.setText("Reservation Number: " + Integer.toString(reservationNum));
        builder.setView(binding.getRoot()).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                currentBook.setHolder(currentUser.getUsername());
                LogEntry temp = new LogEntry("Book Reserved", currentUser.getUsername() + " has reserved " + currentBook.getTitle() + " : Reservation Number : " + Integer.toString(reservationNum));
                db.logs().insertLog(temp);
                db.books().update(currentBook);
                Toast.makeText(getContext(), "Reservation Success", Toast.LENGTH_SHORT).show();
                ((LibraryActivity) getActivity()).refreshListView();
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Just get rid of the Dialog..
                Toast.makeText(getContext(), "Reservation Canceled", Toast.LENGTH_SHORT).show();
                return;
            }
        });
        return builder.create();
    }
    public static ReserveDialog newInstance(int userID, int bookID) {
        ReserveDialog etd = new ReserveDialog();
        Bundle args = new Bundle();
        args.putInt("UserID", userID);
        args.putInt("bookID", bookID);
        etd.setArguments(args);
        return etd;
    }
}
