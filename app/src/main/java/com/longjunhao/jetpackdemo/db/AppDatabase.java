package com.longjunhao.jetpackdemo.db;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
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

@Database(entities = {ContentEntity.class, ContentFtsEntity.class}, version = 1)
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
}
