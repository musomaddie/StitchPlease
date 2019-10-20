package project.comp5216.crossstitchorganiser;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface ThreadDao {
	@Query("SELECT * FROM threads")
    List<ThreadDatabaseItem> listAll();

	@Query("UPDATE threads SET amountOwned=:amountOwned WHERE dmc = :dmc")
	void updateAmountOwned(double amountOwned, String dmc);


    @Insert
    void insert(ThreadDatabaseItem thread);

    @Insert
    void insertAll(ThreadDatabaseItem... threads);

    @Query("DELETE FROM threads")
    void deleteAll();
}
