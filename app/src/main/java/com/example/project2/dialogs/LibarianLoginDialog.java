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

import com.example.project2.models.LogEntry;
import com.example.project2.R;
import com.example.project2.database.LibaryDatabase;
import com.example.project2.databinding.DialogLoginBinding;

public class LibarianLoginDialog extends DialogFragment {
    private DialogLoginBinding binding;
    private LibaryDatabase db;

    public interface LibarianLoginListener {
        void onLoginSuccessful();
    }

    LibarianLoginDialog.LibarianLoginListener listener;
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (LibarianLoginDialog.LibarianLoginListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString() + " must implement NoticeDialogListener");
        }

    }
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        binding = DialogLoginBinding.inflate(LayoutInflater.from(getContext()));
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        db = LibaryDatabase.getInstance(getContext());
        binding.LoginGreeting.setText(R.string.otter_library_administrator_login);

        builder.setView(binding.getRoot()).setPositiveButton("Login", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String userN = binding.usernameSec.getText().toString();
                String passW = binding.passwordSec.getText().toString();
                // Check Available
                if(passW.length() == 0 || userN.length() == 0) {
                    Toast.makeText(getContext(), "One of the fields where left blank!", Toast.LENGTH_LONG).show();
                    return;
                }
                if(db.users().getUserByUsernameAndPassword(userN,passW) == null) {
                    Toast.makeText(getContext(), "One of fields is incorrect!", Toast.LENGTH_LONG).show();
                    return;
                }
                if(!userN.equals("!admin2"))  {
                    Toast.makeText(getContext(), "Not Administrator", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!passW.equals("!admin2"))  {
                    Toast.makeText(getContext(), "Not Administrator", Toast.LENGTH_SHORT).show();
                    return;
                }
                // if all else is true then
                LogEntry temp = new LogEntry("Account Administrator login", userN + " has logged in");
                db.logs().insertLog(temp);
                listener.onLoginSuccessful();
            }
        });
        return builder.create();
    }
    public static LibarianLoginDialog newInstance() {
        LibarianLoginDialog etd = new LibarianLoginDialog();
        return etd;
    }
}
