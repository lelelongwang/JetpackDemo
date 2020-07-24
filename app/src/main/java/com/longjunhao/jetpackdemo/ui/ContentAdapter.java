package com.longjunhao.jetpackdemo.ui;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.longjunhao.jetpackdemo.R;
import com.longjunhao.jetpackdemo.databinding.ContentItemBinding;
import com.longjunhao.jetpackdemo.model.Content;
import java.util.List;

/**
 * .ContentAdapter
 *
 * @author Admitor
 * @date 2020/07/17
 */
public class ContentAdapter extends RecyclerView.Adapter<ContentAdapter.ContentViewHolder> {
    
    List<? extends Content> mContentList;
    
    @Nullable
    private final ContentClickCallback mContentClickCallback;
    
    public ContentAdapter(@Nullable ContentClickCallback clickCallback) {
        this.mContentClickCallback = clickCallback;
        setHasStableIds(true);
    }
    
    public void setContentList(List<? extends Content> contentList) {
        if (mContentList == null) {
            this.mContentList = contentList;
            notifyItemRangeInserted(0, contentList.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return mContentList.size();
                }
                
                @Override
                public int getNewListSize() {
                    return contentList.size();
                }
                
                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return mContentList.get(oldItemPosition).getId()
                        == contentList.get(newItemPosition).getId();
                }
                
                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    Content newContent = contentList.get(newItemPosition);
                    Content oldContent = mContentList.get(oldItemPosition);
                    return newContent.getId() == oldContent.getId()
                        && TextUtils.equals(newContent.getTitle(), oldContent.getTitle())
                        && TextUtils.equals(newContent.getAuthor(), oldContent.getAuthor())
                        && TextUtils.equals(newContent.getChapterName(), oldContent.getChapterName())
                        && newContent.getPublishTime() == oldContent.getPublishTime();
                }
            });
            mContentList = contentList;
            result.dispatchUpdatesTo(this);
        }
        
    }
    
    @NonNull
    @Override
    public ContentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ContentItemBinding binding = DataBindingUtil
            .inflate(LayoutInflater.from(parent.getContext()), R.layout.content_item,
                parent, false);
        binding.setCallback(mContentClickCallback);
        return new ContentViewHolder(binding);
    }
    
    @Override
    public void onBindViewHolder(@NonNull ContentViewHolder holder, int position) {
        holder.binding.setContent(mContentList.get(position));
        holder.binding.executePendingBindings();
    }
    
    @Override
    public int getItemCount() {
        return mContentList == null ? 0 : mContentList.size();
    }
    
    @Override
    public long getItemId(int position) {
        return mContentList.get(position).getId();
    }
    
    static class ContentViewHolder extends RecyclerView.ViewHolder {
        
        final ContentItemBinding binding;
        
        public ContentViewHolder(ContentItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
