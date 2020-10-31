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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

//TODO: location enter start-date&end-date [CALENDAR]

public class HostNewCarActivity extends AppCompatActivity {

    //requestcode constants
    private final int CAMERA_CODE = 1;
    private final int GALLERY_CODE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host_new_car);

        //get extra data: is this a car for sale?
        boolean salePage = getIntent().getBooleanExtra("SELLNEW",false);
        if(salePage){ //if new car is for sale, switch displays
            Button rangePickButton = findViewById(R.id.hostnew_btn_AvailableDate);
            EditText carPriceTxt = findViewById(R.id.hostnew_txt_price);
            TextView carPriceLbl = findViewById(R.id.hostnew_lbl_price);
            TextView rentSellLbl = findViewById(R.id.hostnew_lbl_RentSell);
            TextView fareLbl = findViewById(R.id.hostnew_lbl_FareBy);
            rangePickButton.setVisibility(View.GONE);
            fareLbl.setVisibility(View.GONE);
            rentSellLbl.setText("Put new car for sale");
            carPriceLbl.setText("Car Price ($): ");
            carPriceTxt.setHint("Price of car");
        }

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

        //button to generate DateRangePicker dialog for car's availability
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
        });

        //cancel button- ends the activity
        Button cancelBtn = findViewById(R.id.hostnew_btn_cancel);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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