package com.example.project2;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.project2.database.LibaryDatabase;
import com.example.project2.databinding.CreateAccountActivityBinding;
import com.example.project2.models.LogEntry;
import com.example.project2.models.User;

public class CreateAccountActivity extends AppCompatActivity {
    private CreateAccountActivityBinding binding;
    private int failedAttempts = 0;
    private LibaryDatabase db;
    public void onCreate(@Nullable Bundle savedInstanceState) {
        boolean shouldShowRefreshButton = false;
        super.onCreate(savedInstanceState);
        binding = CreateAccountActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        failedAttempts = 0;
        db = LibaryDatabase.getInstance(this);
        // db.populateInitialData();
        binding.createAccount.setOnClickListener(v -> {
            String userN = binding.usernameSec.getText().toString();
            String passW = binding.passwordSec.getText().toString();
            // Check Available
            if(userN.equals("!admin2"))  {
                Toast.makeText(this, "Cannot use Username!", Toast.LENGTH_SHORT).show();
                failedAttempt();
                return;
            }
            if(passW.length() == 0 || userN.length() == 0) {
                Toast.makeText(this, "One of the fields where left blank!", Toast.LENGTH_LONG).show();
                failedAttempt();
                return;
            }
            if(db.users().getUsernameCount(userN) > 0) {
                Toast.makeText(this, "Username Unavailable, please pick another", Toast.LENGTH_LONG).show();
                failedAttempt();
                return;
            }

            db.users().insert(new User(userN, passW));
            Toast.makeText(this, "Account created!", Toast.LENGTH_LONG).show();
            LogEntry temp = new LogEntry("Account Creation", "Created " + userN);
            db.logs().insertLog(temp);
            finish();
        });
    }

    // We only need to create this when we need it..
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.back_button) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);

    }

    private void failedAttempt() {
        failedAttempts++;
        if(failedAttempts >= 2) {
            Toast.makeText(this, "Too many Failed Attempts! Try again!", Toast.LENGTH_LONG).show();
            finish();
        }
    }
}
