package project.comp5216.crossstitchorganiser;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;

@Entity(tableName="projectsToThreads",
		primaryKeys={"projectName", "threadDmc"})
public class ProjectThreadDatabaseItem {

	@NonNull
	@ColumnInfo(name="projectName")
	private String projectName;

	@NonNull
	@ColumnInfo(name="threadDmc")
	private String threadDmc;

	@ColumnInfo(name="amountNeeded")
	private double amountNeeded;

	public ProjectThreadDatabaseItem(String projectName,
			String threadDmc,
			double amountNeeded) {
		this.projectName = projectName;
		this.threadDmc = threadDmc;
		this.amountNeeded = amountNeeded;
	}

	public String getProjectName() {
		return this.projectName;
	}

	public String getThreadDmc() {
		return this.threadDmc;
	}

	public double getAmountNeeded() {
		return this.amountNeeded;
	}
}
