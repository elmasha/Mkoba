package com.el.mkoba;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.core.app.ActivityCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class WalkthroughActivity extends AppCompatActivity {
  private ViewPager mSliderViewPager;
  private LinearLayout mDotsLayout;

  private TextView[] mDots;
  TextView privacy;
  private SlideAdpter slideAdpter;
  private Button NextBtn;
  private  Button BackBtn,Finishbtn;
  private Button get_started;
  private  int mCurrentPage;
  private RelativeLayout relativeLayout;
  private FirebaseAuth mAuth;

  private CheckBox checkBox;

  int PERMISSION_ALL = 1;
  String[] PERMISSIONS = {Manifest.permission.READ_CONTACTS, Manifest.permission.CALL_PHONE,
          Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION,
          Manifest.permission.READ_EXTERNAL_STORAGE};



  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_walkthrough);
    if(!hasPermissions(this, PERMISSIONS)){
      ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
    }
    mAuth = FirebaseAuth.getInstance();

    mSliderViewPager = (ViewPager) findViewById(R.id.Slide_viewPager);
    mDotsLayout = (LinearLayout) findViewById(R.id.Dot_layout);
    NextBtn  = (Button) findViewById(R.id.Next);
    BackBtn = (Button) findViewById(R.id.Back);
    checkBox = (CheckBox) findViewById(R.id.Check_terms);
    relativeLayout = findViewById(R.id.WalkLayout);
    privacy = findViewById(R.id.Terms);

    get_started = (Button) findViewById(R.id.get_Started);
    slideAdpter = new SlideAdpter(this);

    addDotsIndicator(0);
    mSliderViewPager.setAdapter(slideAdpter);
    mSliderViewPager.addOnPageChangeListener(viewListener);

    privacy.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {

        Intent toCreate = new Intent(getApplicationContext(),Terms_and_coditions.class);
        startActivity(toCreate);
      }
    });


    BackBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        mSliderViewPager.setCurrentItem(-1);
      }
    });




    NextBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {


        mSliderViewPager.setCurrentItem(+1);


      }
    });
    get_started.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {


        if (checkBox.isChecked()){

          Intent toCreate = new Intent(getApplicationContext(),CreateActivity.class);
          startActivity(toCreate);

        }else {

          Snackbar snackbar = Snackbar.make(relativeLayout, "Accept Terms and Conditions", Snackbar.LENGTH_LONG);
          snackbar.show();

        }



      }
    });

  }

  public static boolean hasPermissions(Context context, String... permissions) {
    if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
      for (String permission : permissions) {
        if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {


          return false;
        }

      }
    }
    return true;
  }



  private void addDotsIndicator(int position) {


    mDots = new TextView[3];
    mDotsLayout.removeAllViews();

    for (int i = 0 ; i< mDots.length; i++ ){


      mDots[i] = new TextView(this);
      mDots[i].setText(Html.fromHtml("&#8226;"));
      mDots[i].setTextSize(40);
      mDots[i].setTextColor(getResources().getColor(R.color.colorTransparent));
      mDotsLayout.addView(mDots[i]);

    }

    if (mDots.length > 0)
    {
      mDots[position].setTextColor(getResources().getColor(R.color.colorAccent));
    }

  }

  ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

      addDotsIndicator(position);
      mCurrentPage = position;

      if (position == 0)
      {
        NextBtn.setEnabled(true);
        BackBtn.setEnabled(false);
        BackBtn.setVisibility(View.INVISIBLE);
        get_started.setVisibility(View.INVISIBLE);
        privacy.setVisibility(View.INVISIBLE);
        get_started.setEnabled(false);
        checkBox.setVisibility(View.INVISIBLE);
        NextBtn.setText("Swipe >>");
        BackBtn.setText("");

      }else if (position == 1){

          BackBtn.setVisibility(View.VISIBLE);
          get_started.setVisibility(View.INVISIBLE);
          checkBox.setVisibility(View.VISIBLE);
          privacy.setVisibility(View.VISIBLE);
          NextBtn.setText("Next");
          NextBtn.setEnabled(true);
          get_started.setEnabled(false);
          BackBtn.setText("Back");

      }else if (position == 2){
        NextBtn.setEnabled(true);
        BackBtn.setEnabled(true);
        BackBtn.setVisibility(View.VISIBLE);
        get_started.setVisibility(View.VISIBLE);
        checkBox.setVisibility(View.INVISIBLE);
        get_started.setEnabled(true);
        privacy.setVisibility(View.INVISIBLE);
        NextBtn.setText("");
        BackBtn.setText("Back");
      }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
  };


  @Override
  protected void onStart() {
    super.onStart();

    FirebaseUser current_User = mAuth.getCurrentUser();
    String UiD = mAuth.getUid();

    if (current_User != null)
    {
      if (UiD != null){
        startActivity(new Intent(getApplicationContext(),DashBoardActivity.class));
      }

    }

  }
}
