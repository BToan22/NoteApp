
package com.example.noteapplication;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextUsername;
    private EditText editTextPassword;
    LoginBroadcastReceiver myReceiver = new LoginBroadcastReceiver();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        Button buttonLogin = findViewById(R.id.buttonLogin);
        IntentFilter filter = new IntentFilter("Action123");
        this.registerReceiver(myReceiver, filter);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = editTextUsername.getText().toString();
                String password = editTextPassword.getText().toString();

                if (username.equals("toan") && password.equals("123")) {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);

                    finish();
                    onClickShowBroadcast(v);

                } else {
                    Toast.makeText(LoginActivity.this, "Login fail. Check username and password.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void onClickShowBroadcast(View view) {
        EditText st = findViewById(R.id.editTextUsername);
        Intent intent = new Intent();
        intent.putExtra("msg", (CharSequence) st.getText().toString());
        intent.setAction("Action123");
        sendBroadcast(intent);
    }
}
