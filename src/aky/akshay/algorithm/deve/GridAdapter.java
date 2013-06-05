package aky.akshay.algorithm.deve;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class GridAdapter extends BaseAdapter {
	
    private Context mContext;
    
    // references to images
    public static Integer[] mThumbIds = {
    		R.drawable.ic_launcher, R.drawable.ic_launcher,
            R.drawable.ic_launcher, R.drawable.ic_launcher,
            R.drawable.ic_launcher, R.drawable.ic_launcher,
            R.drawable.ic_launcher, R.drawable.ic_launcher,
            R.drawable.ic_launcher, R.drawable.ic_launcher,
            R.drawable.ic_launcher, R.drawable.ic_launcher
    };

    public GridAdapter(Context c) {
        mContext = c;
    }

    public int getCount() {
        return mThumbIds.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
	public View getView(int position, View convertView, ViewGroup parent) { // if it's not recycled, initialize some attributes
		View view = convertView;
		 if(convertView==null){
			 view = View.inflate(mContext, R.layout.main_custom_grid, null);
			 TextView tv = (TextView)view.findViewById(R.id.text);
			 tv.setText(Algorithm.classes[position]);
			 ImageView iv = (ImageView)view.findViewById(R.id.image);
			 iv.setImageResource(mThumbIds[position]);
		  }
        return view;
        }
    }