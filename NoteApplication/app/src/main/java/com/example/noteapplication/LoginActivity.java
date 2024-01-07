
package com.example.noteapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextUsername;
    private EditText editTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        Button buttonLogin = findViewById(R.id.buttonLogin);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Điều kiện đơn giản: kiểm tra xem tên người dùng và mật khẩu có dữ liệu không
                String username = editTextUsername.getText().toString();
                String password = editTextPassword.getText().toString();

                if (username.equals("toan") && password.equals("123")) {
                    // Đăng nhập thành công, chuyển đến MainActivity
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
//                    finish(); // Kết thúc LoginActivity để người dùng không thể quay lại từ MainActivity
                } else {
                    // Hiển thị thông báo đăng nhập thất bại
                    Toast.makeText(LoginActivity.this, "Login fail. Check username and password.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
