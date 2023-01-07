package it.itsar.twizzoli;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentTransaction;

import it.itsar.twizzoli.databinding.ActivityDashboardBinding;

public class Dashboard extends AppCompatActivity {

    ActionBar actionbar;
    private ActivityDashboardBinding binding;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        actionbar = getSupportActionBar();
        actionbar.setTitle("profile");
        binding.navigation.setOnItemSelectedListener(item ->{
            switch (item.getItemId()){
                case R.id.nav_home:
                    actionbar.setTitle("Home");
                    HomeFragment fragment1= new HomeFragment();
                    FragmentTransaction ft1 = getSupportFragmentManager().beginTransaction();
                    ft1.replace(R.id.container,fragment1,"");
                    ft1.commit();
                    return true;
                case R.id.nav_search:
                    actionbar.setTitle("Search");
                    SearchFragment fragment2= new SearchFragment();
                    FragmentTransaction ft2 = getSupportFragmentManager().beginTransaction();
                    ft2.replace(R.id.container,fragment2,"");
                    ft2.commit();
                    return true;
                case R.id.nav_profile:
                    actionbar.setTitle("User");
                    UserFragment fragment3= new UserFragment();
                    FragmentTransaction ft3 = getSupportFragmentManager().beginTransaction();
                    ft3.replace(R.id.container,fragment3,"");
                    ft3.commit();
                    return true;
            }
            return false;

        });
    }

}
