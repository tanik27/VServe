package com.vserve2019.vserve.UI;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.vserve2019.vserve.R;
public class SplashActivity extends AppCompatActivity {
    TextView txtView;
    FirebaseAuth auth;
    FirebaseUser firebaseUser;
    Animation animation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        handler.sendEmptyMessageDelayed(101,3000);
        getSupportActionBar().hide();
        animation= AnimationUtils.loadAnimation(this,R.anim.alpha);
        txtView=findViewById(R.id.textViewSplash);
        txtView.startAnimation(animation);
        auth=FirebaseAuth.getInstance();
        firebaseUser =auth.getCurrentUser();
        if (firebaseUser==null){
            handler.sendEmptyMessageDelayed(101,3000);
        }else {
            handler.sendEmptyMessageDelayed(201,3000);
        }
    }
    Handler handler=new Handler(){
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            if (msg.what==101){
                Intent intent=new Intent(SplashActivity.this, RegistrationActivity.class);
                startActivity(intent);
                finish();
            }else {
                Intent intent = new Intent(SplashActivity.this, SearchActivity.class);
                startActivity(intent);
                finish();
            }
        }
    };


}
