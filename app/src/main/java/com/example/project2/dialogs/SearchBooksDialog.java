package com.example.project2.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.project2.databinding.DialogSearchBinding;

public class SearchBooksDialog extends DialogFragment {
    private DialogSearchBinding binding;

    public interface SearchBooksDialogListener {
        void onSearchClicked(String searchType, String searchString);
    }

    SearchBooksDialogListener listener;
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (SearchBooksDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString() + " must implement NoticeDialogListener");
        }

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        binding = DialogSearchBinding.inflate(LayoutInflater.from(getContext()));
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        String type = getArguments().getString("SearchType");

        builder.setView(binding.getRoot()).setTitle("Search by " + type).setPositiveButton("Search", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String returnVal = binding.sText.getText().toString();
                listener.onSearchClicked(type, returnVal);
            }
        });
        return builder.create();
    }
    public static SearchBooksDialog newInstance(String type) {
        SearchBooksDialog etd = new SearchBooksDialog();
        Bundle args = new Bundle();
        args.putString("SearchType", type);
        etd.setArguments(args);
        return etd;
    }
}
