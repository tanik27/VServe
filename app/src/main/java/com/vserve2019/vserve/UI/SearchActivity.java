package com.vserve2019.vserve.UI;
import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.vserve2019.vserve.Model.Util;
import com.vserve2019.vserve.R;
import java.util.List;
public class SearchActivity extends AppCompatActivity implements LocationListener {
    TextView txtLocation,txtOrder;
    Button btnFetchLocation;
    LocationManager locationManager;
    ProgressDialog progressDialog;
    void initViews(){
        txtLocation=findViewById(R.id.textViewLocation);
        txtOrder=findViewById(R.id.textViewOrder);
        txtOrder.setVisibility(View.INVISIBLE);
        btnFetchLocation=findViewById(R.id.buttonFetch);
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Fetching Location");
        locationManager=(LocationManager) getSystemService(LOCATION_SERVICE);
        txtOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SearchActivity.this,VserveActivity.class);
                startActivity(intent);
            }
        });
        btnFetchLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(SearchActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(SearchActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(SearchActivity.this,"Please Grant Permissions in Settings and Try Again",Toast.LENGTH_LONG).show();
                }else {
                    progressDialog.show();
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1 * 60, 10, (LocationListener) SearchActivity.this);
                    txtOrder.setVisibility(View.VISIBLE);
                }
            }
        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initViews();
    }
    public void onLocationChanged(Location location) {
        locationManager.removeUpdates(this);
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        try {
            Geocoder geocoder = new Geocoder(this);
            List<Address> addresses = geocoder.getFromLocation(latitude,longitude,5);
            StringBuffer buffer = new StringBuffer();
            if(addresses!=null && addresses.size()>0) {
                Address address = addresses.get(0);
                for(int i=0;i<=address.getMaxAddressLineIndex();i++){
                    buffer.append(address.getAddressLine(i)+"\n");
                }
            }
            txtLocation.setText("Location: "+latitude+" : "+longitude+"\nAddress:"+buffer.toString());
            Util.data.add(buffer.toString());
        }catch (Exception e){
            e.printStackTrace();
        }
        progressDialog.dismiss();
    }
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }
    @Override
    public void onProviderEnabled(String provider) {
    }
    @Override
    public void onProviderDisabled(String provider) {
    }
}
