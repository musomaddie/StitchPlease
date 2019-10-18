package project.comp5216.crossstitchorganiser;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.os.Environment.getExternalStoragePublicDirectory;

public class ProjectsAddPage extends Activity {

    private static final String APP_TAG = "Cross Stitch Organiser";
    EditText editName, editDescription;
    Button buttonChooseImage, buttonAdd, buttonProject, takePicture;
    ImageView imageView;
    String pathToFile;
    boolean wishlist= true;

    final int REQUEST_CODE_GALLERY =999;
    final int REQUEST_CODE_TAKEPICTURE = 888;

    public static ProjectSQLiteHelper sqLiteHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v(APP_TAG, "Loading Projects Add Page");
        setContentView(R.layout.activity_projects_add);

        init(); //calling button constructor

        if (Build.VERSION.SDK_INT >= 23) {
            requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
        }

        sqLiteHelper= new ProjectSQLiteHelper(this, "ProjectDB.sqlite", null, 1);

        //creating database for project
        sqLiteHelper.queryData("CREATE TABLE IF NOT EXISTS PROJECT (Id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR, description VARCHAR, image BLOG )");

        buttonChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(ProjectsAddPage.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE_GALLERY
                );

            }
        });

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try{
                    sqLiteHelper.insertData(
                            editName.getText().toString().trim(),
                            editDescription.getText().toString().trim(),
                            imageViewToByte(imageView),wishlist

                    );
                    Toast.makeText(getApplicationContext(),"Added successfully", Toast.LENGTH_SHORT).show();
                    editName.setText("");
                    editDescription.setText("");
                    imageView.setImageResource(R.mipmap.ic_launcher);

                }
                catch(Exception e){
                    e.printStackTrace();
                }

            }
        });

        takePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchPictureTakeAction();
            }
        });
    }



    public void onProjectsAddBackClick(View view) {
        Log.v(APP_TAG, "Clicked back from projects add");
        finish();
    }

    private byte[] imageViewToByte(ImageView image) {
        Bitmap bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 40, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    private void dispatchPictureTakeAction() {
        Intent takePic = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePic.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            photoFile = createPhotoFile();

            if (photoFile != null) {
                pathToFile = photoFile.getAbsolutePath();
                Uri photoURI = FileProvider.getUriForFile(ProjectsAddPage.this, "project.comp5216.crossstitchorganiser", photoFile);
                takePic.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePic, 888);
            }
        }
    }

    private File createPhotoFile() {
        String name = new SimpleDateFormat("MMdd_mmss").format(new Date());
        File storageDir = getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File image = null;
        try {
            image = File.createTempFile(name, ".jpg", storageDir);
        } catch (IOException e) {
            Log.d("mylog", "Error");
        }
        return image;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == REQUEST_CODE_GALLERY){
            if(grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_GALLERY);
            }
            else {
                Toast.makeText(getApplicationContext(),"you dont have permission to access file location",Toast.LENGTH_LONG);
            }
            return;
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == REQUEST_CODE_TAKEPICTURE){


            Bitmap bitmap = BitmapFactory.decodeFile(pathToFile);
            imageView.setImageBitmap(bitmap);

        }

        else if(requestCode == REQUEST_CODE_GALLERY && resultCode ==RESULT_OK && data != null){
            Uri uri =data.getData();

            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imageView.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();

            }
        }


        super.onActivityResult(requestCode, resultCode, data);
    }



    private void init(){
        editName = (EditText) findViewById(R.id.editText_projectName);
        editDescription= (EditText) findViewById(R.id.editText_projectDescription);
        buttonChooseImage = (Button) findViewById(R.id.button_addImage);
        buttonAdd = (Button) findViewById(R.id.button_addNewProject);
        imageView= (ImageView) findViewById(R.id.addProject_imageView);
        takePicture = (Button) findViewById(R.id.button_camera);
    }
}
