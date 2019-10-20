package project.comp5216.crossstitchorganiser;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ProjectThreadDao {

	@Query("SELECT * FROM projectsToThreads")
	List<ProjectThreadDatabaseItem> listAll();

	@Query("SELECT * FROM projectsToThreads WHERE projectName = :projectName")
	List<ProjectThreadDatabaseItem> findThreads(String projectName);

	@Insert
	void insert(ProjectThreadDatabaseItem item);

	@Insert
	void insertAll(ProjectThreadDatabaseItem... items);

	@Query("DELETE FROM projectsToThreads")
	void deleteAll();
}
