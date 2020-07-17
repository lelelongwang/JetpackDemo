package com.longjunhao.jetpackdemo;

import android.app.Application;
import com.longjunhao.jetpackdemo.db.AppDatabase;
import com.longjunhao.jetpackdemo.repository.DataRepository;

/**
 * Android Application class. Used for accessing singletons.
 * @author Admitor
 */
public class BasicApp extends Application {
    
    private AppExecutors mAppExecutors;
    
    @Override
    public void onCreate() {
        super.onCreate();
        
        mAppExecutors = new AppExecutors();
    }
    
    public AppDatabase getDatabase() {
        return AppDatabase.getInstance(this, mAppExecutors);
    }
    
    public DataRepository getRepository() {
        return DataRepository.getInstance(getDatabase());
    }
}