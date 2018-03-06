package com.example.mihaelasolomon.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    public Button register;
    public EditText user;
    public EditText mail;
    public EditText pass;
    public EditText confirmPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        register = findViewById(R.id.button2);
        user = findViewById(R.id.editText3);
        mail = findViewById(R.id.editText4);
        pass = findViewById(R.id.editText5);
        confirmPass = findViewById(R.id.editText6);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user.getText().length() < 4) {
                    Toast.makeText(RegisterActivity.this, "Username-ul este prea scurt.", Toast.LENGTH_SHORT).show();
                }
                else if(!mail.getText().toString().contains("@") || !mail.getText().toString().contains(".")) {
                    Toast.makeText(RegisterActivity.this, "Mail-ul este invalid.", Toast.LENGTH_SHORT).show();
                }
                else if(pass.getText().length() < 4) {
                    Toast.makeText(RegisterActivity.this, "Parola este prea scurta.", Toast.LENGTH_SHORT).show();
                }
                /*else if(!pass.getText().toString().equals(confirmPass.getText().toString())) {
                    Toast.makeText(RegisterActivity.this, "Parolele nu sunt identice.", Toast.LENGTH_SHORT).show();
                }*/
                else {
                    DBHelper dbhelper = new DBHelper(getApplicationContext());
                    boolean x = dbhelper.createUser(user.getText().toString(), pass.getText().toString(), mail.getText().toString(), dbhelper, getApplicationContext());

                    RegisterActivity.this.startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                }
            }
        });
    }
}
