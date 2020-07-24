package com.longjunhao.jetpackdemo.repository;

import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import com.longjunhao.jetpackdemo.db.AppDatabase;
import com.longjunhao.jetpackdemo.db.entity.ContentEntity;
import java.util.List;

/**
 * .DataRepository
 * Repository handling the work with products and comments.
 *
 * @author Admitor
 * @date 2020/07/17
 */
public class DataRepository {
    private static final String TAG = "DataRepository";
    
    private static DataRepository sInstance;
    
    private final AppDatabase mDatabase;
    
    private MediatorLiveData<List<ContentEntity>> mObservableContents;
    
    private DataRepository(final AppDatabase mDatabase) {
        this.mDatabase = mDatabase;
        this.mObservableContents = new MediatorLiveData<>();
    
        mObservableContents.addSource(mDatabase.contentDao().loadAllContents(),
            new Observer<List<ContentEntity>>() {
                @Override
                public void onChanged(List<ContentEntity> contentEntities) {
                    if (mDatabase.getDatabaseCreated().getValue() != null) {
                        mObservableContents.postValue(contentEntities);
                        Log.d(TAG, "DataRepository: size="+contentEntities.size());
                    }
                }
            });
    }
    
    public static DataRepository getInstance(final AppDatabase database) {
        if (sInstance == null) {
            synchronized (DataRepository.class) {
                if (sInstance == null) {
                    sInstance = new DataRepository(database);
                }
            }
        }
        return sInstance;
    }
    
    /**
     * Get the list of products from the database and get notified when the data changes.
     */
    public LiveData<List<ContentEntity>> getContents() {
        return mObservableContents;
    }
    
    public LiveData<List<ContentEntity>> searchProducts(String query) {
        return mDatabase.contentDao().searchAllProducts(query);
    }
    
}
