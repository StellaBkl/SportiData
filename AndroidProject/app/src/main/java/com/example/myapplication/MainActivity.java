package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.room.Room;


import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {
public static Database myDatabase;
public static FragmentManager fragmentmanager;
public static FirebaseFirestore db=FirebaseFirestore.getInstance();
    Toolbar toolbar1;
    DrawerLayout dlayout;
    NavigationView navView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        myDatabase = Room.databaseBuilder(getApplicationContext(), Database.class, "athletesDB").allowMainThreadQueries().build();
        fragmentmanager = getSupportFragmentManager();

        toolbar1 = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar1);

        dlayout = findViewById(R.id.Drawer_layout);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, dlayout, toolbar1, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        dlayout.addDrawerListener(toggle);
        toggle.syncState();


        navView = findViewById(R.id.NavView);


        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {


                switch (item.getItemId()) {
                    case R.id.home:
                        item.setChecked(true);
                        fragmentmanager.beginTransaction().replace(R.id.fragment_container, new AllMainFragment()).commit();
                        toolbar1.setTitle("My Application");
                        //displayMessage("Καλως ηρθατε!");
                        dlayout.closeDrawers();
                        return true;

                    case R.id.athlites:
                        item.setChecked(true);
                        fragmentmanager.beginTransaction().replace(R.id.fragment_container, new AthlitisMainFragment()).commit();
                        //displayMessage("Επιλεχθηκαν οι αθλητες");
                        toolbar1.setTitle("ΑΘΛΗΤΕΣ");
                        dlayout.closeDrawers();
                        return true;
                    case R.id.sports:
                        item.setChecked(true);
                        fragmentmanager.beginTransaction().replace(R.id.fragment_container, new SportMainFragment()).commit();
                        toolbar1.setTitle("ΑΘΛΗΜΑΤΑ");
                        //displayMessage("Επιλεχθηκαν τα αθλήματα");
                        dlayout.closeDrawers();
                        return true;
                    case R.id.teams:
                        item.setChecked(true);
                        fragmentmanager.beginTransaction().replace(R.id.fragment_container, new OmadaMainFragment()).addToBackStack(null).commit();
                        toolbar1.setTitle("ΟΜΑΔΕΣ");
                        //displayMessage("Επιλεχθηκαν οι ομαδες");
                        dlayout.closeDrawers();
                        return true;
                    case R.id.agones:
                        item.setChecked(true);
                        fragmentmanager.beginTransaction().replace(R.id.fragment_container, new ResultMainFragment()).addToBackStack(null).commit();
                        toolbar1.setTitle("ΑΓΩΝΕΣ");
                        //displayMessage("Επιλεχθηκαν οι αγωνες");
                        dlayout.closeDrawers();
                        return true;
                    case R.id.score:
                        item.setChecked(true);
                        int i1 = item.getItemId();
                        fragmentmanager.beginTransaction().replace(R.id.fragment_container, new ResultAllMainFragment()).addToBackStack(null).commit();
                        toolbar1.setTitle("SCORE ΑΓΩΝΩΝ");
                        //displayMessage("Επιλεχθηκαν οι αγωνες");
                        dlayout.closeDrawers();
                        return true;
                }

              /*  for (int i = 1; i < 7; i++) {
                    if (navView.getMenu().getItem(i).isChecked()) {
                        navView.getMenu().getItem(i).setChecked(false);
                } else {
                    navView.getMenu().getItem(i).setChecked(true);
                }
            }*/


                return false;
            }
        });




        if (findViewById(R.id.fragment_container) != null) {
            if (savedInstanceState != null) {
                return;
            }
            fragmentmanager.beginTransaction().add(R.id.fragment_container,
                    new AllMainFragment()).commit();
            //getSupportActionBar();
            //navView.setCheckedItem(R.id.home);
        }
    }



}