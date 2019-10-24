package project.comp5216.crossstitchorganiser;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import androidx.lifecycle.LifecycleObserver;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Toast;



public class HomePage extends AppCompatActivity {

    private static final String APP_TAG = "Cross Stitch Organiser";

    //write setting request
    public final int REQUEST_WRITE_SETTING =1000;

    //brightness setting field
    public MyCountDownTimer timer;
    private int brightness;
    ContentResolver contentResolver;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        Log.v(APP_TAG, "Loading Home Page");



        getPermission();//asking permission to write brightness setting


        //battery saver mode
        contentResolver = getApplicationContext().getContentResolver();//getting Content resolver
        try{
            brightness = Settings.System.getInt(contentResolver, Settings.System.SCREEN_BRIGHTNESS);}//getting the current brightness settings
        catch (Exception e){
            e.printStackTrace();
        }
        timer = new MyCountDownTimer(contentResolver,30000,1000, brightness);//timer for 30 sec. with 1 sec interval



    }

    public void onThreadsNavClick(View view) {
        Log.v(APP_TAG, "clicked the threads button");
        Intent intent = new Intent(this, ThreadsMainPage.class);
        startActivity(intent);
    }

    public void onProjectsNavClick(View view) {
        Log.v(APP_TAG, "clicked the projects button");
        Intent intent = new Intent(this, ProjectsMainPage.class);
        startActivity(intent);
    }

    public void onShoppingListNavClick(View view) {
        Log.v(APP_TAG, "clicked the shopping list button");
        // TODO: when clicked from here we should load the shopping list including all current projects
        Intent intent = new Intent(this, ShoppingListPage.class);
        intent.putExtra("projectsIncluded", "all");
        startActivity(intent);
    }





    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==REQUEST_WRITE_SETTING){
            if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
                boolean value = Settings.System.canWrite(getApplicationContext());
                if(value!=true){
                    Toast.makeText(this,"Permission is not granted", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void getPermission(){
        boolean value;
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){
            value = Settings.System.canWrite(getApplicationContext());
            if(value!=true){
                Intent intent = new Intent (Settings.ACTION_MANAGE_WRITE_SETTINGS);
                intent.setData(Uri.parse("package:"+getApplicationContext().getPackageName()));
                startActivityForResult(intent, REQUEST_WRITE_SETTING);
            }
        }
    }



    @Override
    public void onUserInteraction(){
        Settings.System.putInt(contentResolver, Settings.System.SCREEN_BRIGHTNESS, brightness);
        timer.start();

    }

    @Override
    protected void onStop() {
        super.onStop();
        timer.cancel();
    }

    @Override
    protected void onPause() {
        super.onPause();
        timer.cancel();
    }
}
