package com.longjunhao.jetpackdemo.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import com.longjunhao.jetpackdemo.db.entity.ContentEntity;
import java.util.List;

/**
 * .ContentDao
 *
 * @author Admitor
 * @date 2020/07/16
 */

@Dao
public interface ContentDao {
    
    @Query("SELECT * FROM contents")
    LiveData<List<ContentEntity>> loadAllContents();
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<ContentEntity> contents);
    
    @Query("SELECT contents.* FROM contents JOIN contentsFts ON (contents.id = contentsFts.rowid) "
        + "WHERE contentsFts MATCH :query")
    LiveData<List<ContentEntity>> searchAllProducts(String query);
}
