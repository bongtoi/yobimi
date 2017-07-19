package com.example.nvquang.englishgrammertestdemo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.nvquang.fragment.FragmentT;

import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */

    private boolean doubleBackToExitPressedOnce = false;
    private Fragment curFragment;
    private String categoryCurrent;

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this); // Init views

        onOpenFragment(new FragmentT(), false);

        // navigation drawer
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        // --> end navigation drawer

    }

    public void setUpWithToolbar(Toolbar toolbar){
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
    }

    public void onOpenFragment(Fragment fragment, boolean isAddToBackStack) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        curFragment = fragment;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (isAddToBackStack) {
            fragmentTransaction.replace(R.id.container, fragment).addToBackStack(null);
        } else {
            fragmentTransaction.replace(R.id.container, fragment);
        }
        fragmentTransaction.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    // Optional for press again to exit app
     @Override
     public void onBackPressed() {
         FragmentManager fm = getSupportFragmentManager();

         if (fm.getBackStackEntryCount() > 0) {
             superBackPress();
             return;
         }
         if (doubleBackToExitPressedOnce || fm.getBackStackEntryCount() != 0) {
             super.onBackPressed();
             return;
         }

         this.doubleBackToExitPressedOnce = true;
         Toast.makeText(this, "Press again to exit", Toast.LENGTH_SHORT).show();

         new Handler().postDelayed(new Runnable() {

             @Override
             public void run() {
                 doubleBackToExitPressedOnce = false;
             }
         }, 2000);

         // navigation drawer
//         DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//         if (drawer.isDrawerOpen(GravityCompat.START)) {
//             drawer.closeDrawer(GravityCompat.START);
//         } else {
//             super.onBackPressed();
//         }
         // --> end navigation drawer

     }

     public void superBackPress() {
         super.onBackPressed();
     }

    // navigation drawer
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        switch (id) {
            case R.id.nav_home:
            {
                onOpenFragment(new FragmentT(), false);
                break;
            }
            case R.id.nav_rateme:
            {
                final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
                try {
                    //startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.yobimi.bbclearningenglish")));
                } catch (android.content.ActivityNotFoundException anfe) {
                    //startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.yobimi.bbclearningenglish")));
                }
                break;
            }
            case R.id.nav_settings:
            {

                break;
            }
            case R.id.nav_feedback:
            {

                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"quangthcn7@gmail.com"});
                intent.putExtra(Intent.EXTRA_SUBJECT, "Feeback");
                intent.putExtra(Intent.EXTRA_TEXT, "Content");
                intent.setType("message/rfc822");
                startActivity(Intent.createChooser(intent, "Choose a client: "));

                break;
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    // --> end navigation drawer
}