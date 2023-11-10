package com.sova.example_recycleview;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class MainActivity extends AppCompatActivity {

    EditText eName, eEmail, ePhone,eId;
    TextView tImageURI;
    ImageView imageView;
    Button btInsert, btViewAll, btLoadImage, btGet, btSave;
    DbHelper dbHelper;
    private final int GALLERY_REQ_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        eId = findViewById(R.id.etId);
        eName = findViewById(R.id.etName);
        eEmail = findViewById(R.id.etEmail);
        ePhone = findViewById(R.id.etPhone);

        tImageURI = findViewById(R.id.tvImageUrl);
        imageView = findViewById(R.id.imageView);

        btInsert = findViewById(R.id.btInsert);
        btViewAll = findViewById(R.id.btViewAll);
        btLoadImage = findViewById(R.id.btLoadImg);
        btGet = findViewById(R.id.btGet);
        btSave = findViewById(R.id.btSave);

        dbHelper= new DbHelper(this);
        btLoadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //image load button
                Intent iGallery = new Intent(Intent.ACTION_PICK);
                iGallery.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(iGallery,GALLERY_REQ_CODE);
            }
        });
        btViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,UserList.class));
            }
        });
        btInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameTXT = eName.getText().toString();
                if (nameTXT.isEmpty()) { Toast.makeText(MainActivity.this,
                        "Fill data to add",
                        Toast.LENGTH_SHORT).show();
                    return;}
                String emailTXT = eEmail.getText().toString();
                String phoneTXT = ePhone.getText().toString();
                String imageTXT = tImageURI.getText().toString();
                if (imageTXT.isEmpty()) { Toast.makeText(MainActivity.this,
                        "Load image",
                        Toast.LENGTH_SHORT).show();
                    return;}
                try {
                    Boolean checkInsert = dbHelper.insertUserDetails(
                            nameTXT,
                            emailTXT,
                            phoneTXT,
                            imageTXT
                    );
                    if (checkInsert == true)
                        Toast.makeText(MainActivity.this,
                                "New data inserted",
                                Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(MainActivity.this,
                                "New data not inserted",
                                Toast.LENGTH_SHORT).show();

                } catch (Exception e) {
                    Toast.makeText(MainActivity.this,
                            e.toString(),
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
        btGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    String id = eId.getText().toString();
                    Cursor result = dbHelper.getData(id);

                    if (result.getCount()==0){
                        Toast.makeText(MainActivity.this,
                                "No data",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }

                    while (result.moveToNext()) {

                        eName.setText(result.getString(1));
                        eEmail.setText(result.getString(2));
                        ePhone.setText(result.getString(3));
                        tImageURI.setText(result.getString(4));
//                        imageView.setImageURI(Uri.parse(result.getString(4)));
                        Glide.with(imageView.getContext())
                        .load(Uri.parse(result.getString(4)))
                        .into(imageView);

                    }
                }
                catch (Exception e){
                    Toast.makeText(MainActivity.this,
                            e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }}

        });
        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idTXT = eId.getText().toString();
                String nameTXT = eName.getText().toString();
                String emailTXT = eEmail.getText().toString();
                String phoneTXT = ePhone.getText().toString();
                String imageTXT = tImageURI.getText().toString();
                try {
                    Boolean checkInsert = dbHelper.updateUserDetails(
                            idTXT,
                            nameTXT,
                            emailTXT,
                            phoneTXT,
                            imageTXT
                    );
                    if (checkInsert == true)
                        Toast.makeText(MainActivity.this,
                                "Data upgraded",
                                Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(MainActivity.this,
                                "Data not upgraded",
                                Toast.LENGTH_SHORT).show();

                } catch (Exception e) {
                    Toast.makeText(MainActivity.this,
                            e.toString(),
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){
            if (requestCode==GALLERY_REQ_CODE){
                //for gallery
                imageView.setImageURI(data.getData());
//                Glide.with(imageView.getContext())
//                        .load(data.getData())
//                        .into(imageView);
                tImageURI.setText(data.getData().toString());
            }
        }
    }
}