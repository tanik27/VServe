package com.vserve2019.vserve.UI;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.vserve2019.vserve.Adapter.HistoryAdapter;
import com.vserve2019.vserve.Listener.OnRecyclerListener;
import com.vserve2019.vserve.Model.History;
import com.vserve2019.vserve.Model.Util;
import com.vserve2019.vserve.R;
import java.util.ArrayList;
import java.util.List;
public class HistoryActivity extends AppCompatActivity implements OnRecyclerListener {
    private static final String TAG="HistoryActivity";
    RecyclerView recyclerView;
    ArrayList<History> history;
    HistoryAdapter historyAdapter;
    FirebaseAuth auth;
    FirebaseUser firebaseUser;
    FirebaseFirestore db;
    void initViews(){
        recyclerView = findViewById(R.id.RecyclerViewHistory);
        auth=FirebaseAuth.getInstance();
        db=FirebaseFirestore.getInstance();
        firebaseUser=auth.getCurrentUser();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        initViews();
        if (Util.isInternetConnected(this)){
            Toast.makeText(HistoryActivity.this,"Fetched From Cloud",Toast.LENGTH_LONG).show();
            //Log.i("HistoryActivity","Fetch");
            fetchOrderfromCloudDb();
        }else {
            Toast.makeText(HistoryActivity.this, "Please Connect to Internet and Try Again", Toast.LENGTH_LONG).show();
        }
    }
void fetchOrderfromCloudDb(){
        db.collection("Users").document(firebaseUser.getUid())
                .collection("Order").get()
                .addOnCompleteListener(this, new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                   if (task.isComplete()){
                       history= new ArrayList<>();
                       QuerySnapshot querySnapshot=task.getResult();
                       List<DocumentSnapshot> documentSnapshots=querySnapshot.getDocuments();
                       for (DocumentSnapshot snapshot:documentSnapshots){
                           String doc=snapshot.getId();
                           History history1=snapshot.toObject(History.class);
                           history1.docId=doc;
                           history.add(history1);
                       }

                       Toast.makeText(HistoryActivity.this,"Order:"+history.toString(),Toast.LENGTH_LONG).show();
                       historyAdapter=new HistoryAdapter(HistoryActivity.this,R.layout.historyresourcefile,history);
                       historyAdapter.setRecyclerItemListener(HistoryActivity.this);
                       LinearLayoutManager linearLayoutManager= new LinearLayoutManager(HistoryActivity.this);
                       recyclerView.setLayoutManager(linearLayoutManager);
                       recyclerView.setAdapter(historyAdapter);


                   }else {
                       Toast.makeText(HistoryActivity.this,"Some Error Occurred",Toast.LENGTH_LONG).show();
                   }
                    }
                });
}
    @Override
    public void OnIemClick(int position) {
        Toast.makeText(HistoryActivity.this,"You Selected On "+history.size(),Toast.LENGTH_LONG).show();
    }
}
