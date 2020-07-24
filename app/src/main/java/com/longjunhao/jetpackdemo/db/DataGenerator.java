package com.longjunhao.jetpackdemo.db;

import android.util.Log;
import com.longjunhao.jetpackdemo.db.entity.ContentEntity;
import java.util.ArrayList;
import java.util.List;

/**
 * .DataGenerator
 * Generates data to pre-populate the database
 *
 * @author Admitor
 * @date 2020/07/17
 */
public class DataGenerator {
    
    private static final String[] TITLE = new String[] {
        "第一部分：Jetpack真香", "第二部分：MVVM永恒钻石", "第三部分：MVP黄金时代",
        "第四部分：MVC青铜段位", "第五部分：Android系统", "第六部分：设计模式",
        "第七部分：函数响应编程", "第八部分：应用架构设计", "第九部分：Kotlin语言",
        "第十部分：Fuchsia系统",
    };
    private static final String[] SECOND_TITLE = new String[] {
        "第一篇", "第二篇", "第三篇", "第四篇", "第五篇", "第六篇", "第七篇", "第八篇", "第九篇",
        "第十篇", "第十一篇", "第十二篇", "第十三篇", "第十四篇", "第十五篇", "第十六篇", "第十七篇",
        "第十八篇", "第十九篇", "第二十篇", "第二十一篇", "第二十二篇", "第二十三篇", "第二十四篇",
        "第二十五篇", "第二十六篇", "第二十七篇", "第二十八篇", "第二十九篇", "第三十篇", "第三十一篇",
        "第三十二篇", "第三十三篇", "第三十四篇", "第三十五篇", "第三十六篇", "第三十七篇", "第三十八篇",
        "第三十九篇", "第四十篇", "第四十一篇", "第四十二篇", "第四十三篇", "第四十四篇", "第四十五篇",
        "第四十六篇", "第四十七篇", "第四十八篇", "第四十九篇", "第五十篇",
    };
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
                if (j > AUTHOR.length - 1) {
                    content.setAuthor(AUTHOR[j % AUTHOR.length]);
                    content.setChapterName(CHAPTER_NAME[j % AUTHOR.length]);
                } else {
                    content.setAuthor(AUTHOR[j]);
                    content.setChapterName(CHAPTER_NAME[j]);
                }
                content.setId(TITLE.length * i + j + 1);
                contents.add(content);
            }
        }
        return contents;
    }
}
