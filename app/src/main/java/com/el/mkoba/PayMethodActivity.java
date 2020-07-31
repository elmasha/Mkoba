package com.el.mkoba;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

public class PayMethodActivity extends AppCompatActivity {




    private TextView Mpesa,create_tab,pending_tab,past_tab,lable,back;
    private ViewPager viewPager;
    private PagerAdapter pagerAdapter;
    private View view1,view2,view3;
    TabLayout tabLayout;
    TabItem creatPayment;
    TabItem pendingTab;
    TabItem pastTab;




    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_method);



        create_tab = findViewById(R.id.Create);
        pending_tab = findViewById(R.id.Pending);
        past_tab = findViewById(R.id.Past);
        viewPager = findViewById(R.id.ViewPager12);
        lable = findViewById(R.id.Label_pay);
        back = findViewById(R.id.PayM_Back);
        tabLayout = findViewById(R.id.tab_layout);
        create_tab = findViewById(R.id.CreatePay);
        pending_tab = findViewById(R.id.Pending);
        past_tab = findViewById(R.id.Past);





        pagerAdapter = new PagerAdapter(getSupportFragmentManager(),tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);


        tabLayout.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());

                if (tab.getPosition() == 1){

                }else if (tab.getPosition() == 2){

                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent logout = new Intent(getApplicationContext(),DashBoardActivity.class);
                logout.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(logout);
            }
        });



        pagerAdapter = new PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onPageSelected(int position) {


            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onStart() {
        super.onStart();

//        create_tab.setTextSize(18);
//        TextPaint paint = lable.getPaint();
//        float width = paint.measureText("Create Payment");
//
//        Shader textShader = new LinearGradient(0, 0, width, lable.getTextSize(),
//                new int[]{
//                        Color.parseColor("#14bfd9"),
//                        Color.parseColor("#7658A5"),
//                        Color.parseColor("#E930B5"),
//                }, null, Shader.TileMode.CLAMP);
//        create_tab.getPaint().setShader(textShader);
//        view1.setVisibility(View.VISIBLE);




    }



}