package com.el.mkoba;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SlideAdpter extends PagerAdapter {

    Context mContext;
    LayoutInflater layoutInflater;

    public SlideAdpter(Context context) {
        mContext = context;
    }

    public int[] slideIamage = {

            R.drawable.buyer,
            R.drawable.safepay,
            R.drawable.account
    };

    public String[] slideHeadings = {
            "Mkoba",
            "Safe payment",
            "Registration"
    };
    public String[] slideDesc = {
            "Mkoba app Allows buyers and sellers transact safely with protection\n" +
                    "and always protecting your money.",

            "Do you have a problem with online transaction payments and delivery?.\n" +
                    "Mkoba is the answer.",

            "Mkoba app allows buyers and sellers to easily transact by \n" +
                    "by protecting your money and time.",


    };

    @Override
    public int getCount() {
        return slideHeadings.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (RelativeLayout) object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        layoutInflater = (LayoutInflater) mContext.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide_layout, container, false);

        ImageView slideImage = (ImageView) view.findViewById(R.id.SlideImage);
        TextView slideHeading = (TextView) view.findViewById(R.id.Slide_Heading);
        TextView slidedesc = (TextView) view.findViewById(R.id.Slide_Desc);

        slideImage.setImageResource(slideIamage[position]);
        slideHeading.setText(slideHeadings[position]);
        slidedesc.setText(slideDesc[position]);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((RelativeLayout) object);
    }
}