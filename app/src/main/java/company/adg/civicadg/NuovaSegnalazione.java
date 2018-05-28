package company.adg.civicadg;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.PowerManager;
import android.preference.DialogPreference;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class NuovaSegnalazione extends AppCompatActivity {


    static final int PICK_IMAGE = 100;
    private static final int REQUEST_LOCATION = 1;
    Geocoder geo;
    LocationManager locationManager;
    LocationListener locationListener;
    private ImageButton TakePosition;
    double latti = 0;
    double longi = 0;
    List<Address> address;
    String comune;
    TextView indirizzo;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuova_segnalazione);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);


        RadioGroup selezione = (RadioGroup) findViewById(R.id.RadioGravità);
        selezione.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.RadioRed) {
                    Toast.makeText(getApplicationContext(), "Alta",
                            Toast.LENGTH_SHORT).show();
                } else if (checkedId == R.id.RadioYellow) {
                    Toast.makeText(getApplicationContext(), "Media",
                            Toast.LENGTH_SHORT).show();
                } else if (checkedId == R.id.RadioGreen) {
                    Toast.makeText(getApplicationContext(), "Bassa",
                            Toast.LENGTH_SHORT).show();
                }

            }
        });


        ImageButton PhotoButton = (ImageButton) findViewById(R.id.imageButton);
        PhotoButton.setOnClickListener(PhotoButtonListener);

        ImageButton addPhoto = (ImageButton) findViewById(R.id.addPhotoButton);
        addPhoto.setOnClickListener(addPhotoListener);

        TakePosition = (ImageButton) findViewById(R.id.LocButton);
        geo = new Geocoder(this, Locale.getDefault());

        indirizzo = (TextView) findViewById(R.id.TextAddress);
        configurebutton();



    }


    private void configurebutton() {
        TakePosition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                   /* Toast.makeText(getApplicationContext(),"NON VA"
                    ,Toast.LENGTH_SHORT).show();*/
                    buildAlertMessageNOps();

                }else if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
                    getLocation();
                }
                if (latti == 0 && longi == 0){

                }else{
                    Log.d("latitudine",""+latti);
                    try{

                        address = geo.getFromLocation(latti,longi,1);
                        comune = address.get(0).getLocality(); Log.d("latitudine",""+comune);
                        indirizzo.setText(""+comune);
                    }catch(IOException e){
                        e.printStackTrace();
                    }
                }
            }
        });

    }

    protected void buildAlertMessageNOps(){

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Attiva gps")
        .setCancelable(false)
                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                }
                ).setNegativeButton("no", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        final AlertDialog alert = builder.create();
        alert.show();
    }


    private void getLocation(){
     //   PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
     //   PowerManager.WakeLock mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "LocationManagerService");
        if (ActivityCompat.checkSelfPermission(NuovaSegnalazione.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(NuovaSegnalazione.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(NuovaSegnalazione.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_LOCATION);
        }else{
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            Location location2 = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            Location location3 = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
        if(location!=null){
            latti = location.getLatitude();
            longi = location.getLongitude();
        }else if(location2!=null) {
            latti = location.getLatitude();
            longi = location.getLongitude();
            }else if(location3!=null){
            latti = location.getLatitude();
            longi = location.getLongitude();
        }
            else{
                Toast.makeText(getApplicationContext(),"NON VA"
                        ,Toast.LENGTH_SHORT).show();        }
            }
        }


    public View.OnClickListener SelezioneListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };


    public View.OnClickListener addPhotoListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            openGallery();
        }
    };


    private void openGallery() {

        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
        Toast.makeText(getApplicationContext(), "Foto Acquisita con successo",
                Toast.LENGTH_SHORT).show();
    }

    public View.OnClickListener PhotoButtonListener = new View.OnClickListener() {

        static final int REQUEST_IMAGE_CAPTURE = 1;

        @Override
        public void onClick(View v) {


            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }

        }

    };


}

