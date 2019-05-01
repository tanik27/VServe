package com.vserve2019.vserve.UI;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.vserve2019.vserve.Adapter.MechanicAdapter;
import com.vserve2019.vserve.Listener.OnRecyclerListener;
import com.vserve2019.vserve.Model.Mechanic;
import com.vserve2019.vserve.Model.User;
import com.vserve2019.vserve.Model.Util;
import com.vserve2019.vserve.R;
import java.util.ArrayList;
public class VserveActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnRecyclerListener {
    private static final String TAG="VServeActivity";
    //TextView txtName,txtEmail;
    //Mechanic mechanic;
    ArrayList<Mechanic> mechanics;
    RecyclerView recyclerView;
    MechanicAdapter mechanicAdapter;
    FirebaseAuth auth;
    FirebaseFirestore db;
    FirebaseUser firebaseUser;
    FirebaseFirestore firebaseFirestore;
    void initViews(){
//        txtName=findViewById(R.id.TextViewName);
//        txtEmail=findViewById(R.id.textViewEmail);
        recyclerView=findViewById(R.id.RecyclerViewMechanic);
        auth=FirebaseAuth.getInstance();
        db=FirebaseFirestore.getInstance();
        firebaseUser=auth.getCurrentUser();
        firebaseFirestore=FirebaseFirestore.getInstance();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vserve);
        initViews();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (Util.isInternetConnected(this)){
            fetchMechanicsFromClouddb();
        }else {
            Toast.makeText(VserveActivity.this,"Please Connect to the Internet",Toast.LENGTH_LONG).show();
        }
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //getCurrentUserDetail();
    }
//    void getCurrentUserDetail(){
//        String uid=auth.getCurrentUser().getUid();
//        firebaseFirestore.collection("Users").document(uid).get()
//                .addOnCompleteListener(this, new OnCompleteListener<DocumentSnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                        if (task.isComplete()){
//                            DocumentSnapshot documentSnapshot=task.getResult();
//                            User user=documentSnapshot.toObject(User.class);
//                            Log.i("fetch name",user.Name);
//                            Log.i("fetch email",user.Email);
////                            txtName.setText(user.Name);
////                            txtEmail.setText(user.Email);
//                        }
//                    }
//                });
 //   }
    void  fetchMechanicsFromClouddb(){
        db.collection("Mechanics").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                     if (task.isSuccessful()){
                         mechanics= new ArrayList<>();
                         for (
                                 QueryDocumentSnapshot document : task.getResult()){
                             String doc =document.getId();
                             Mechanic mechanic= document.toObject(Mechanic.class);
                             mechanics.add(mechanic);
                             mechanicAdapter = new MechanicAdapter(VserveActivity.this,R.layout.mechanicsresourcefile,mechanics);
                             mechanicAdapter.setOnRecyclerItemListener(VserveActivity.this);
                             LinearLayoutManager linearLayoutManager= new LinearLayoutManager(VserveActivity.this);
                             recyclerView.setLayoutManager(linearLayoutManager);
                             recyclerView.setAdapter(mechanicAdapter);
                         }
                     }else {
                         Log.d(TAG,"Error Getting Document:", task.getException());
                     }
                     Toast.makeText(VserveActivity.this,"Shops"+mechanics.size(),Toast.LENGTH_LONG).show();
                    }
                });
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        menu.add(1,101,0,"Log Out");
        getMenuInflater().inflate(R.menu.vserve, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        if( item.getItemId()==101){
            auth.signOut(); // Clear Logged In Users Data from Shared Preferences
            Intent intent = new Intent(VserveActivity.this, SplashActivity.class);
            startActivity(intent);
//            finish();
        }
        //noinspection SimplifiableIfStatement
        return super.onOptionsItemSelected(item);
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_Home) {
        } else if (id == R.id.nav_Search) {
            Intent intent=new Intent(VserveActivity.this,SearchActivity.class);
            startActivity(intent);
            finish();
        } else if (id == R.id.nav_Notification) {
        } else if (id == R.id.nav_History) {
            Intent intent = new Intent(VserveActivity.this, HistoryActivity.class);
            startActivity(intent);
            finish();
        }
//         else if (id == R.id.nav_share) {
//
//        } else if (id == R.id.nav_send) {
//
//        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    public void OnIemClick(int position) {
        Util.data.add(mechanics.get(position));
        Intent intent=new Intent(VserveActivity.this,ServicesActivity.class);
        startActivity(intent);
        finish();
    }
}
