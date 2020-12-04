package project.comp5216.crossstitchorganiser;

// Special class just to store a project how it appears in the thread list
public class ThreadProject {

	public final String projectName;
	public final double amountNeeded;

	public ThreadProject(String projectName, double amountNeeded) {
		this.projectName = projectName;
		this.amountNeeded = amountNeeded;
	}
}
