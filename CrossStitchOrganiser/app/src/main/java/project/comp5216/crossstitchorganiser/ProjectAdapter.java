package project.comp5216.crossstitchorganiser;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ProjectAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private ArrayList<Project> projectList;

    public ProjectAdapter(Context context, int layout, ArrayList<Project> projectList) {
        this.context = context;
        this.layout = layout;
        this.projectList = projectList;
    }

    @Override
    public int getCount() {
        return projectList.size();
    }

    @Override
    public Object getItem(int position) {
        return projectList.get(position);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    private class ViewHolder{
        ImageView imageView;
        TextView projectName, projectDescription, threadColour, threadAmounts, projectsWishlist;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View row=view;
        ViewHolder holder = new ViewHolder();

        if(row==null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layout,null);

            holder.projectName = (TextView) row.findViewById(R.id.project_item_name);
            holder.projectDescription = (TextView) row.findViewById(R.id.project_item_description);
            holder.imageView= (ImageView) row.findViewById(R.id.project_item_imageView);
            holder.threadColour = (TextView) row.findViewById(R.id.project_thread_colour);
            holder.threadAmounts= (TextView) row.findViewById(R.id.project_thread_amounts);
            holder.projectsWishlist= (TextView) row.findViewById(R.id.project_wishlist);
            row.setTag(holder);

        } else {
            holder = (ViewHolder) row.getTag();
        }

        Project project= projectList.get(i);

        holder.projectName.setText(project.getName());
        holder.projectDescription.setText(project.getDescription());

        byte[] projectImage = project.getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(projectImage,0, projectImage.length);
        holder.imageView.setImageBitmap(bitmap);

        return row;
    }

}
