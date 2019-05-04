package com.vserve2019.vserve.UI;
import android.app.ProgressDialog;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;
import com.vserve2019.vserve.Model.User;
import com.vserve2019.vserve.R;
public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener {
    EditText etxtName,etxtPhone,etxtEmail,etxtPassword;
    TextView txtLogin;
    Button btnRegister;
    User user;
    ProgressDialog progressDialog;
    FirebaseAuth auth;
    FirebaseFirestore db;
    FirebaseUser firebaseUser;
    FirebaseInstanceId firebaseInstanceId;
    FirebaseMessaging firebaseMessaging;
    void initViews(){
        etxtName=findViewById(R.id.editTextName);
        etxtPhone=findViewById(R.id.editTextPhone);
        etxtEmail=findViewById(R.id.editTextEmail);
        etxtPassword=findViewById(R.id.editTextPassword);
        btnRegister=findViewById(R.id.buttonRegister);
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Please Wait..");
        progressDialog.setCancelable(false);
        txtLogin=findViewById(R.id.textViewLogin);
        btnRegister.setOnClickListener(this);
        txtLogin.setOnClickListener(this);
        user=new User();
        auth=FirebaseAuth.getInstance();
        db=FirebaseFirestore.getInstance();
        firebaseMessaging=FirebaseMessaging.getInstance();
        firebaseInstanceId=FirebaseInstanceId.getInstance();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        initViews();
    }
    @Override
    public void onClick(View v) {
        user.Name=etxtName.getText().toString();
        user.Phone=etxtPhone.getText().toString();
        user.Email=etxtEmail.getText().toString();
        user.Password=etxtPassword.getText().toString();
        int id=v.getId();
        //fetchSigninMethodforMail();
        if (id == R.id.buttonRegister){
            user.Name=etxtName.getText().toString();
            user.Phone=etxtPhone.getText().toString();
            user.Email=etxtEmail.getText().toString();
            user.Password=etxtPassword.getText().toString();
            registerUser();
        }else {
            Intent intent=new Intent(RegistrationActivity.this,LoginActivity.class);
            startActivity(intent);
        }
    }
    void subscrieUserForCloudMessaging(){
        firebaseMessaging.subscribeToTopic("events").addOnCompleteListener(this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    getToken();
                }
            }
        });
    }
    void getToken(){
        firebaseInstanceId.getInstanceId().addOnCompleteListener(this, new OnCompleteListener<InstanceIdResult>() {
            @Override
            public void onComplete(@NonNull Task<InstanceIdResult> task) {
                if (task.isComplete()){
                    user.Token=task.getResult().getToken();
                    saveUserInCloudDB();
                }
            }
        });
    }
    void saveUserInCloudDB(){
        /*db.collection("Users").add(user)
                .addOnCompleteListener(this, new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        if(task.isComplete()){
                            Toast.makeText(RegistrationActivity.this,user.Name+ "Registered Sucessfully", Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();
                            Intent intent = new Intent(RegistrationActivity.this, HomeActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                });*/
        db.collection("Users").document(firebaseUser.getUid()).set(user)
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(RegistrationActivity.this,user.Name+ "Registered Sucessfully", Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                        Intent intent = new Intent(RegistrationActivity.this, SearchActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
    }
    void  registerUser(){
        progressDialog.show();
        auth.createUserWithEmailAndPassword(user.Email,user.Password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isComplete()){
                            firebaseUser=auth.getCurrentUser();
                            subscrieUserForCloudMessaging();
                            // saveUserInCloudDB();
                        }
                    }
                });
    }
    void fetchSigninMethodforMail(){
        auth.fetchSignInMethodsForEmail(etxtEmail.getText().toString())
                .addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
            @Override
            public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                boolean check = task.getResult().getSignInMethods().isEmpty();
                if (check ){
                    Toast.makeText(RegistrationActivity.this,"Already Registered",Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(RegistrationActivity.this,"Register Here",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
