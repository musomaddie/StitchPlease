package project.comp5216.crossstitchorganiser;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.List;

public class ProjectSpecificPage  extends Activity {

    private static final String APP_TAG = "Cross Stitch Organiser";
	
	private String thisProjectTitle;
    private Project thisProject;
	private OrganiserDatabase db;
    private ProjectDao projectDao;
    private ProjectThreadDao projectThreadDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_specific);
		thisProjectTitle = getIntent().getStringExtra("name");

		// Setting up the DB
		db = OrganiserDatabase.getDatabase(this.getApplication().getApplicationContext());
		projectDao = db.projectDao();
		projectThreadDao = db.projectThreadDao();

		// Loading the project details from db
		loadDetailsFromDatabase();

		//Setting up title
		TextView title = findViewById(R.id.specificProjectTitle);
		title.setText(thisProject.getTitle());

		// Viewing the image
		ImageView image = findViewById(R.id.specificProjectImage);
		if (thisProject.getPathToImage() != null) {
			// TODO: source from stack overflow (again)
			File imageFile = new File(thisProject.getPathToImage());
			if (imageFile.exists()) {
				Bitmap bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
				image.setImageBitmap(bitmap);
			}
		}

		// Viewing the buy button (only if wishlist)
		Button buyButton = findViewById(R.id.specificProjectBuy);
		if (thisProject.isWishlist()) {
			buyButton.setVisibility(View.VISIBLE);
		}

		// TODO: once loaded threads I need to sort them as well.
		// Numerically with non numbers at the front
    }

    public  void onShoppingListNavClick(View view) {
        Log.v(APP_TAG, "clicked shopping list from project");
        // TODO: create a shopping list only from this project!
        Intent intent = new Intent(this, ShoppingListPage.class);
        intent.putExtra("projectsIncluded", thisProject.getTitle());
        startActivity(intent);
    }

    public void onSpecificProjectBackClick(View view) {
        Log.v(APP_TAG, "clicked back from thread specific page");
        finish();
    }

    public void onProjectBuyClick(View view) {
    	Log.v(APP_TAG, "clicked buy project!");
    	// TODO: the work of moving this project to purchased. (should be
    	// straightfoward)
    }

	private void loadDetailsFromDatabase() {
        try {
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... voids) {
                    // Find the project from the database
                    ProjectDatabaseItem projectFromDB = projectDao.loadProject(thisProjectTitle);
                    if (projectFromDB != null) {
                        thisProject = new Project(projectFromDB.getTitle(),
                                projectFromDB.isWishlist(),
                                projectFromDB.getPicturePath());
                    }
                    Log.i(APP_TAG, "Read item from database: " + projectFromDB.getTitle());
                    // Load all thread details with it
                    List<ProjectThreadDatabaseItem> threadsFromDB = projectThreadDao.findThreads(thisProjectTitle);
                    if (thisProject != null && threadsFromDB != null && threadsFromDB.size() > 0) {
                    	for (ProjectThreadDatabaseItem item: threadsFromDB) {
                    		thisProject.addThreadAmount(item.getThreadDmc(), item.getAmountNeeded());
						}
					}
                    return null;
                }
            }.execute().get();
        } catch (Exception ex) {
            Log.e(APP_TAG, ex.getStackTrace().toString());
        }
    }
}
