package com.example.hasalp.notepad;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import java.util.List;
import java.util.Vector;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        listenerHandler();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = this.getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.addButton:{
                Intent addActivity = new Intent(this, AddActivity.class);
                startActivity(addActivity);
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void init(){
        addButton = (FloatingActionButton) findViewById(R.id.floatingButton_add);

        ListFragment listFragment = new ListFragment();
        if(getResources().getConfiguration().orientation==Configuration.ORIENTATION_LANDSCAPE) {
            ContentFragment contentFragment = new ContentFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.leftPane, listFragment).commit();
            getSupportFragmentManager().beginTransaction().add(R.id.rightPane, contentFragment).commit();
        }else{
            initalizePages();
        }
    }

    private void listenerHandler(){
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addActivity = new Intent(getApplicationContext(), AddActivity.class);
                startActivity(addActivity);
            }
        });

    }

    private void initalizePages() {
        List<Fragment> pages = new Vector<Fragment>();
        pages.add(Fragment.instantiate(this,ListFragment.class.getName()));
        pages.add(Fragment.instantiate(this,ContentFragment.class.getName()));
        PagerAdapter pagerAdapter = new PagerAdapter(this.getSupportFragmentManager(), pages);

        CustomViewPager viewPager = (CustomViewPager) findViewById(R.id.mainPane);
        viewPager.setAdapter(pagerAdapter);
    }

    @Override
    public void onBackPressed() {
        if (getResources().getConfiguration().orientation!=Configuration.ORIENTATION_LANDSCAPE) {
            CustomViewPager viewPager = (CustomViewPager) findViewById(R.id.mainPane);
            if (viewPager.getCurrentItem() == 0)
                super.onBackPressed();
            else
                viewPager.setCurrentItem(0);
        }else
            super.onBackPressed();
    }
}
