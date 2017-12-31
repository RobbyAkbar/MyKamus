package es.esy.android_inyourhand.mykamus;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.ArrayList;

import es.esy.android_inyourhand.mykamus.adapters.EnglishAdapter;
import es.esy.android_inyourhand.mykamus.adapters.IndonesiaAdapter;
import es.esy.android_inyourhand.mykamus.helpers.KamusHelper;
import es.esy.android_inyourhand.mykamus.models.KamusEngModel;
import es.esy.android_inyourhand.mykamus.models.KamusIndModel;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    private final static String SELECTED_TAG = "selected_index";
    private static int selectedIndex;
    private RecyclerView recyclerView;
    private Toolbar toolbar;
    private EnglishAdapter englishAdapter;
    private ArrayList<KamusEngModel> kamusEngModels;
    private IndonesiaAdapter indonesiaAdapter;
    private ArrayList<KamusIndModel> kamusIndModels;
    private KamusHelper kamusHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.english_indonesia);
        setSupportActionBar(toolbar);
        recyclerView = findViewById(R.id.recyclerview);
        kamusHelper = new KamusHelper(this);
        englishAdapter= new EnglishAdapter(this);
        indonesiaAdapter = new IndonesiaAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(englishAdapter);
        kamusHelper.open();
        kamusEngModels = kamusHelper.getAllDataENG();
        kamusIndModels = kamusHelper.getAllDataIND();
        kamusHelper.close();
        englishAdapter.addItem(kamusEngModels);

        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if (savedInstanceState!=null){
            navigationView.getMenu().findItem(savedInstanceState.getInt(SELECTED_TAG)).setChecked(true);
            if (savedInstanceState.getInt(SELECTED_TAG)==R.id.nav_eng){
                toolbar.setTitle(R.string.english_indonesia);
                recyclerView.setAdapter(englishAdapter);
                englishAdapter.addItem(kamusEngModels);
            } else if (savedInstanceState.getInt(SELECTED_TAG)==R.id.nav_ind){
                toolbar.setTitle(R.string.indonesia_english);
                recyclerView.setAdapter(indonesiaAdapter);
                indonesiaAdapter.addItem(kamusIndModels);
            }
            return;
        }

        selectedIndex = R.id.nav_eng;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.options_menu, menu);
        final MenuItem search = menu.findItem(R.id.menu_search);
        SearchView searchView = (SearchView) search.getActionView();
        searchView.setMaxWidth(R.dimen.search_max_width);
        searchView.setQueryHint(getResources().getString(R.string.search_text_in_here));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                kamusHelper.open();
                kamusEngModels = kamusHelper.getDataByNameENG(newText);
                kamusIndModels = kamusHelper.getDataByNameIND(newText);
                kamusHelper.close();
                if (selectedIndex==R.id.nav_eng){
                    recyclerView.setAdapter(englishAdapter);
                    englishAdapter.addItem(kamusEngModels);
                } else if (selectedIndex==R.id.nav_ind){
                    recyclerView.setAdapter(indonesiaAdapter);
                    indonesiaAdapter.addItem(kamusIndModels);
                }
                return false;
            }
        });
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SELECTED_TAG, selectedIndex);
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_eng:
                if (!item.isChecked()){
                    selectedIndex = R.id.nav_eng;
                    item.setChecked(true);
                    toolbar.setTitle(R.string.english_indonesia);
                    recyclerView.setAdapter(englishAdapter);
                    englishAdapter.addItem(kamusEngModels);
                }
                drawer.closeDrawer(GravityCompat.START);
                return true;
            case R.id.nav_ind:
                if (!item.isChecked()){
                    selectedIndex = R.id.nav_ind;
                    item.setChecked(true);
                    toolbar.setTitle(R.string.indonesia_english);
                    recyclerView.setAdapter(indonesiaAdapter);
                    indonesiaAdapter.addItem(kamusIndModels);
                }
                drawer.closeDrawer(GravityCompat.START);
                return true;
            case R.id.nav_exit:
                if(!item.isChecked()){
                    exit();
                }
                drawer.closeDrawer(GravityCompat.START);
                return true;
        }
        return false;
    }

    private void exit(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AppCompatAlertDialogStyle);
        builder.setMessage(R.string.dialog_exit)
                .setCancelable(false)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        MainActivity.this.finish();
                    }
                })
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                })
                .show();
    }
}
