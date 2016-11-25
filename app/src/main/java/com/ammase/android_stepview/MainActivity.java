package com.ammase.android_stepview;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    CostumViewPager viewPager;
    int NUMB = 3;
    ImageButton[] step;
    int[] ImageButtonId = {R.id.step_1, R.id.step_2, R.id.step_3};
    private LinearLayout View1, View2, View3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        step = new ImageButton[NUMB];
        for (int i = 0; i < NUMB; i++) {
            step[i] = (ImageButton)findViewById(ImageButtonId[i]);
        }

        viewPager = (CostumViewPager) findViewById(R.id.viewPager);
        viewPager.setAdapter(new Adapter(MainActivity.this, NUMB));
        viewPager.setCurrentItem(0);

        //Set false or true to disable or enabled swipe in view Pager you can learn the code in CostumViewPager
        viewPager.setPagingEnabled(false);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                //Set indicator success when you already finish step in viewPager
                for (int i = 0; i < NUMB; i++) {
                    if(i == position){
                        step[i].setImageResource(R.drawable.status_selected);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public class Adapter extends PagerAdapter {
        int numb;
        Context mContext;

        public Adapter(Context context, int numb) {
            this.numb = numb;
            this.mContext = context;
        }

        @Override
        public int getCount() {
            return numb;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((RelativeLayout) object);

        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            View itemView = LayoutInflater.from(mContext).inflate(R.layout.pager_item, container, false);

            View1 = (LinearLayout)itemView.findViewById(R.id.linear1);
            View2 = (LinearLayout) itemView.findViewById(R.id.linear2);
            TextView text = (TextView)itemView.findViewById(R.id.text);
            text.setText("Page "+(position+1));



            Button next = (Button)itemView.findViewById(R.id.next);
            next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Next to previous step
                    viewPager.setCurrentItem(position + 1);
                }
            });

            Button previous = (Button)itemView.findViewById(R.id.previous);
            previous.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    //Set indicator disable before you finish step in viewPager
                    for (int i = 0; i < NUMB; i++) {
                        if(i == position) {
                            step[i].setImageResource(R.drawable.status_unselected);
                        }
                    }

                    // Back to previous step
                    viewPager.setCurrentItem(position - 1);
                }
            });

            if (position == 0){
                previous.setVisibility(View.GONE);
                View1.setVisibility(View.GONE);
                View2.setVisibility(View.GONE);

            }else if(position == 1){
                View1.setVisibility(View.GONE);
                View2.setVisibility(View.VISIBLE);

            }else if(position == 2){
                previous.setVisibility(View.VISIBLE);
                next.setVisibility(View.GONE);
                View1.setVisibility(View.VISIBLE);
                View2.setVisibility(View.GONE);
            }

            container.addView(itemView);

            return itemView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((RelativeLayout) object);
        }
    }
}
