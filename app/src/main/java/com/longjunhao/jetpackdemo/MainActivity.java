package com.longjunhao.jetpackdemo;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.longjunhao.jetpackdemo.ui.ContentListFragment;

/**
 * @author Admitor
 */
public class MainActivity extends AppCompatActivity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // Add product list fragment if this is first creation
        if (savedInstanceState == null) {
            ContentListFragment fragment = new ContentListFragment();
            
            getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, fragment, ContentListFragment.TAG).commit();
        }
    }
}