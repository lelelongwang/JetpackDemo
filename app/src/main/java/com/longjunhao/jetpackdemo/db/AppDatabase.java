package com.longjunhao.jetpackdemo.db;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;
import com.longjunhao.jetpackdemo.AppExecutors;
import com.longjunhao.jetpackdemo.db.converter.DateConverter;
import com.longjunhao.jetpackdemo.db.dao.ContentDao;
import com.longjunhao.jetpackdemo.db.entity.ContentEntity;
import com.longjunhao.jetpackdemo.db.entity.ContentFtsEntity;
import java.util.List;

/**
 * .AppDatabase
 *
 * @author Admitor
 * @date 2020/07/16
 */

@Database(entities = {ContentEntity.class, ContentFtsEntity.class}, version = 3)
@TypeConverters(DateConverter.class)
public abstract class AppDatabase extends RoomDatabase {
    
    private static AppDatabase sInstance;
    
    public static final String DATABASE_NAME = "jetpack_db";
    
    public abstract ContentDao contentDao();
    
    private final MutableLiveData<Boolean> mIsDatabaseCreated = new MutableLiveData<>();
    
    public static AppDatabase getInstance(final Context context, final AppExecutors executors) {
        if (sInstance == null) {
            synchronized (AppDatabase.class) {
                if (sInstance == null) {
                    sInstance = buildDatabase(context.getApplicationContext(), executors);
                    sInstance.updateDatabaseCreated(context.getApplicationContext());
                }
            }
        }
        return sInstance;
    }
    
    /**
     * Build the database. {@link Builder#build()} only sets up the database configuration and
     * creates a new instance of the database.
     * The SQLite database is only created when it's accessed for the first time.
     */
    private static AppDatabase buildDatabase(final Context appContext,
                                             final AppExecutors executors) {
        return Room.databaseBuilder(appContext, AppDatabase.class, DATABASE_NAME)
            .addCallback(new Callback() {
                @Override
                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                    super.onCreate(db);
                    executors.diskIO().execute(new Runnable() {
                        @Override
                        public void run() {
                            // Add a delay to simulate a long-running operation
                            addDelay();
                            // Generate the data for pre-population
                            AppDatabase database = AppDatabase.getInstance(appContext, executors);
                            List<ContentEntity> contents = DataGenerator.generateContents();
                            
                            insertData(database, contents);
                            // notify that the database was created and it's ready to be used
                            database.setDatabaseCreated();
                        }
                    });
                }
            })
            .addMigrations(MIGRATION_1_2, MIGRATION_2_3)
            .build();
    }
    
    /**
     * Check whether the database already exists and expose it via {@link #getDatabaseCreated()}
     */
    private void updateDatabaseCreated(final Context context) {
        if (context.getDatabasePath(DATABASE_NAME).exists()) {
            setDatabaseCreated();
        }
    }
    
    private void setDatabaseCreated() {
        mIsDatabaseCreated.postValue(true);
    }
    
    private static void insertData(final AppDatabase database, final List<ContentEntity> products) {
        database.runInTransaction(new Runnable() {
            @Override
            public void run() {
                database.contentDao().insertAll(products);
            }
        });
    }
    
    private static void addDelay() {
        try {
            Thread.sleep(4000);
        } catch (InterruptedException ignored) {
        }
    }

    
    public MutableLiveData<Boolean> getDatabaseCreated() {
        return mIsDatabaseCreated;
    }

    static final Migration MIGRATION_1_2 = new Migration(1, 2) {

        /**
         * .
         * 本次更新数据库是因为在ContentEntity.java中新增了@PrimaryKey(autoGenerate = true)中的
         * autoGenerate = true属性，提示要更新数据库版本。
         * @param database
         */
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            // Room uses an own database hash to uniquely identify the database
            // Since version 1 does not use Room, it doesn't have the database hash associated.
            // By implementing a Migration class, we're telling Room that it should use the data
            // from version 1 to version 2.
            // If no migration is provided, then the tables will be dropped and recreated.
            // Since we didn't alter the table, there's nothing else to do here.
        }
    };

    static final Migration MIGRATION_2_3 = new Migration(2, 3) {

        /**
         *.
         * 一些常用的语句：
         * // Create the new table
         * database.execSQL(
         *         "CREATE TABLE users_new (userid TEXT, username TEXT, last_update INTEGER,"
         *                 + " PRIMARY KEY(userid))");
         * // Copy the data
         * database.execSQL("INSERT INTO users_new (userid, username, last_update) "
         *         + "SELECT userid, username, 0 "
         *         + "FROM users");
         * // Remove the old table
         * database.execSQL("DROP TABLE users");
         * // Change the table name to the correct one
         * database.execSQL("ALTER TABLE users_new RENAME TO users");
         *
         *
         * 本次更新数据库是因为要修改数据库中某一列
         * @param database
         */
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE contents RENAME COLUMN postedAt TO publishTime");
        }
    };
}
