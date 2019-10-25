package project.comp5216.crossstitchorganiser;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface ProjectDao {

	@Query("SELECT * FROM projects")
	List<ProjectDatabaseItem> listAll();

	@Query("SELECT * FROM projects WHERE title = :title")
	ProjectDatabaseItem loadProject(String title);

	@Query("UPDATE projects SET isWishlist=:isWishlist WHERE title =:title")
	void updateBuy(boolean isWishlist, String title);

	@Insert
	void insert(ProjectDatabaseItem project);

    @Query("DELETE FROM projects")
    void deleteAll();

}
