package com.intern.clcenter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.ui.AppBarConfiguration;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.intern.clcenter.Fragment.DiscoverFragment;
import com.intern.clcenter.Fragment.MyBookingFragment;
import com.intern.clcenter.Fragment.ProfileFragment;
import com.intern.clcenter.Fragment.WalletFragment;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class Home extends AppCompatActivity{

    private AppBarConfiguration mAppBarConfiguration;
    DiscoverFragment discoverFragment;
    ProfileFragment profileFragment;
    WalletFragment walletFragment;
   androidx.appcompat.widget.Toolbar toolbar;
LinearLayout llProfileClick;
MyBookingFragment myBookingFragment;

View navHeader;
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder().setDefaultFontPath("fonts/algerian.ttf").setFontAttrId(R.attr.fontPath).build());

        setContentView(R.layout.activity_home);
        toolbar = findViewById(R.id.toolbar);
         setSupportActionBar(toolbar);
         toolbar.setLogo(R.drawable.ic_group_1);
         toolbar.setCollapseIcon(R.drawable.ic_menu_black_24dp);
         toolbar.setNavigationIcon(R.drawable.ic_menu_black_24dp);
         getSupportActionBar().setTitle(null);

        discoverFragment=new DiscoverFragment();
        profileFragment =new ProfileFragment();
        walletFragment=new WalletFragment();
        myBookingFragment=new MyBookingFragment();


        final FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
       fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        fragmentTransaction.replace(R.id.discover_fragment,discoverFragment);
         fragmentTransaction.commit();



        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
        final NavigationView navigationView = findViewById(R.id.nav_view);
        navHeader = navigationView.getHeaderView(0);

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                super.onDrawerOpened(drawerView);
            }
        };

        //Setting the actionbarToggle to drawer layout
        drawer.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessary or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();

        llProfileClick = navHeader.findViewById(R.id.llProfileClick);


        llProfileClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.discover_fragment,profileFragment);
                fragmentTransaction.commitAllowingStateLoss();
                drawer.closeDrawers();
            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);

                switch (menuItem.getItemId()) {
                    case R.id.nav_home:

                        Intent  intent1=new Intent(Home.this,Home.class);
                        startActivity(intent1);
                        break;

                    case R.id.nav_jobs:

                      Intent intent=new Intent(Home.this,PostJob.class);
                      startActivity(intent);

                        break;
                    case  R.id.nav_wallet:
                        fragmentTransaction.replace(R.id.discover_fragment, walletFragment);

                        break;

                    case R.id.nav_booking:
                    fragmentTransaction.replace(R.id.discover_fragment,myBookingFragment);
                    break;
                    case R.id.nav_profilesetting:

                        Intent  intent2=new Intent(Home.this,ProfileActivity.class);
                        startActivity(intent2);
                        break;
                    case R.id.nav_signout:
                        FirebaseAuth firebaseAuth;
                        firebaseAuth=FirebaseAuth.getInstance();
                        firebaseAuth.signOut();

                        Intent  intent3=new Intent(Home.this,RegisterActivity.class);
                        startActivity(intent3);
                        finish();
                        break;






                    default:

                        break;

                } fragmentTransaction.commitAllowingStateLoss();

                drawer.closeDrawers();


                //   loadHomeFragment();

                return true;
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();

    }}