package eu.ase.proiect;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

import eu.ase.proiect.fragments.AllBooksFragment;
import eu.ase.proiect.fragments.BooksReadFragment;
import eu.ase.proiect.fragments.FavoriteFragment;
import eu.ase.proiect.util.Book;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    private Fragment currentFragment;
    private ArrayList<Book> listBooks = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        configNavigation();

        initComponents();
        openDefaultFragment(savedInstanceState);

    }

    private void configNavigation() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }

    private void initComponents() {
        navigationView=findViewById(R.id.nav_view);

       // select item din meniu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if(item.getItemId()==R.id.nav_all_books){
                   currentFragment = AllBooksFragment.newInstance(listBooks);
                }
                else if(item.getItemId() == R.id.nav_favorite){
                    currentFragment = new FavoriteFragment();
                }
                else if(item.getItemId() == R.id.nav_books_read) {
                    currentFragment = new BooksReadFragment();
                }

                Toast.makeText(getApplicationContext(),
                        getString(R.string.show_option,item.getTitle()),
                        Toast.LENGTH_LONG).show();

                openFragment();
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });

    }


    /*********   FRAGMENTE    *********/

    private void openFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_frame_container, currentFragment)
                .commit();
    }

    private void openDefaultFragment(Bundle saveInstanceState){
        if(saveInstanceState == null) {
            currentFragment =  AllBooksFragment.newInstance(listBooks);
            openFragment();
            navigationView.setCheckedItem(R.id.nav_all_books);
        }
    }

}