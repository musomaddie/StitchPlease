package project.comp5216.crossstitchorganiser;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class HomePage extends AppCompatActivity {

    private static final String APP_TAG = "Cross Stitch Organiser";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        Log.v(APP_TAG, "Loading Home Page");
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
}
