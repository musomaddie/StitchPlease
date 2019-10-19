package project.comp5216.crossstitchorganiser;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProjectsWishlistPage extends Activity {

    private static final String APP_TAG = "Cross Stitch Organiser";


    private ListView listView;
    private List<Project> wishlistProjects;
    private ArrayAdapter<Project> projectAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projects_wishlist);
        Log.v(APP_TAG, "Loading projects wishlist");

        // Gonna create project list now
        listView = findViewById(R.id.projectsWishlistList);
        setUpProjectData();
        projectAdapter = new ProjectAdapter(this, wishlistProjects);
        listView.setAdapter(projectAdapter);

        setUpProjectItemListener();
    }

    public void onProjectsWishlistBackClick(View view) {
        Log.d(APP_TAG, "clicked back from projects wishlist");
        finish();
    }

    private void setUpProjectItemListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Project updateProject = (Project) projectAdapter.getItem(position);
                Log.i(APP_TAG, "Clicked item " + position + ": " + updateProject.getTitle());

                Intent intent = new Intent(ProjectsWishlistPage.this,
                        ProjectSpecificPage.class);

                if (intent != null) {
                    intent.putExtra("name", updateProject.getTitle());
                    startActivity(intent);
                }
            }
        });
    }

    private void setUpProjectData() {
        // TODO: this is dummy data: read from the database properly later
        wishlistProjects = new ArrayList<Project>();
        wishlistProjects.add(new Project("PROJECT 1", true));
        Map<Thread, Double> t = new HashMap<Thread, Double>();
        t.put(new Thread("310", Colour.BLACK, 1.1), 1.0);
        t.put(new Thread("550", Colour.BLUE, 1.2), 0.2);
       // wishlistProjects.add(new Project("PROJECT 2", t, true));
    }
}
