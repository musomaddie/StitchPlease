package project.comp5216.crossstitchorganiser;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface ThreadDao {
	@Query("SELECT * FROM threads")
    List<ThreadDatabaseItem> listAll();

    @Insert
    void insert(ThreadDatabaseItem thread);

    @Insert
    void insertAll(ThreadDatabaseItem... threads);

    @Query("DELETE FROM threads")
    void deleteAll();
}
