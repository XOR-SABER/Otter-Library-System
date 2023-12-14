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
import com.example.project2.database.LibaryDatabase;
import com.example.project2.databinding.DialogLoginBinding;

public class LoginAccountDialog extends DialogFragment {
    private DialogLoginBinding binding;
    private LibaryDatabase db;

    public interface LoginAccountListener {
        void onLoginClicked(String userName, String passWord);
    }

    LoginAccountDialog.LoginAccountListener listener;
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (LoginAccountDialog.LoginAccountListener) context;
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

        builder.setView(binding.getRoot()).setPositiveButton("Login", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String userN = binding.usernameSec.getText().toString();
                String passW = binding.passwordSec.getText().toString();
                // Check Available
                if(userN.equals("!admin2"))  {
                    Toast.makeText(getContext(), "Cannot use Username!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(passW.length() == 0 || userN.length() == 0) {
                    Toast.makeText(getContext(), "One of the fields where left blank!", Toast.LENGTH_LONG).show();
                    return;
                }
                if(db.users().getUserByUsernameAndPassword(userN,passW) == null) {
                    Toast.makeText(getContext(), "One of fields is incorrect!", Toast.LENGTH_LONG).show();
                    return;
                }
                // if all else is true then
                LogEntry temp = new LogEntry("Account login", userN + " has logged in");
                db.logs().insertLog(temp);
                listener.onLoginClicked(userN, passW);
            }
        });
        return builder.create();
    }
    public static LoginAccountDialog newInstance() {
        LoginAccountDialog etd = new LoginAccountDialog();
        return etd;
    }
}

