package com.plaglabs.pillreminder.app;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import com.plaglabs.pillreminder.app.NavDrawer.AbstractNavDrawerActivity;
import com.plaglabs.pillreminder.app.NavDrawer.NavDrawerActivityConfiguration;
import com.plaglabs.pillreminder.app.NavDrawer.NavDrawerAdapter;
import com.plaglabs.pillreminder.app.NavDrawer.NavDrawerItem;
import com.plaglabs.pillreminder.app.NavDrawer.NavMenuItem;
import com.plaglabs.pillreminder.app.NavDrawer.NavMenuSection;
import com.plaglabs.pillreminder.app.PillReminder.PillsReminderFragment;
import com.plaglabs.pillreminder.app.Pills.PillsFragment;

import SQLite.Model.PillReminder;

public class MainActivity extends AbstractNavDrawerActivity {
    public static final int DEBUG = 0;
    private static final int TIME_INTERVAL = 2000;
    private long mBackPressed;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if ( savedInstanceState == null ) {
            Fragment fragment;
            fragment = PillsReminderFragment.newInstance(PillReminder.STATE_ACTIVE);
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content_frame, fragment)
                    .addToBackStack("Pills1")
                    .commit();
        }
    }

    @Override
    protected NavDrawerActivityConfiguration getNavDrawerConfiguration() {

        NavDrawerItem[] menu = new NavDrawerItem[] {
                NavMenuItem.create(101, getResources().getString(R.string.navdrawer_item_activepills), R.drawable.pill_green, true, this),
                NavMenuItem.create(102,getResources().getString(R.string.navdrawer_item_pills), R.drawable.pill_red, true, this),
                NavMenuItem.create(103,getResources().getString(R.string.navdrawer_item_archived),R.drawable.pill_blue, true, this),
                NavMenuItem.create(104,getResources().getString(R.string.navdrawer_item_deleted),R.drawable.pill_grey, true, this),
                NavMenuSection.create(200, getResources().getString(R.string.others)),
                NavMenuItem.create(201, getResources().getString(R.string.navdrawer_item_feedback), R.drawable.ic_action_email_dark, true, this),
                NavMenuItem.create(202, getResources().getString(R.string.navdrawer_item_help),R.drawable.ic_action_help_dark, true, this)};
        NavDrawerActivityConfiguration navDrawerActivityConfiguration = new NavDrawerActivityConfiguration();
        navDrawerActivityConfiguration.setMainLayout(R.layout.activity_main);
        navDrawerActivityConfiguration.setDrawerLayoutId(R.id.drawer_layout);
        navDrawerActivityConfiguration.setLeftDrawerId(R.id.left_drawer);
        navDrawerActivityConfiguration.setNavItems(menu);
        navDrawerActivityConfiguration.setDrawerShadow(R.drawable.drawer_shadow);
        navDrawerActivityConfiguration.setDrawerOpenDesc(R.string.drawer_open);
        navDrawerActivityConfiguration.setDrawerCloseDesc(R.string.drawer_close);
        navDrawerActivityConfiguration.setBaseAdapter(
                new NavDrawerAdapter(this, R.layout.navdrawer_item, menu ));
        return navDrawerActivityConfiguration;
    }

    @Override
    protected void onNavItemSelected(int id) {
        Fragment fragment;
        switch (id) {
            case 101:
                //PillsReminderFragment pillsReminderFragment = null;
                fragment = PillsReminderFragment.newInstance(PillReminder.STATE_ACTIVE);
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content_frame, fragment)
                        .addToBackStack("pills2")
                        .commit();
                break;
            case 102:
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content_frame, new PillsFragment())
                        .addToBackStack("pills3")
                        .commit();
                break;

            case 103:
                fragment = PillsReminderFragment.newInstance(PillReminder.STATE_ARCHIVE);
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content_frame, fragment)
                        .addToBackStack("pills2")
                        .commit();
                break;

            case 104:
                fragment = PillsReminderFragment.newInstance(PillReminder.STATE_DELETED);
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content_frame, fragment)
                        .addToBackStack("pills2")
                        .commit();
                break;
            case 201:
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/html");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"hborrasaleixandre@gmail.com"});
                intent.putExtra(Intent.EXTRA_SUBJECT, "FeedBack");
                intent.putExtra(Intent.EXTRA_TEXT, "");
                startActivity(intent);
                break;
            case 202:
                String url = "https://github.com/plagueis/PillReminder/tree/master#pillreminder";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
                break;

        }
    }

    @Override
    public void onBackPressed()
    {
        if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis())
        {
            super.onBackPressed();
            return;
        }
        else { Toast.makeText(getBaseContext(), R.string.exit, Toast.LENGTH_SHORT).show(); }

        mBackPressed = System.currentTimeMillis();
    }

}