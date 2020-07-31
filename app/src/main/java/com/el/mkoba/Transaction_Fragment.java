package com.el.mkoba;


import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;


/**
 * A simple {@link Fragment} subclass.
 */
public class Transaction_Fragment extends Fragment {

    private TextView transHistory,searchtag,back;
    private EditText searchInput;

    private  RecyclerView recyclerView;
    private FirebaseAuth mAuth;



    TabLayout tabLayout;
    TabItem pastPayment_tab;
    TabItem pendingPay_tab;
    ViewPager viewPager;

//    PagerAdapter pagerAdapter;

    public Transaction_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_transaction_, container, false);

//       tabLayout = view.findViewById(R.id.Tablayout);
//        pastPayment_tab = view.findViewById(R.id.pastpayment_tab);
//        pendingPay_tab = view.findViewById(R.id.pendingpay_tab);
//        viewPager = view.findViewById(R.id.view_pager);

       recyclerView = view.findViewById(R.id.transRecycler);
       mAuth = FirebaseAuth.getInstance();
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


//        pagerAdapter = new PagerAdapter(getChildFragmentManager(),tabLayout.getTabCount());
//        viewPager.setAdapter(pagerAdapter);


//        tabLayout.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                viewPager.setCurrentItem(tab.getPosition());
//
//                if (tab.getPosition() == 1){
//
//                }else if (tab.getPosition() == 2){
//
//                }
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//
//            }
//        });
//
//        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));


        return view;
    }


    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onStop() {
        super.onStop();

    }

    @Override
    public void onResume() {
        super.onResume();

    }
}
