package aky.akshay.algorithm.deve;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
 
public class UnitAdapter extends BaseAdapter {
	
	public static String names[] = {"Angle" , "Area" , "Current" ,"Distance", "Energy" ,"Force" ,
			"Number Base", "Power" , "Pressure" , "Radioactivity", "Temperature",
			"Time" , "Weight"};
	
	String details[] = { "Degree, Radian, Kelvin" , "Yard, Acre, Hectare" , "Ampere, Coulomb"
			,"Mile, Kilometer, Yard" , "Calorie, Joule" , "Newton, Dyne" , "Decimal, Binary, Hex",
			"Watt, HorsePower" , "Atm, Bar, Pascal" , "Curie, Rutherford" , "Celsius, Kelvin" ,
			"Hour, Min, Second" , "Kilogram, Ton, Pound"};
	
    private Activity activity;
    private static LayoutInflater inflater=null;
    private String[] data;
    public ImageButton imageLoader;
    
    /* The reason for ViewHolder pattern is:
    	The ListViews are optimized (as mentioned here).
        The number of calls to findViewById() reduces. */ 
    ViewHolder holder;
    
    
    public UnitAdapter(Activity a, String[] d) {
        activity = a;
        data=d;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        imageLoader = new ImageButton(activity.getApplicationContext());
    }
    public int getCount() {
        return data.length;
    }
 
    public Object getItem(int position) {
        return position;
    }
 
    public long getItemId(int position) {
        return position;
    }
    
    static class ViewHolder {
    	TextView title;
		TextView content;
		//ImageView image;
		int position;
		}
 
    public View getView(int position, View convertView, ViewGroup parent) {
        // if convertView is null, the view is newly inflated else, re-assign new values.
        if(convertView==null) {
        	// Inflating layout
        	convertView = inflater.inflate(R.layout.unit_custom_list, null);
            
            // Set up the ViewHolder for smooth scrolling
            holder = new ViewHolder();
        	holder.title = (TextView) convertView.findViewById(R.id.title);
        	holder.content = (TextView) convertView.findViewById(R.id.content);
        	//holder.image =(ImageView) convertView.findViewById(R.id.image);
        	// Store the holder with the view.
        	convertView.setTag(holder);
        }
        else
        	holder = (ViewHolder) convertView.getTag();
        // Assign values
        holder.title.setText(names[position]);
        holder.content.setText(details[position]);
        //holder.image.setImageResource(GridAdapter.mThumbIds[position]);
        

        return convertView;
    }
}
