package com.longjunhao.jetpackdemo.db.entity;

import androidx.room.Entity;
import androidx.room.Fts4;

/**
 * .ContentFtsEntity
 *
 * @author Admitor
 * @date 2020/07/17
 */

@Entity(tableName = "contentsFts")
@Fts4(contentEntity = ContentEntity.class)
public class ContentFtsEntity {
    
    private String title;
    private String author;
    
    public ContentFtsEntity(String title, String author) {
        this.title = title;
        this.author = author;
    }
    
    public String getTitle() {
        return title;
    }
    
    public String getAuthor() {
        return author;
    }
}
