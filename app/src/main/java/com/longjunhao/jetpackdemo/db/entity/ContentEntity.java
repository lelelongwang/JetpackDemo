package com.longjunhao.jetpackdemo.db.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import com.longjunhao.jetpackdemo.model.Content;
import java.util.Date;

/**
 * .ContentEntity
 *
 * @author Admitor
 * @date 2020/07/16
 */
@Entity(tableName = "contents")
public class ContentEntity implements Content {
    
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo
    private String title;
    @ColumnInfo
    private String author;
    @ColumnInfo
    private String chapterName;
    @ColumnInfo
    private long publishTime;
    
    @Override
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    @Override
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    @Override
    public String getAuthor() {
        return author;
    }
    
    public void setAuthor(String author) {
        this.author = author;
    }
    
    @Override
    public String getChapterName() {
        return chapterName;
    }
    
    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }
    
    @Override
    public long getPublishTime() {
        return publishTime;
    }
    
    public void setPublishTime(long publishTime) {
        this.publishTime = publishTime;
    }
    
    public ContentEntity() {
    }
    
    @Ignore
    public ContentEntity(int id, String title, String author, String chapterName, long publishTime) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.chapterName = chapterName;
        this.publishTime = publishTime;
    }
    
    public ContentEntity(Content content) {
        this.id = content.getId();
        this.title = content.getTitle();
        this.author = content.getAuthor();
        this.chapterName = content.getChapterName();
        this.publishTime = content.getPublishTime();
    }
}
