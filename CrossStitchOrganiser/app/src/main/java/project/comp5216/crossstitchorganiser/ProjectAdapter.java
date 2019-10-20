package project.comp5216.crossstitchorganiser;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.List;


public class ProjectAdapter extends ArrayAdapter {

	public ProjectAdapter(Context context, List<Project> projects) {
		super(context, 0, projects);
	}

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

    	// First things first: find the appropriate project
    	Project project = (Project) getItem(position);

    	if (convertView == null) {
			convertView = LayoutInflater.from(getContext())
				.inflate(R.layout.list_item_project, parent, false);
		}
		
		// Lookup for project population
		// Set title
		TextView projectTitle = convertView.findViewById(R.id.listItemProjectTitle);
		projectTitle.setText(project.getTitle());

		// Set image
		// If no image to load: quit early
		if (project.getPathToImage() == null) {
			return convertView;
		}

		// TODO: source from stack overflow https://stackoverflow.com/questions/4181774/show-image-view-from-file-path
		File imageFile = new File(project.getPathToImage());
		if (imageFile.exists()) {
			Bitmap bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
			ImageView image = (ImageView) convertView.findViewById(R.id.listItemProjectImage);
			image.setImageBitmap(bitmap);
		}

		return convertView;

    }


}
