package com.example.thearbiter.thriftbooksnepal.Adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.thearbiter.thriftbooksnepal.R;
import com.squareup.picasso.Picasso;

/**
 * Created by Aadesh Rana on 03-05-17.
 */

public class SlideShowAdapter extends PagerAdapter {



        public int x[];
        String xyz[];
        private Context context;
        private LayoutInflater inflater;

        public  SlideShowAdapter(Context context,String[] a) {
            this.context = context;
            x = new int[] {R.drawable.temp,R.drawable.background};
            xyz = new String[a.length];
            for(int i =0;i<a.length;i++){
                xyz[i]=a[i];
                Log.d("valuesare","kk"+xyz[i]);
            }

        }



        @Override
        public int getCount() {
            return xyz.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==(LinearLayout)object;
        }
        @Override
        public Object instantiateItem(ViewGroup container, int position) {


            inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.slider_layout,container,false);
            ImageView imageView = (ImageView)view.findViewById(R.id.swipImage1);
            Picasso.with(context).load(xyz[position]).placeholder(R.drawable.noimageplaceholder).into(imageView);


            container.addView(view);
            return view;
        }
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((LinearLayout) object);
        }
    }


