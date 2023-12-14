package com.example.project2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.example.project2.databinding.MainActivityBinding;
import com.example.project2.dialogs.LibarianLoginDialog;

public class MainActivity extends AppCompatActivity implements LibarianLoginDialog.LibarianLoginListener {

    private MainActivityBinding binding;

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = MainActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.CreateAccount.setOnClickListener(v -> {
            startActivity(new Intent(this, CreateAccountActivity.class));
        });
        binding.PlaceHold.setOnClickListener(v -> {
            startActivity(new Intent(this, LibraryActivity.class));
        });
        binding.Manage.setOnClickListener(v -> {
            DialogFragment dialogFragment = LibarianLoginDialog.newInstance();
            dialogFragment.show(getSupportFragmentManager(), "");
        });
    }

    @Override
    public void onLoginSuccessful() {
        Toast.makeText(this, "Admin Login Successful", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, ManageActivity.class));
    }
}
