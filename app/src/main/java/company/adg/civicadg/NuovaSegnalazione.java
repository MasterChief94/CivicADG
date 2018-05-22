package company.adg.civicadg;

import android.content.Intent;
import android.media.Image;
import android.provider.MediaStore;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class NuovaSegnalazione extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuova_segnalazione);

        RadioGroup selezione = (RadioGroup) findViewById(R.id.RadioGravità);
        selezione.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if(checkedId == R.id.RadioRed){
                    Toast.makeText(getApplicationContext(),"Alta",
                            Toast.LENGTH_SHORT).show();
                } else if (checkedId == R.id.RadioYellow){
                    Toast.makeText(getApplicationContext(),"Media",
                            Toast.LENGTH_SHORT).show();
                } else if (checkedId == R.id.RadioGreen){
                    Toast.makeText(getApplicationContext(),"Bassa",
                            Toast.LENGTH_SHORT).show();
                }

            }
        });


        ImageButton PhotoButton = (ImageButton) findViewById(R.id.imageButton);
        PhotoButton.setOnClickListener(PhotoButtonListener);

        ImageButton TakePosition = (ImageButton) findViewById(R.id.LocButton);

        //Button PhotoButton = (Button) findViewById(R.id.PhotoButton);
        //PhotoButton.setOnClickListener(PhotoButtonListener);
    }

    public View.OnClickListener SelezioneListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };

    public View.OnClickListener PhotoButtonListener = new View.OnClickListener() {

        static final int REQUEST_IMAGE_CAPTURE = 1;

        @Override
        public void onClick(View v){
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }

        }
        
    };
}

