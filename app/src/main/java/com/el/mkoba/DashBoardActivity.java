package com.el.mkoba;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import com.google.android.material.snackbar.Snackbar;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class DashBoardActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private CoordinatorLayout coordinatorLayout;
    private FirebaseAuth mAuth;
    private long backPressedTime;
    private Toast backToast;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            Fragment SelectedFragment = null;
            switch (item.getItemId()) {

                case R.id.navigation_transaction:
                    startActivity(new Intent(getApplicationContext(),PayMethodActivity.class));
                    break;

                case R.id.navigation_profile:
                    SelectedFragment = new Profile_Fragment();
                    break;

                case R.id.navigation_notifications:
                    SelectedFragment = new Notifictaion_Fragment();
                    break;
                case R.id.navigation_support:
                    SelectedFragment = new SurpportFragment();
                    break;
            }



             return loadFragment(SelectedFragment);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        loadFragment(new Profile_Fragment());
        mTextMessage = (TextView) findViewById(R.id.message);
        coordinatorLayout = findViewById(R.id.layout32);
        mAuth = FirebaseAuth.getInstance();


    }


    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.ViewPager, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    @Override
    protected void onStart() {
        super.onStart();



        if (!isConnected(getApplicationContext())){
            showSnackBaroffline(getApplicationContext());
        }else {


        }

//        FirebaseUser current = mAuth.getCurrentUser();
//        String user_id = current.getUid();
//        if (user_id != null){
//
//
//        }else {
//
//            startActivity(new Intent(getApplicationContext(),LogInActivity.class));
//            finish();
//
//        }


    }


    @Override
    protected void onStop() {
        super.onStop();
    }



    public void showSnackBaroffline(Context context) {
        Snackbar snackbar = Snackbar.make(coordinatorLayout, "Offline: Check Network Connection", Snackbar.LENGTH_LONG);
        snackbar.show();

    }

    public void showSnackBarOnline(Context context) {
        Snackbar snackbar = Snackbar.make(coordinatorLayout, "Back Online", Snackbar.LENGTH_LONG);
        snackbar.show();

    }

    public boolean isConnected(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnectedOrConnecting()) {

            NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            NetworkInfo data = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);


            if (data != null && data.isConnected() || wifi != null && wifi.isConnected())
                return true;
            else return false;


        } else {
            return false;
        }


    }


    @Override
    public void onBackPressed() {
        if (backPressedTime + 3000 > System.currentTimeMillis()) {
            backToast.cancel();
        super.onBackPressed();
            Intent a = new Intent(Intent.ACTION_MAIN);
            a.addCategory(Intent.CATEGORY_HOME);
            a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(a);
            finish();
            return;

    }else {
            backToast = Toast.makeText(getBaseContext(), "Double tap to exit", Toast.LENGTH_SHORT);
            backToast.show();
        }

        backPressedTime = System.currentTimeMillis();
    }
}
