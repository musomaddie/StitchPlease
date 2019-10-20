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
	
	@Insert
	void insert(ProjectDatabaseItem project);

    @Insert
	void insertAll(ProjectDatabaseItem ... projects);

    @Query("DELETE FROM projects")
    void deleteAll();

}
