package com.pabloserrano.androidmvp.view.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.pabloserrano.androidmvp.R;
import com.pabloserrano.androidmvp.view.fragment.MainFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.toolbar) Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_list:
                if (item.getIcon().getConstantState().equals(getResources().getDrawable(R.mipmap.ic_view_module_white_36dp).getConstantState())) {
                    item.setIcon(R.mipmap.ic_view_list_white_36dp);
                    setLayoutManager(MainFragment.LayoutManagerType.GRID_LAYOUT_MANAGER);
                } else if (item.getIcon().getConstantState().equals(getResources().getDrawable(R.mipmap.ic_view_list_white_36dp).getConstantState())) {
                    item.setIcon(R.mipmap.ic_view_module_white_36dp);
                    setLayoutManager(MainFragment.LayoutManagerType.LINEAR_LAYOUT_MANAGER);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setLayoutManager(MainFragment.LayoutManagerType layoutManagerType) {
        MainFragment userFragment = (MainFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragment);

        if (userFragment != null) {
            userFragment.setLayoutManager(layoutManagerType);
        } else {
            MainFragment newFragment = new MainFragment();

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment, newFragment);
            transaction.addToBackStack(null);
            transaction.commit();

            userFragment.setLayoutManager(layoutManagerType);
        }
    }
}
