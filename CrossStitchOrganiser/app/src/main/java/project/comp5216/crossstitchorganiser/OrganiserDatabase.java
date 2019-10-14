package project.comp5216.crossstitchorganiser;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {ThreadDatabaseItem.class}, version=1, exportSchema=false)

public abstract class OrganiserDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "organiser_db";
    private static OrganiserDatabase DBINSTANCE;

    public abstract ThreadDao threadDao();

    public static OrganiserDatabase getDatabase(Context context) {
        if (DBINSTANCE == null) {
            synchronized (OrganiserDatabase.class) {
                DBINSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        OrganiserDatabase.class, DATABASE_NAME).build();
            }
        }
        return DBINSTANCE;
    }

    public static void destroyInstance() {
        DBINSTANCE = null;
    }
}
