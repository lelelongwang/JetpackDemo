package com.longjunhao.jetpackdemo.viewmodel;

import android.app.Application;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.Transformations;
import com.longjunhao.jetpackdemo.BasicApp;
import com.longjunhao.jetpackdemo.db.entity.ContentEntity;
import com.longjunhao.jetpackdemo.repository.DataRepository;
import java.util.List;

/**
 * .ContentListViewModel
 *
 * @author Admitor
 * @date 2020/07/16
 */
public class ContentListViewModel extends AndroidViewModel {
    
    private static final String QUERY_KEY = "QUERY";
    
    private final SavedStateHandle mSavedStateHandler;
    private final DataRepository mRepository;
    private final LiveData<List<ContentEntity>> mContents;
    
    public ContentListViewModel(@NonNull Application application,
                                @NonNull SavedStateHandle savedStateHandler) {
        super(application);
        this.mSavedStateHandler = savedStateHandler;
        this.mRepository = ((BasicApp) application).getRepository();
        
        // Use the savedStateHandle.getLiveData() as the input to switchMap,
        // allowing us to recalculate what LiveData to get from the DataRepository
        // based on what query the user has entered
        mContents = Transformations.switchMap(
            savedStateHandler.getLiveData("QUERY", null),
            (Function<CharSequence, LiveData<List<ContentEntity>>>) query -> {
                if (TextUtils.isEmpty(query)) {
                    return mRepository.getContents();
                }
                return mRepository.searchProducts("*" + query + "*");
            });
    }
    
    public void setQuery(CharSequence query) {
        // Save the user's query into the SavedStateHandle.
        // This ensures that we retain the value across process death
        // and is used as the input into the Transformations.switchMap above
        mSavedStateHandler.set(QUERY_KEY, query);
    }
    
    /**
     * Expose the LiveData Products query so the UI can observe it.
     */
    public LiveData<List<ContentEntity>> getProducts() {
        return mContents;
    }
}
