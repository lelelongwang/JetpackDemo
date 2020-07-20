package com.longjunhao.jetpackdemo.db.entity;

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
    
    @PrimaryKey
    private int id;
    private String title;
    private String author;
    private String chapterName;
    private Date postedAt;
    
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
    public Date getPostedAt() {
        return postedAt;
    }
    
    public void setPostedAt(Date postedAt) {
        this.postedAt = postedAt;
    }
    
    public ContentEntity() {
    }
    
    @Ignore
    public ContentEntity(int id, String title, String author, String chapterName, Date postedAt) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.chapterName = chapterName;
        this.postedAt = postedAt;
    }
    
    public ContentEntity(Content content) {
        this.id = content.getId();
        this.title = content.getTitle();
        this.author = content.getAuthor();
        this.chapterName = content.getChapterName();
        this.postedAt = content.getPostedAt();
    }
}
