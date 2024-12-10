package com.example.callapitourdulich.activity;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.callapitourdulich.R;
import com.example.callapitourdulich.adapter.MyViewPagerAdapter;
import com.example.callapitourdulich.transformer.DepthPageTransformer;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {
    private ViewPager2 mViewPager2;
    private BottomNavigationView mBottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        mViewPager2 = findViewById(R.id.view_pager_2);
        mBottomNavigationView = findViewById(R.id.bottom_navigation);

        MyViewPagerAdapter myViewPagerAdapter = new MyViewPagerAdapter(this);
        mViewPager2.setAdapter(myViewPagerAdapter);
        // Set hiệu ứng chuyển page
        mViewPager2.setPageTransformer(new DepthPageTransformer());

        mBottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if(id == R.id.bottom_home){
                    mViewPager2.setCurrentItem(0);
                } else if(id == R.id.bottom_contact){
                    mViewPager2.setCurrentItem(1);
                } else if(id == R.id.bottom_profile){
                    mViewPager2.setCurrentItem(2);
                }
                return true;
            }
        });

        // Sự kiện khi vuốt màn hình chuyển sang các tag khác của bottomNavigation
        mViewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                switch (position){
                    case 0:
                        mBottomNavigationView.getMenu().findItem(R.id.bottom_home).setChecked(true);
                        break;
                    case 1:
                        mBottomNavigationView.getMenu().findItem(R.id.bottom_contact).setChecked(true);
                        break;
                    case 2:
                        mBottomNavigationView.getMenu().findItem(R.id.bottom_profile).setChecked(true);
                        break;
                }
            }
        });

    }


}
