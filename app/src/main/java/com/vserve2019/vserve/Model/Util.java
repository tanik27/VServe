package com.vserve2019.vserve.Model;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.vserve2019.vserve.UI.VserveActivity;
import java.util.ArrayList;
public class Util {
    public static final int DB_Version=1;
    public static boolean isInternetConnected(Context context){
        ConnectivityManager connectivityManager=(ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
        return (networkInfo!=null&& networkInfo.isConnected());
    }
    public static Util getInstance(VserveActivity vserveActivity) {
        return null;
    }
    public static ArrayList data = new ArrayList<>();
}
