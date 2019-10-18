package project.comp5216.crossstitchorganiser;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

@Entity(tableName="projects")
public class ProjectDatabaseItem {

	@PrimaryKey
	@NonNull
	@ColumnInfo(name="title")
	private String title;

	@ColumnInfo(name="isWishlist")
	private boolean isWishlist;

	public ProjectDatabaseItem(String title, boolean isWishlist) {
		this.title = title;
		this.isWishlist = isWishlist;
	}

	public String getTitle() {
		return this.title;
	}

	public boolean isWishlist() {
		return this.isWishlist;
	}
}
