package com.vserve2019.vserve.UI;
import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.vserve2019.vserve.Model.Mechanic;
import com.vserve2019.vserve.Model.Order;
import com.vserve2019.vserve.Model.Services;
import com.vserve2019.vserve.Model.User;
import com.vserve2019.vserve.Model.Util;
import com.vserve2019.vserve.R;
import java.util.ArrayList;
public class LastActivity extends AppCompatActivity {
    TextView txtLocation,txtMechanicName,txtMechanicPhone,txtMechanicAddress
            ,txtService,txtService2;
    Button btnDone;
    ArrayList arrayList;
    Order order;
    Services services;
    User user;
    FirebaseAuth auth;
    FirebaseFirestore db;
    FirebaseUser firebaseUser;
    ProgressDialog progressDialog;
    Mechanic mechanic;
    String name;
    void initViews(){
        auth=FirebaseAuth.getInstance();
        db=FirebaseFirestore.getInstance();
        firebaseUser=auth.getCurrentUser();
        progressDialog=new ProgressDialog(this);
        order=new Order();
        txtLocation=findViewById(R.id.textViewLocationShow);
        txtMechanicName=findViewById(R.id.textViewMechanicName);
        txtMechanicPhone=findViewById(R.id.textViewMechanicPhone);
        txtMechanicAddress=findViewById(R.id.textViewMechanicAddress);
        txtService=findViewById(R.id.textViewServices);
        txtService2=findViewById(R.id.textViewServicesno2);
        btnDone=findViewById(R.id.buttonDone);
        arrayList= Util.data;
        mechanic= new Mechanic();
        user=new User();
        services=new Services();
        int size=arrayList.size();
        txtLocation.setText(arrayList.get(0).toString());
        mechanic = (Mechanic) arrayList.get(1);
        txtMechanicName.setText(mechanic.Name);
        txtMechanicPhone.setText(mechanic.Phone);
        txtMechanicAddress.setText(mechanic.Address);
        StringBuffer buffer=new StringBuffer();
        for (int i=2; i<Util.data.size();i++){
            buffer.append(Util.data.get(i).toString()+"\n");
        }
        txtService.setText(buffer.toString());

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                order.Location=txtLocation.getText().toString();
                order.MechanicName=txtMechanicName.getText().toString();
                order.MechanicPhone=txtMechanicPhone.getText().toString();
                order.MechanicAddress=txtMechanicAddress.getText().toString();
                order.Service=txtService.getText().toString();
                if (Util.isInternetConnected(LastActivity.this)){
                    Toast.makeText(LastActivity.this,"Coonected to the internet",Toast.LENGTH_LONG).show();
                    saveCustomerinClouddb();
                    Toast.makeText(LastActivity.this,"Data Saved ",Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(LastActivity.this,"Please Connect to the Internet",Toast.LENGTH_LONG).show();
                }

            }
        });
    }
    void saveCustomerinClouddb(){
        progressDialog.show();
        db.collection("Users").document(firebaseUser.getUid()).collection("Order").add(order)
             .addOnCompleteListener(this, new OnCompleteListener<DocumentReference>() {
                 @Override
                 public void onComplete(@NonNull Task<DocumentReference> task) {
                   //  Toast.makeText(LastActivity.this,"hello",Toast.LENGTH_LONG).show();
                     if (task.isComplete()){
                         // Fetch User data from Firebase and use it in SMS Code
                         Toast.makeText(LastActivity.this,"Order",Toast.LENGTH_LONG).show();
                         SmsManager smsManager=SmsManager.getDefault();
                         smsManager.sendTextMessage(mechanic.Phone,null,"Name"+mechanic.Name+" Phone no.:"+user.Phone+" Service:"+services.Name+" Price"+services.Price,null,null);
                         Log.i("Send message",mechanic.Phone);
                     }
                 }
             }) ;
        progressDialog.dismiss();
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_last);
        initViews();
    }
}
