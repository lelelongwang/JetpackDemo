package com.longjunhao.jetpackdemo.ui;

import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.longjunhao.jetpackdemo.R;
import com.longjunhao.jetpackdemo.databinding.FragmentContentListBinding;
import com.longjunhao.jetpackdemo.db.entity.ContentEntity;
import com.longjunhao.jetpackdemo.model.Content;
import com.longjunhao.jetpackdemo.viewmodel.ContentListViewModel;
import java.util.List;

/**
 * @author Admitor
 */
public class ContentListFragment extends Fragment {
    
    public static final String TAG = "ContentListFragment";
    
    private ContentAdapter mContentAdapter;
    
    private FragmentContentListBinding mBinding;
    
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_content_list,
            container, false);
        
        mContentAdapter = new ContentAdapter(mContentClickCallback);
        mBinding.contentsList.setAdapter(mContentAdapter);
        
        return mBinding.getRoot();
    }
    
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ContentListViewModel viewModel =
            new ViewModelProvider(this).get(ContentListViewModel.class);
        mBinding.contentsSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Editable query = mBinding.contentsSearchBox.getText();
                viewModel.setQuery(query);
            }
        });
        
        subscribeUi(viewModel.getProducts());
    }
    
    private void subscribeUi(LiveData<List<ContentEntity>> liveData) {
        // Update the list when the data changes
        liveData.observe(getViewLifecycleOwner(), new Observer<List<ContentEntity>>() {
            @Override
            public void onChanged(List<ContentEntity> contentEntities) {
                if (contentEntities != null) {
                    mBinding.setIsLoading(false);
                    mContentAdapter.setContentList(contentEntities);
                } else {
                    mBinding.setIsLoading(true);
                }
                // espresso does not know how to wait for data binding's loop so we execute changes
                // sync.
                mBinding.executePendingBindings();
            }
        });
    }
    
    @Override
    public void onDestroyView() {
        mBinding = null;
        mContentAdapter = null;
        super.onDestroyView();
    }
    
    private final ContentClickCallback mContentClickCallback = new ContentClickCallback() {
        @Override
        public void onClick(Content product) {
            if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
                Log.d(TAG, "onClick: click item");
                Toast.makeText(requireActivity(), "点击第 "+product.getId()+" 个item", Toast.LENGTH_SHORT).show();
            }
        }
    };
}