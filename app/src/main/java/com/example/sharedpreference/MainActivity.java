package com.example.sharedpreference;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Handler handler = new Handler();
    private SharedPrefManager sharedPrefManager;
    private EditText etUsername, etPassword;
    private Button btnLogin;
    private ProgressBar pbLogin;
    private ImageView icLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPrefManager = new SharedPrefManager(this);

        if (sharedPrefManager.getIsLogin()){
            // kondisi jika user sudah login
            startHomeUI();
        } else {
            // kondisi jika user belum login
            etUsername = findViewById(R.id.etUsername);
            etPassword = findViewById(R.id.etPassword);
            btnLogin = findViewById(R.id.btnLogin);
            pbLogin = findViewById(R.id.pbLogin);
            icLogin = findViewById(R.id.icLogin);

            login();


        }

    }

    public void startHomeUI() {
        Intent i = new Intent(MainActivity.this, HomeActivity.class);
        startActivity(i);
        finishAffinity();
    }

    public void login() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // ambil value dari edit text
                final String username = etUsername.getText().toString();
                final String password = etPassword.getText().toString();

                pbLogin.setVisibility(View.VISIBLE);
                icLogin.setVisibility(View.GONE);

                if (username.isEmpty() || password.isEmpty()) {
                    pbLogin.setVisibility(View.GONE);
                    icLogin.setVisibility((View.VISIBLE));
                    Toast.makeText(MainActivity.this, "Username dan password tidak boleh kosong",Toast.LENGTH_LONG).show();
                } else {
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            String spUsername = sharedPrefManager.getUsername();
                            String spPassword = sharedPrefManager.getPassword();

                            Log.d("Username", "user"+username);
                            Log.d("Password", "password"+password);

                            if (username.equals(spUsername) && password.equals(spPassword)){
                                Intent i = new Intent(MainActivity.this, HomeActivity.class);
                                sharedPrefManager.saveIsLogin(true);
                                startActivity(i);

                            } else {
                                pbLogin.setVisibility(View.GONE);
                                icLogin.setVisibility(View.VISIBLE);
                                Toast.makeText(MainActivity.this, "Username dan password salah", Toast.LENGTH_LONG).show();
                            }
                        }
                    }, 3000);
                }
            }
        });
    }
}