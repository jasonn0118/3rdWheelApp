package com.example.a3rdwheel;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.util.Pair;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

//TODO: location enter start-date&end-date [CALENDAR]

public class HostNewCarActivity extends AppCompatActivity {

    //requestcode constants
    private final int CAMERA_CODE = 1;
    private final int GALLERY_CODE = 2;

    private DatabaseReference database;
    private FirebaseDatabase fbInstance;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host_new_car);

        //Firebase Reference
        fbInstance = FirebaseDatabase.getInstance();
        database = fbInstance.getReference("cars");

        //imageview onClick: Select photo of car -> Dialogbox
        ImageView newCarImage = findViewById(R.id.hostnew_img_Car);
        newCarImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*  Creates a dialogbox, giving the user option to take photo from camera or select an image
                    in phone's gallery. (REQUIRES MANIFEST PERMISSIONS.) The dialogbox will start an intent opening the
                    camera/gallery and once completed will return with result from the activity
                 */
                CharSequence[] options = { "Use Camera", "Select from Gallery","Cancel" };
                AlertDialog.Builder builder = new AlertDialog.Builder(HostNewCarActivity.this);
                builder.setTitle("Add Photo!");
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if (options[item].equals("Use Camera")) {   //if use camera option
                            //check camera permission
                            if(ContextCompat.checkSelfPermission(HostNewCarActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                                // Permission is not granted -> request camera
                                ActivityCompat.requestPermissions(HostNewCarActivity.this, new String[]{Manifest.permission.CAMERA}, CAMERA_CODE);
                            }

                            //check again if permission is given
                            if(ContextCompat.checkSelfPermission(HostNewCarActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                                startActivityForResult(cameraIntent, CAMERA_CODE);
                            }

                        }
                        else if (options[item].equals("Select from Gallery")) {
                            Intent intent = new   Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(intent, GALLERY_CODE);
                        }
                        else if (options[item].equals("Cancel")) {  //if cancel, do nothing
                            dialog.dismiss();
                        }
                    }
                });
                builder.show();
            }
        });

        /*/button to generate DateRangePicker dialog for car's availability
        Button rangePickButton = findViewById(R.id.hostnew_btn_AvailableDate);
        rangePickButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //create a date range picker dialog
                MaterialDatePicker.Builder<Pair<Long,Long>> builder = MaterialDatePicker.Builder.dateRangePicker();
                MaterialDatePicker<Pair<Long,Long>> rangePicker = builder.build();
                rangePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Pair<Long,Long>>() {
                    @Override
                    public void onPositiveButtonClick(Pair<Long, Long> selection) {
                        TextView displayDateSelected = findViewById(R.id.hostnew_txt_AvailableDate);
                        String startDate = new java.text.SimpleDateFormat("MM/dd/yyyy").format(new java.util.Date (selection.first));
                        String endDate = new java.text.SimpleDateFormat("MM/dd/yyyy").format(new java.util.Date (selection.second));
                        displayDateSelected.setText("Car available from "+startDate+" to "+endDate+".");
                    }
                });
                rangePicker.show(getSupportFragmentManager(),"RangePicker");
            }
        });*/

        //cancel button- ends the activity
        Button cancelBtn = findViewById(R.id.hostnew_btn_cancel);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //confirm button
        Button confirmBtn = findViewById(R.id.hostnew_btn_add);
        confirmBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //get datas from interface
                TextView nameTxt = findViewById(R.id.hostnew_txt_name);
                TextView modelTxt = findViewById(R.id.hostnew_txt_CarModel);
                TextView typeTxt = findViewById(R.id.hostnew_txt_CarType);
                String carname = nameTxt.getText().toString();
                String carmodel = modelTxt.getText().toString();
                String carType = typeTxt.getText().toString();

                //TODO: Image handling?

                if(carname.isEmpty() || carmodel.isEmpty() || carType.isEmpty()){
                    //missing data
                    Toast.makeText(getBaseContext(),"Car is missing data!",Toast.LENGTH_LONG).show();
                    return;
                }

                //get user key
                if (TextUtils.isEmpty(userId)) {
                    userId = database.push().getKey();
                }

                //Car Object
                FirebaseCar newCar = new FirebaseCar(carType,carname,carmodel);

                //send to Firebase
                database.child(userId).setValue(newCar);
                Toast.makeText(getBaseContext(),"New car added!",Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }

    //onActivityResult, triggered when returning to this from camera/gallery select image.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ImageView carImage = findViewById(R.id.hostnew_img_Car);

        if (resultCode == RESULT_OK) {  //operation ok, continue - else do nothing
            if (requestCode == CAMERA_CODE) {
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                carImage .setImageBitmap(photo);

            } else if (requestCode == GALLERY_CODE) {  //select from Gallery
                Uri photo = data.getData();
                carImage.setImageURI(photo);
            }
        }
    }
}