package com.vserve2019.vserve.UI;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.vserve2019.vserve.Model.User;
import com.vserve2019.vserve.R;
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    EditText etxtPhone,etxtPassword;
    Button btnLogged;
    User user;
    ProgressDialog progressDialog;
    FirebaseAuth auth;
    void initViews(){
        etxtPhone=findViewById(R.id.editTextPhone);
        etxtPassword=findViewById(R.id.editTextPassword);
        btnLogged=findViewById(R.id.buttonLogged);
        btnLogged.setOnClickListener(this);
        user= new User();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait..");
        progressDialog.setCancelable(false);
        auth = FirebaseAuth.getInstance();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initViews();
    }
    @Override
    public void onClick(View v) {
        user.Email = etxtPassword.getText().toString();
        user.Password = etxtPassword.getText().toString();
        loginUser();
    }
    void loginUser() {
        progressDialog.show();
        auth.signInWithEmailAndPassword(user.Email,user.Password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            //        Task<AuthResult> authResultTask = auth.signInWithEmailAndPassword(user.Email, user.Password)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isComplete()) {
                    Intent intent = new Intent(LoginActivity.this, SearchActivity.class);
                    startActivity(intent);
                    finish();
                    //Log.i("Tag","hello rushil");
                }
            }

        });
    }
}
