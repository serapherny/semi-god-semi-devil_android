package com.qc.camera;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.Media;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.qc.R;
import com.qc.Util;
import com.qc.entity.PhotoCategory;
import com.qc.login.RPCUtil;

public class CameraActivity extends Activity {
    private static final int CAPTURE_FIRST_IMAGE_ACTIVITY_REQUEST_CODE = 0;
    private static final int PICK_FIRST_IMAGE_ACTIVITY_REQUESRT_CODE = 1;
    private static final int CAPTURE_SECOND_IMAGE_ACTIVITY_REQUEST_CODE = 2;
    private static final int PICK_SECOND_IMAGE_ACTIVITY_REQUESRT_CODE = 3;

    // define the file-name to save photo taken by Camera activity
    private static String FIRST_FILE_NAME = "muzhigirl1.jpg";
    private static String SECOND_FILE_NAME = "muzhigirl2.jpg";
    private static String[] VISIBILITIES = new String[] { "所有人", "仅好友可见" };

    // create parameters for Intent with filename
    ContentValues values;
    Button photoQuestionButton;
    Button photoSendButton;
    ImageView firstPhoto;
    ImageView secondPhoto;
    ProgressDialog progressDialog;

    // data for upload
    private String description = "亲们，下面哪个比较好看一点？";
    private String visibility = "";
    private String category = "";
    private Bitmap photo1 = null;
    private Bitmap photo2 = null;

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        Util.clearImageResource(firstPhoto);
        Util.clearImageResource(secondPhoto);
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        Util.clearImageResource(firstPhoto);
        Util.clearImageResource(secondPhoto);
    }

    class ButtonChoosePhotoClicker implements OnClickListener {
        int whichphoto;

        public ButtonChoosePhotoClicker(int whichphoto) {
            super();
            this.whichphoto = whichphoto;
        }

        @Override
        public void onClick(DialogInterface dialog, int which) {
            // TODO Auto-generated method stub
            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            photoPickerIntent.setType("image/*");
            switch (this.whichphoto) {
            case 1:
                startActivityForResult(photoPickerIntent, PICK_FIRST_IMAGE_ACTIVITY_REQUESRT_CODE);
                break;
            case 2:
                startActivityForResult(photoPickerIntent, PICK_SECOND_IMAGE_ACTIVITY_REQUESRT_CODE);
                break;
            }

        }
    }

    class ButtonTakePhotoClicker implements OnClickListener {
        int whichphoto;

        public ButtonTakePhotoClicker(int whichphoto) {
            super();
            this.whichphoto = whichphoto;
        }

        @Override
        public void onClick(DialogInterface arg0, int arg1) {
            ContentValues values = new ContentValues();
            switch (this.whichphoto) {
            case 1:
                values.put(Media.TITLE, FIRST_FILE_NAME);
                values.put(Media.DESCRIPTION, "First image captured by the camera, muzhi");
                break;
            case 2:
                values.put(Media.TITLE, SECOND_FILE_NAME);
                values.put(Media.DESCRIPTION, "Second image captured by the camera, muzhi");
            }
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
            switch (this.whichphoto) {
            case 1:
                startActivityForResult(intent, CAPTURE_FIRST_IMAGE_ACTIVITY_REQUEST_CODE);
                break;
            case 2:
                startActivityForResult(intent, CAPTURE_SECOND_IMAGE_ACTIVITY_REQUEST_CODE);
            }
        }
    }

    class ButtonPhotoClicker implements Button.OnClickListener {
        int whichphoto;

        public ButtonPhotoClicker(int whichphoto) {
            super();
            this.whichphoto = whichphoto;
        }

        @Override
        public void onClick(View v) {
            AlertDialog.Builder builder = new AlertDialog.Builder(CameraActivity.this);
            builder.setMessage("选择相册照片或拍照上传").setCancelable(true)
                    .setPositiveButton("拍照上传", new ButtonTakePhotoClicker(this.whichphoto))
                    .setNegativeButton("相册选择", new ButtonChoosePhotoClicker(this.whichphoto));
            AlertDialog alert = builder.create();
            alert.show();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toast toast = Toast.makeText(getApplicationContext(), "onCreate", Toast.LENGTH_SHORT);
        toast.show();
        setContentView(R.layout.camera);

        firstPhoto = (ImageView) findViewById(R.id.imageView1);
        secondPhoto = (ImageView) findViewById(R.id.imageView2);

        setupButtons();

        firstPhoto.setClickable(true);
        secondPhoto.setClickable(true);
        firstPhoto.setOnClickListener(new ButtonPhotoClicker(1));
        secondPhoto.setOnClickListener(new ButtonPhotoClicker(2));
    }

    private void setupButtons() {
        photoQuestionButton = (Button) findViewById(R.id.photo_question_button);
        photoSendButton = (Button) findViewById(R.id.photo_send_button);

        photoQuestionButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        photoSendButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String[] items = PhotoCategory.getAllCategories();
                Log.i("Camera", "" + items.length);

                AlertDialog.Builder builder = new AlertDialog.Builder(CameraActivity.this);
                builder.setTitle("请选择类别:");
                builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        category = items[item];
                        AlertDialog.Builder builder = new AlertDialog.Builder(CameraActivity.this)
                                .setTitle("请选择求评价对象:");
                        builder.setSingleChoiceItems(VISIBILITIES, -1,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int item) {
                                        visibility = VISIBILITIES[item];
                                        checkPoll();
                                    }
                                });
                        builder.create().show();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }

    private void checkPoll() {
        // TODO Auto-generated method stub
        AlertDialog.Builder builder = new AlertDialog.Builder(CameraActivity.this);
        builder.setMessage("确定发送此评价吗？").setCancelable(false).setNegativeButton("取消", null)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sendPoll();
                    }
                });
        builder.create().show();
    }

    protected void sendPoll() {
        // show the progress bar
        progressDialog = ProgressDialog.show(CameraActivity.this, "", "上传中，请稍等...", true);
        progressDialog.setCancelable(true);
        progressDialog.show();

        // assembly the poll data for sending
        Map<String, String> map = new HashMap<String, String>();
        map.put("description", description);
        map.put("visibility", visibility);
        map.put("category", category);
        map.put("poll_type", "1");
        if (photo1 == null) {
            Log.i("CameraActivity", "No photo is selected!");
            return;
        }
        map.put("photo_1", convertPhoto(photo1));
        // map.put("photo_1", "123");
        map.put("ext1", ".jpeg");
        if (photo2 != null) {
            map.put("photo_2", convertPhoto(photo2));
            map.put("ext2", ".jpeg");
        }
        new RPCUtil().sendPoll(map, new PollResponseHanlder());
    }

    public class PollResponseHanlder {
        public void handle(Map<String, String> response) {
            progressDialog.cancel();
            String result = response.get(Util.ACTION_RESULT);
            if (result.startsWith(Util.FAILED)) {
                Util.showDialog(CameraActivity.this, "上传评价失败：" + result, null);
            } else if (result.equals(Util.SUCCESS)) {
                Util.showDialog(CameraActivity.this, "上传评价成功！", CameraActivity.this);
            }
        }
    }

    private String convertPhoto(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    private Bitmap getRotatedBitmap() {
        return null;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Toast.makeText(this, "on activity result is called", Toast.LENGTH_SHORT).show();
        int w = 512;
        int h = 384;
        switch (requestCode) {
        case CAPTURE_FIRST_IMAGE_ACTIVITY_REQUEST_CODE:
            if (resultCode == RESULT_OK) {
                Bitmap original_bitmap = null;
                Bitmap result_bitmap = null;
                try {
                    original_bitmap = Util.decodeFile(new File(new URI(data.getDataString())), 750);
                    boolean rotated = original_bitmap.getWidth() > original_bitmap.getHeight();
                    if (!rotated) {
                        result_bitmap = result_bitmap.createScaledBitmap(original_bitmap, w, h,
                                true);
                        // If rotated, scale it by switching width and height
                        // and then rotated it
                    } else {
                        Bitmap scaled_bitmap = Bitmap.createScaledBitmap(original_bitmap, h, w,
                                true);
                        Matrix mat = new Matrix();
                        mat.postRotate(90);
                        result_bitmap = Bitmap.createBitmap(scaled_bitmap, 0, 0, h, w, mat, true);
                        // Release image resources
                        scaled_bitmap.recycle();
                        scaled_bitmap = null;
                    }
                    original_bitmap.recycle();
                    original_bitmap = null;
                    firstPhoto.setImageBitmap(result_bitmap);
                    photo1 = result_bitmap;
                } catch (URISyntaxException e) {
                    Toast.makeText(this, "Unable to locate or decode the image you selected.",
                            Toast.LENGTH_SHORT).show();
                }
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Picture was not taken: result_canceled", Toast.LENGTH_SHORT)
                        .show();
            } else {
                Toast.makeText(this, "Picture was not taken: else", Toast.LENGTH_SHORT).show();
            }
            break;

        case CAPTURE_SECOND_IMAGE_ACTIVITY_REQUEST_CODE:
            if ((resultCode == RESULT_OK) && (data.getData() != null)) {
                Bitmap bitmap;
                try {
                    bitmap = Util.decodeFile(new File(new URI(data.getDataString())), 750);
                    Log.i("photo size is ", "" + bitmap.getWidth() + " , " + bitmap.getHeight());
                    secondPhoto.setImageBitmap(bitmap);
                    photo2 = bitmap;
                } catch (URISyntaxException e) {
                    Toast.makeText(this, "Unable to locate or decode the image you selected.",
                            Toast.LENGTH_SHORT).show();
                }

            } else if (resultCode == RESULT_CANCELED) {

            } else {

            }
            break;

        case PICK_FIRST_IMAGE_ACTIVITY_REQUESRT_CODE:
            if ((resultCode == RESULT_OK) && (data.getData() != null)) {
                // resize the image size to 750 px (either width or height,
                // whichever comes first)
                Bitmap bitmap;
                try {
                    bitmap = Util.decodeFile(new File(new URI(data.getDataString())), 750);
                    try {
                        ExifInterface exif = new ExifInterface(FIRST_FILE_NAME);
                        String exifOrientation = exif.getAttribute(ExifInterface.TAG_ORIENTATION);
                        Toast.makeText(this, exifOrientation, Toast.LENGTH_SHORT);
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    Log.i("photo size is ", "" + bitmap.getWidth() + " , " + bitmap.getHeight());
                    firstPhoto.setImageBitmap(bitmap);
                    photo1 = bitmap;
                } catch (URISyntaxException e) {
                    Toast.makeText(this, "Unable to locate or decode the image you selected.",
                            Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Image not been selected!", Toast.LENGTH_SHORT).show();
            }
            break;

        case PICK_SECOND_IMAGE_ACTIVITY_REQUESRT_CODE:
            if ((resultCode == RESULT_OK) && (data.getData() != null)) {
                // resize the image size to 750 px (either width or height,
                // whichever comes first)
                Bitmap bitmap;
                try {
                    bitmap = Util.decodeFile(new File(new URI(data.getDataString())), 750);
                    Log.i("photo size is ", "" + bitmap.getWidth() + " , " + bitmap.getHeight());
                    secondPhoto.setImageBitmap(bitmap);
                    photo2 = bitmap;
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