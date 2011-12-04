package com.qc.camera;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.Media;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.qc.R;
import com.qc.Util;

public class CameraActivity extends Activity {
    // define the file-name to save photo taken by Camera activity
    String fileName = "muzhigirl.jpg";
    
    // create parameters for Intent with filename
    ContentValues values;
    Button buttonTakePhoto;
    Button buttonChoosePhoto;
    ImageView imgTakenPhoto;
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 0;
    private static final int PICK_IMAGE_ACTIVITY_REQUESRT_CODE = 1;

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        Util.clearImageResource(imgTakenPhoto);
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        Util.clearImageResource(imgTakenPhoto);
    }

    class buttonChoosePhotoClicker implements Button.OnClickListener {
        public void onClick(View v) {
            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            photoPickerIntent.setType("image/*");
            startActivityForResult(photoPickerIntent, PICK_IMAGE_ACTIVITY_REQUESRT_CODE);
        }
    }

    class buttonTakePhotoClicker implements Button.OnClickListener {
        public void onClick(View v) {
            ContentValues values = new ContentValues();
            values.put(Media.TITLE, fileName);
            values.put(Media.DESCRIPTION, "Image capture by camera, muzhi");
            Uri uri = getContentResolver().insert(Media.EXTERNAL_CONTENT_URI, values);

            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
            startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toast toast = Toast.makeText(getApplicationContext(), "onCreate", Toast.LENGTH_SHORT);
        toast.show();
        setContentView(R.layout.camera);
        buttonTakePhoto = (Button) findViewById(R.id.buttonTakePhoto);
        buttonChoosePhoto = (Button) findViewById(R.id.buttonChoosePhoto);
        imgTakenPhoto = (ImageView) findViewById(R.id.imageView);

        buttonTakePhoto.setOnClickListener(new buttonTakePhotoClicker());
        buttonChoosePhoto.setOnClickListener(new buttonChoosePhotoClicker());
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Toast.makeText(this, "on activity result is called", Toast.LENGTH_SHORT).show();
        switch (requestCode) {
        case CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE:
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, "photo taken ok", Toast.LENGTH_SHORT).show();
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Picture was not taken", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Picture was not taken", Toast.LENGTH_SHORT).show();
            }
            break;

        case PICK_IMAGE_ACTIVITY_REQUESRT_CODE:
            if ((resultCode == RESULT_OK) && (data.getData() != null)) {
                // resize the image size to 750 px (either width or height,
                // whichever comes first)
                Bitmap bitmap;
                try {
                    bitmap = Util.decodeFile(new File(new URI(data.getDataString())), 750);
                    Log.i("photo size is ", "" + bitmap.getWidth() + " , " + bitmap.getHeight());
                    imgTakenPhoto.setImageBitmap(bitmap);
                } catch (URISyntaxException e) {
                    Toast.makeText(this, "Unable to locate or decode the image you selected.",
                            Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Image not been selected!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}