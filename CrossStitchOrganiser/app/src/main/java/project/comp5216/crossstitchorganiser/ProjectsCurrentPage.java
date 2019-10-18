package project.comp5216.crossstitchorganiser;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
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

public class ProjectsCurrentPage extends Activity {

    private static final String APP_TAG = "Cross Stitch Organiser";

    private ListView listView;
    private ArrayList<Project> currentProjects;
    private ProjectAdapter projectAdapter =null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projects_current);
        Log.v(APP_TAG, "Loading projects current page");

        listView = (ListView) findViewById(R.id.projectsCurrentList);
        currentProjects = new ArrayList<>();
        projectAdapter = new ProjectAdapter(this, R.layout.list_item_project, currentProjects );

        listView.setAdapter(projectAdapter);

        //get data from sqlite
        Cursor cursor = ProjectsAddPage.sqLiteHelper.getData("SELECT * FROM PROJECT");
        currentProjects.clear();
        while (cursor.moveToNext()){
            int id= cursor.getInt(0);
            String name = cursor.getString(1);
            String description = cursor.getString(2);
            byte[] image= cursor.getBlob(3);

            currentProjects.add(new Project(id,name,description,image));
        }


        setUpProjectItemListener();
    }

    public void onProjectsCurrentBackClick(View view) {
        Log.v(APP_TAG, "Clicked back from on projects current page");
        finish();
    }

    private void setUpProjectItemListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Project updateProject = (Project) projectAdapter.getItem(position);
                Log.i(APP_TAG, "Clicked item " + position + ": " + updateProject.getName());

                Intent intent = new Intent(ProjectsCurrentPage.this,
                        ProjectSpecificPage.class);

                if (intent != null) {
                    intent.putExtra("name", updateProject.getName());
                    startActivity(intent);
                }
            }
        });
    }

    private void setUpProjectData() {
        // TODO: this is dummy data: read from the database properly later
        currentProjects = new ArrayList<Project>();
        currentProjects.add(new Project("PROJECT 1", false));
        Map<Thread, Double> t = new HashMap<Thread, Double>();
        t.put(new Thread("310", Colour.BLACK, 1.1), 1.0);
        t.put(new Thread("550", Colour.BLUE, 1.2), 0.2);
        currentProjects.add(new Project("PROJECT 2", t, false));
    }

}
