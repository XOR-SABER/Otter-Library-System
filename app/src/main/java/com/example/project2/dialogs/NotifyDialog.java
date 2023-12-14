package com.example.project2.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.project2.databinding.DialogNotifyBinding;

public class NotifyDialog extends DialogFragment {
    private DialogNotifyBinding binding;
    public interface NotifyDialogListener {
        void onReturnToMain();
    }
    NotifyDialog.NotifyDialogListener listener;
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (NotifyDialog.NotifyDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString() + " must implement NotifyDialogListener");
        }

    }
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        binding = DialogNotifyBinding.inflate(LayoutInflater.from(getContext()));
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        String title = getArguments().getString("message");
        builder.setView(binding.getRoot()).setTitle(title).setPositiveButton("Continue", (dialog, which) -> {

        }).setNegativeButton("Return to main menu", (dialog, which) -> {
            // Just get rid of the Dialog..
            listener.onReturnToMain();
        });
        return builder.create();
    }
    public static NotifyDialog newInstance(String message) {
        NotifyDialog etd = new NotifyDialog();
        Bundle args = new Bundle();
        args.putString("message", message);
        etd.setArguments(args);
        return etd;
    }
}
