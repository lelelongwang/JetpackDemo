package com.longjunhao.jetpackdemo.db;

import com.longjunhao.jetpackdemo.db.entity.ContentEntity;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * .DataGenerator
 * Generates data to pre-populate the database
 *
 * @author Admitor
 * @date 2020/07/17
 */
public class DataGenerator {
    
    private static final String[] TITLE = new String[] {
        "Jetpack 真香", "MVVM 永恒钻石", "MVP 黄金时代", "MVC 青铜段位", "Android 系统"};
    private static final String[] SECOND_TITLE = new String[] {
        "第一篇", "第二篇", "第三篇", "第四篇"};
    private static final String[] AUTHOR = new String[] {
        "张三", "李四", "王五", "赵六", "隔壁老王", "如是"};
    private static final String[] CHAPTER_NAME = new String[] {
        "Kotlin系列", "Java系列", "Python系列", "C++系列", "PHP系列", "Ruby系列"};
    
    public static List<ContentEntity> generateContents() {
        List<ContentEntity> contents = new ArrayList<>(TITLE.length * SECOND_TITLE.length);
        for (int i = 0; i < TITLE.length; i++) {
            for (int j = 0; j < SECOND_TITLE.length; j++) {
                ContentEntity content = new ContentEntity();
                content.setTitle(TITLE[i] + " " + SECOND_TITLE[j]);
                content.setAuthor(AUTHOR[j]);
                content.setChapterName(CHAPTER_NAME[j]);
                content.setId(TITLE.length * i + j + 1);
                contents.add(content);
            }
        }
        return contents;
    }
}
