package project.comp5216.crossstitchorganiser;

import android.Manifest;
import android.app.Activity;
import android.database.Cursor;
import android.os.AsyncTask;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.os.Environment.getExternalStoragePublicDirectory;

public class ProjectsAddPage extends Activity {

    private static final String APP_TAG = "Cross Stitch Organiser";
	
	private String projectTitle;
	private Project newProject;
	private List<ThreadDetails> threadDetails;
	private List<Project> allExistingProjects;
	private boolean isWishlist;

	private OrganiserDatabase db;
    private ProjectDao projectDao;
    private ProjectThreadDao projectThreadDao;

    Button buttonChooseImage, takePicture;
    ImageView imageView;
    String pathToFile;

    final int REQUEST_CODE_GALLERY =999;
    final int REQUEST_CODE_TAKEPICTURE = 888;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projects_add);
        Log.v(APP_TAG, "Loading Projects Add Page");


        // Loading the dynamic thread amount adding!
        LinearLayout ll = (LinearLayout) findViewById(R.id.projectsAddThreadDets);
        threadDetails = new ArrayList<ThreadDetails>();
        for (int i = 0; i < 10; i++) {
			View view = LayoutInflater.from(this).inflate(R.layout.view_add_thread_to_project, null);
			EditText dmcET = view.findViewById(R.id.projectsAddThreadDmc);
			EditText amountET = view.findViewById(R.id.projectsAddThreadAmount);
			threadDetails.add(new ThreadDetails(dmcET, amountET));
			ll.addView(view);
		}

		// Loading the database
		db = OrganiserDatabase.getDatabase(this.getApplication().getApplicationContext());
		projectDao = db.projectDao();
		projectThreadDao = db.projectThreadDao();
       // deleteAllFromDatabase();

        init(); //calling button constructor

        if (Build.VERSION.SDK_INT >= 23) {
            requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
        }

		// This just makes a request 
        buttonChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(ProjectsAddPage.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE_GALLERY
                );

            }
        });

        takePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchPictureTakeAction();
            }
        });
    }

	public void onProjectsAddSubmitClick(View view) {
		EditText titleET = findViewById(R.id.projectsAddTitleChange);
		projectTitle = titleET.getText().toString();
		if (existing()) {
			return;
		}
		if (projectTitle.equals("")) {
			Toast.makeText(getApplicationContext(),
					getResources().getString(R.string.failure_add_project_no_title),
					Toast.LENGTH_SHORT).show();
			return;
		}
		newProject = new Project(projectTitle, isWishlist, pathToFile);


		// Going through the thread info now
        List<String> seen = new ArrayList<String>();
		for(ThreadDetails td : threadDetails) {
			// TODO: deal with the case where added to this project twice!!
			String dmc = td.getDmc();
			if (dmc == null) {
			    continue;
            }
			if (dmc.equals("")) {
				// No message as some might be naturally blank
				continue;
			}
			if (seen.contains(dmc)) {
			    // If we've already seen the thread ignore it the second time around
			    continue;
            }
			newProject.addThreadAmount(dmc, td.getAmount());
			seen.add(dmc);
		}
		saveProjectToDatabase();

		wipeInput();
		Toast.makeText(getApplicationContext(),
				getResources().getString(R.string.success_project_add) + newProject.getTitle(),
				Toast.LENGTH_SHORT).show();
	}

	private void wipeInput() {
        EditText titleEt = findViewById(R.id.projectsAddTitleChange);
        titleEt.getText().clear();
        for (ThreadDetails td: threadDetails) {
            td.clear();
        }

    }

	public void onProjectsAddMoreThreadsClick(View view) {
        LinearLayout ll = (LinearLayout) findViewById(R.id.projectsAddThreadDets);
        for (int i = 0; i < 5; i++) {
			View viewLL = LayoutInflater.from(this).inflate(R.layout.view_add_thread_to_project, null);
			EditText dmcET = viewLL.findViewById(R.id.projectsAddThreadDmc);
            EditText amountET = viewLL.findViewById(R.id.projectsAddThreadAmount);
            threadDetails.add(new ThreadDetails(dmcET, amountET));
			ll.addView(viewLL);
		}

	}

	public void onProjectsAddCheckboxClick(View view) {
		boolean checked = ((CheckBox) view).isChecked();
		isWishlist = checked;
	}

    public void onProjectsAddBackClick(View view) {
        Log.v(APP_TAG, "Clicked back from projects add");
        finish();
    }

    private boolean existing() {
    	loadAllFromDatabase();
    	for (Project p : allExistingProjects) {
			if (p.getTitle().equals(projectTitle)) {
				// refuse to accept it
				Toast.makeText(getApplicationContext(),
						getResources().getString(R.string.failure_add_project_exist_p1)
						+ " " + projectTitle + " "
						+ getResources().getString(R.string.failure_add_project_exist_p2),
						Toast.LENGTH_SHORT).show();
				return true;
			}
		}
		return false;
	}

	private void saveProjectToDatabase() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
				projectDao.insert(new ProjectDatabaseItem(newProject.getTitle(),
							newProject.isWishlist(), pathToFile));
				Log.i(APP_TAG, "Saved project: " + newProject.toString() + " to database");
				for (String threadDmc : newProject.getThreadsAmountNeeded().keySet()) {
					double amount = newProject.getThreadsAmountNeeded().get(threadDmc);
					projectThreadDao.insert(
							new ProjectThreadDatabaseItem(newProject.getTitle(), threadDmc, amount));
					Log.i(APP_TAG, "Saved project thread: " + newProject.getTitle() + " " + threadDmc + " to database");
				}
                return null;
            }
        }.execute();
	}

    private void deleteAllFromDatabase() {
        try {
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... voids) {
                    projectDao.deleteAll();
                    return null;
                }
            }.execute().get();
        } catch(Exception ex) {
            Log.e(APP_TAG, ex.getStackTrace().toString());
        }
    }

	private void loadAllFromDatabase() {
        try {
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... voids) {
                	List<ProjectDatabaseItem> itemsFromDB = projectDao.listAll();
                	allExistingProjects = new ArrayList<Project>();
                    if (itemsFromDB != null && itemsFromDB.size() > 0) {
                    	for (ProjectDatabaseItem item: itemsFromDB) {
                    		allExistingProjects.add(new Project(
                    					item.getTitle(),
                    					item.isWishlist()));
							Log.i(APP_TAG, "Read item from database: " + item.getTitle());
                        }
                    }
                    return null;
                }
            }.execute().get();
        } catch(Exception ex) {
			Log.e(APP_TAG, ex.getStackTrace().toString());
        }
	}

    private void dispatchPictureTakeAction() {
        Intent takePic = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePic.resolveActivity(getPackageManager()) != null) {
            File photoFile = createPhotoFile();

            if (photoFile != null) {
                pathToFile = photoFile.getAbsolutePath();
                Uri photoUri = FileProvider.getUriForFile(ProjectsAddPage.this, "project.comp5216.crossstitchorganiser", photoFile);
                takePic.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
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
        	e.printStackTrace();
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
            	pathToFile = new File(getRealPathFromUri(uri)).getAbsolutePath();
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imageView.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();

            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    // private method to help
    // TODO: source this: taken from stack overflow https://stackoverflow.com/questions/2789276/android-get-real-path-by-uri-getpath/9989900
    private String getRealPathFromUri(Uri contentUri) {
		String result;
		Cursor cursor = getContentResolver().query(contentUri, null, null, null, null);
		if (cursor == null) {
			result = contentUri.getPath();
		} else {
			cursor.moveToFirst();
			int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
			result = cursor.getString(idx);
			cursor.close();
		}
		return result;
	}



    private void init(){
        buttonChooseImage = (Button) findViewById(R.id.button_addImage);
        imageView= (ImageView) findViewById(R.id.addProject_imageView);
        takePicture = (Button) findViewById(R.id.button_camera);
    }
}

class ThreadDetails {

	private EditText dmcET;
	private EditText amountET;

	public ThreadDetails(EditText dmc, EditText amount) {
		this.dmcET = dmc;
		this.amountET = amount;
	}

	public void clear() {
	    try {
            dmcET.getText().clear();
            amountET.getText().clear();
        } catch (NullPointerException ex) {
	        return;
        }

    }

	public String getDmc() {
	    try {
            return this.dmcET.getText().toString();
        } catch (NullPointerException ex) {
	        return null;
        }
	}

	public double getAmount() {
		try {
			return Double.parseDouble(this.amountET.getText().toString());
		} catch (NumberFormatException e) {
			// TODO: this means for shopping list will want you to buy 2! Fix this
			return 1.0;
		}
	}
}
