package com.example.nisargpatel.trainerhunt;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.nisargpatel.trainerhunt.activity.MainActivity;
import com.example.nisargpatel.trainerhunt.activity.R;
import com.example.nisargpatel.trainerhunt.activity.Trainer_form;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.UUID;

import static com.example.nisargpatel.trainerhunt.activity.login.PREFS_NAME;

public class upload_photo extends AppCompatActivity {
    private static final String UPLOAD_URL = constant.PATH + "/insert_image.php";
    private static final int IMAGE_REQUEST_CODE = 3;
    private static final int STORAGE_PERMISSION_CODE = 123;
    private ImageView imageView;
    private EditText etCaption;
    public String user;
    String ss;
    String selectedPath;
    ArrayList<String> listItems = new ArrayList<>();
    ArrayAdapter<String> adapter;
    Spinner sp;

    private Uri filePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_photo);
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, 0);

        user = sharedPreferences.getString("u", "");

        // sp = (Spinner) findViewById(R.id.cat);
        // adapter = new ArrayAdapter<String>(this, R.layout.spinner_layout, R.id.txt, listItems);
        // sp.setAdapter(adapter);
        imageView = (ImageView) findViewById(R.id.image);
        // etCaption = (EditText) findViewById(R.id.etCaption);
        //tvPath = (TextView) findViewById(R.id.path);
        requestStoragePermission();
        setTitle("Upload");
        Intent intent = new Intent();
        intent.setType("video/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Complete action using"), IMAGE_REQUEST_CODE);

    }

    public void upload(View view) {


        uploadMultipart();


        Intent intent = new Intent(upload_photo.this, Trainer_form.class);
        startActivity(intent);
    }

    public void choose(View view) {
        Intent intent = new Intent();
        intent.setType("video/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Complete action using"), IMAGE_REQUEST_CODE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == IMAGE_REQUEST_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            selectedPath = getPath(filePath);
            imageView.setImageURI(filePath);
            imageView.requestFocus();

            // tvPath.setText(selectedPath);

        }
    }

    public void uploadMultipart() {
        String caps = etCaption.getText().toString().trim();
        String caption = null;
        try {
            caption = URLEncoder.encode(caps, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        ss = sp.getSelectedItem().toString().trim();


        //getting the actual path of the image
        String path = getPath(filePath);

        //Uploading code
        try {
            String uploadId = UUID.randomUUID().toString();

            //Creating a multi part request
           // new MultipartUploadRequest(this, uploadId, UPLOAD_URL)
                  //  .addFileToUpload(path, "image") //Adding file
                   // .addParameter("caption", caption)
                   // .addParameter("category", ss)
                    //.addParameter("user_id", user)//  Adding text parameter to the request
                   // .setNotificationConfig(new UploadNotificationConfig())
                  //  .setMaxRetries(2)
                   // .startUpload(); //Starting the upload


        } catch (Exception exc) {
            Toast.makeText(this, exc.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public String getPath(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor.close();

        cursor = getContentResolver().query(
                android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA));
        cursor.close();


        return path;

    }

    private void requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }
        //And finally ask for the permission
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
    }

    //This method will be called when the user will tap on allow or deny
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        //Checking the request code of our request
        if (requestCode == STORAGE_PERMISSION_CODE) {

            //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Displaying a toast
                Toast.makeText(this, "Permission granted now you can read the storage", Toast.LENGTH_LONG).show();
            } else {
                //Displaying another toast if permission is not granted
                Toast.makeText(this, "Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }
        }
    }
}
