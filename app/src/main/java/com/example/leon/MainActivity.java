package com.example.leon;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        /**
         * Bottom Navigation
         */
        bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.history);
    }

    /**
     * Two windows
     * 1) MicFragment
     * 2) History fragment
     */
    MicFragment micFragment = new MicFragment();
    HistoryFragment historyFragment = new HistoryFragment();

    /**
     *
     * Switch case to Navigate the windows
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.mic:
                getSupportFragmentManager().beginTransaction().replace(R.id.container, micFragment).commit();
                return true;

            case R.id.history:
                getSupportFragmentManager().beginTransaction().replace(R.id.container, historyFragment).commit();
                return  true;
        }
        return false;
    }
}