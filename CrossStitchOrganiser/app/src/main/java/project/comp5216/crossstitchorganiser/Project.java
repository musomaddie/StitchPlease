package project.comp5216.crossstitchorganiser;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;

public class Project {

    private int id;

    private static final String APP_TAG = "Cross Stitch Organiser";

    private String name; // stores the name of the project

    private byte[] image; // images associated with the project

    private String description; // project description

    private Map<Thread, Double> threadsAmountNeeded;
    // stores the thread needed mapped to how much is needed for this particular project.
    private boolean isWishlist; // true if on wishlist, false if not (owned)
    // TODO: not sure if isWishlist is needed in final product -> could just store
    //  in two different places

    //TODO: not sure which constructor is more appropriate so currently have both
    public Project(String name, boolean isWishlist) {
        this.name = name;
        this.threadsAmountNeeded = new HashMap<Thread, Double>();
        this.isWishlist = isWishlist;
        Log.i(APP_TAG, "Created new project: " + this.toString());
    }

    public Project(String name, Map<Thread, Double> threads, boolean w) {
        this.name = name;
        this.threadsAmountNeeded = threads;
        this.isWishlist = w;
        Log.i(APP_TAG, "Created new project: " + this.toString());
    }
    // Project constructor with images
    public Project(int id, String name,String description, byte[] image) {
        this.id=id; //database id
        this.name = name;
        this.description=description;
        this.image=image;
        this.threadsAmountNeeded = new HashMap<Thread, Double>();
        this.isWishlist = true;
        Log.i(APP_TAG, "Created new project: " + this.toString());
    }



    public String getName() {
        return this.name;
    }

    public void addThreadAmount(Thread thread, double a) {
        this.threadsAmountNeeded.put(thread, a);
    }

    public void buy() {
        this.isWishlist = false;
    }

    public Map<Thread, Double> getThreadsAmountNeeded() {
        return this.threadsAmountNeeded;
    }

    // use this when only interested in which threads, not how much.
    public Thread [] getThreadsNeeded() {
        return this.threadsAmountNeeded.keySet().toArray(
                new Thread[this.threadsAmountNeeded.size()]);
    }

    public String toString() {
        return this.name + ": " + this.threadsAmountNeeded.toString();
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setThreadsAmountNeeded(Map<Thread, Double> threadsAmountNeeded) {
        this.threadsAmountNeeded = threadsAmountNeeded;
    }

    public boolean isWishlist() {
        return isWishlist;
    }

    public void setWishlist(boolean wishlist) {
        isWishlist = wishlist;
    }

}
