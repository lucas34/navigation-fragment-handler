package fr.nelaupe.navigation_handler;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import fr.nelaupe.NavigationFragmentHandler;
import fr.nelaupe.navigation_handler.fragment.BaseFragment;
import fr.nelaupe.navigation_handler.fragment.SimpleFragment;

public class MainActivity extends AppCompatActivity {

    private NavigationFragmentHandler<BaseFragment> mFragmentHandler;
    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFragmentHandler = new NavigationFragmentHandler<>(this, R.id.content);

        if (savedInstanceState == null) {
            mFragmentHandler.showMain(new SimpleFragment());
        }

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(android.R.drawable.ic_menu_add);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                menuItem.setChecked(false);
                mDrawerLayout.closeDrawers();

                Bundle args = new Bundle();
                switch (menuItem.getItemId()) {

                    case R.id.menu_home:
                        mFragmentHandler.showMain(new SimpleFragment());
                        return true;

                    case R.id.fragment_1:
                        args.putInt("fragment", 1);
                        mFragmentHandler.replaceContent(new SimpleFragment(), args);
                        return true;

                    case R.id.fragment_2:
                        args.putInt("fragment", 2);
                        mFragmentHandler.replaceContent(new SimpleFragment(), args);
                        return true;

                    case R.id.fragment_3:
                        args.putInt("fragment", 3);
                        mFragmentHandler.replaceContent(new SimpleFragment(), args);
                        return true;

                    case R.id.fragment_4:
                        args.putInt("fragment", 4);
                        mFragmentHandler.replaceContent(new SimpleFragment(), args);
                        return true;

                    case R.id.fragment_5:
                        args.putInt("fragment", 5);
                        mFragmentHandler.replaceContent(new SimpleFragment(), args);
                        return true;

                    case R.id.fragment_6:
                        args.putInt("fragment", 6);
                        mFragmentHandler.replaceContent(new SimpleFragment(), args);
                        return true;

                }

                return false;
            }
        });

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
//        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, "OPEN","" R.string.close_drawer);
//        mDrawerLayout.setDrawerListener(actionBarDrawerToggle);
//        actionBarDrawerToggle.syncState();

    }

    @Override
    public void onBackPressed() {
        if (mFragmentHandler.getDeepness() == 0) {
            super.onBackPressed();
        } else {
            mFragmentHandler.popCurrentFragment();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public NavigationFragmentHandler<BaseFragment> getHandler() {
        return mFragmentHandler;
    }

}