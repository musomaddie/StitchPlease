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

	@ColumnInfo(name="picturePath")
	private String picturePath;

	public ProjectDatabaseItem(String title, boolean isWishlist, String picturePath) {
		this.title = title;
		this.isWishlist = isWishlist;
		this.picturePath = picturePath;
	}

	public String getTitle() {
		return this.title;
	}

	public boolean isWishlist() {
		return this.isWishlist;
	}

	public String getPicturePath() {
		return this.picturePath;
	}
}
