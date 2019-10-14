package project.comp5216.crossstitchorganiser;

// TODO: problem: means thread can be added with ONLY an amount needed instead of an amount owned. Have a special case in add project for this.

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class Thread {

    private static final String APP_TAG = "Cross Stitch Organiser";

    private String dmc; // This is its number: the unique identifier
    // TODO: decide if we want the user to enter Colour or we calculate ourselves.
    private Colour colour; // The colour of the thread (For grouping later)
    private int[] rgb; // The RGB colour (calculated by us for display)
    private double amountOwned; // the amount of thread the user owns
    private double amountNeeded; // the amount of thread the user needs for all projects -> calculated by us based on projects reqs.
    private List<Project> projects; // the projects this thread is used in

    public Thread(String dmc, Colour colour, double amountOwned) {
        this.dmc = dmc;
        this.colour = colour;
        this.rgb = new int[3];
        this.amountOwned = amountOwned;
        this.amountNeeded = 0;
        this.projects = new ArrayList<Project>();
        Log.i(APP_TAG, "Created new thread: " + this.toString());
    }

    public String getDmc() {
        return this.dmc;
    }

    public Colour getColour() {
        return this.colour;
    }

    public int[] rgb() {
        return this.rgb;
    }

    public double getAmountOwned() {
        return this.amountOwned;
    }

    public double getAmountNeeded() {
        return this.amountNeeded;
    }

    public List<Project> getProjects() {
        return this.projects;
    }

    public void addProject(Project project) {
        this.projects.add(project);
    }

    public String toString() {
        return this.dmc + ": " + this.colour + "(" + this.amountOwned + ")";
    }

}
