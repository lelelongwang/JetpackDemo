package com.longjunhao.jetpackdemo.model;

import java.util.Date;

/**
 * .Content
 *
 * @author Admitor
 * @date 2020/07/16
 */
public interface Content {
    int getId();
    
    String getTitle();
    
    String getAuthor();
    
    String getChapterName();
    
    Date getPostedAt();
}
