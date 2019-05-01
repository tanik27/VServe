package com.vserve2019.vserve.UI;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.vserve2019.vserve.Adapter.ServiceAdapter;
import com.vserve2019.vserve.Listener.OnRecyclerListener;
import com.vserve2019.vserve.Model.Services;
import com.vserve2019.vserve.Model.Util;
import com.vserve2019.vserve.R;
import java.util.ArrayList;
public class ServicesActivity extends AppCompatActivity implements OnRecyclerListener {
    private static final String TAG="ServicesActivity";
    RecyclerView recyclerView;
    ArrayList<Services> services;
    ServiceAdapter serviceAdapter;
    FirebaseAuth auth;
    FirebaseUser firebaseUser;
    FirebaseFirestore db;
    Button btnProceed;
    CheckBox chCash;
    void initViews(){
        chCash=findViewById(R.id.checkBoxCash);
        btnProceed=findViewById(R.id.buttonProceed);
        btnProceed.setVisibility(View.INVISIBLE);
        chCash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnProceed.setVisibility(View.VISIBLE);
            }
        });
        btnProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(ServicesActivity.this,LastActivity.class);
                startActivity(intent);
            }
        });
        recyclerView=findViewById(R.id.RecyclerViewServices);
        auth=FirebaseAuth.getInstance();
        db=FirebaseFirestore.getInstance();
        firebaseUser=auth.getCurrentUser();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);
        initViews();
        if (Util.isInternetConnected(this)) {
            fetchServicesFromCloudDb();
        } else {
            Toast.makeText(ServicesActivity.this, "Please Connect to Internet and Try Again", Toast.LENGTH_LONG).show();
        }
    }
    void fetchServicesFromCloudDb(){
        db.collection("Services").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            services=new ArrayList<>();
                            for (
                                    QueryDocumentSnapshot documentSnapshot: task.getResult()){
                            Services servicess=documentSnapshot.toObject(Services.class);
                            services.add(servicess);
                            }
                            serviceAdapter=new ServiceAdapter(ServicesActivity.this,R.layout.servicesresourcefile,services);
                            serviceAdapter.setOnRecyclerItemListener(ServicesActivity.this);
                            LinearLayoutManager linearLayoutManager= new LinearLayoutManager(ServicesActivity.this);
                            recyclerView.setLayoutManager(linearLayoutManager);
                            recyclerView.setAdapter(serviceAdapter);
                        }else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                        Toast.makeText(ServicesActivity.this, "Total Services" + services.size(), Toast.LENGTH_LONG).show();
                    }
                });
    }
    @Override
    public void OnIemClick(int position) {
        Toast.makeText(ServicesActivity.this,"You Clicked On: "+services.toString(),Toast.LENGTH_LONG).show();
        Util.data.add(services.get(position).toString());

    }
}
