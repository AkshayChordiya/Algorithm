package aky.akshay.algorithm.deve;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;

public class QuickStart extends Activity {
	
    ViewPager mPager;  
    private PagerAdapter mAdapter;
    private static int VIEWS = 4;
    private CharSequence titleId;
    PagerTabStrip  mTitleStrip;
    
    // private static final String[] CONTENT = new String[] { "Welcome" , "Gestures" ,
	//	"Useful popups" , "Let's start" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//Remove title bar
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_quick_start);
		mAdapter = new QuickAdapter();	
		mTitleStrip = (PagerTabStrip ) findViewById(R.id.title);
		mTitleStrip.setTextColor(getResources().getColor(R.color.androidblue));
		mTitleStrip.setTabIndicatorColor(getResources().getColor(R.color.androidblue));
		mPager = (ViewPager) findViewById(R.id.pager);
		mPager.setAdapter(mAdapter);	
		
		}
	
	private class QuickAdapter extends PagerAdapter{	
		
		@Override
        public CharSequence getPageTitle(int position) {
        	switch(position){
        	case 0:
        		titleId = "Welcome";
        		break;
        	case 1:
        		titleId = "Gestures";
                 break;
            case 2:
            	titleId = "Intiuitive popups";
                 break;
            case 3:
            	titleId = "Let's start";
             	break;       		
        	}
            return titleId;
        }
		        
        @Override
        public int getCount() {
                return VIEWS;
        }

    /**
     * Create the page for the given position.  The adapter is responsible
     * for adding the view to the container given here, although it only
     * must ensure this is done by the time it returns from
     * {@link #finishUpdate(android.view.ViewGroup)}.
     *
     * @param collection The containing View in which the page will be shown.
     * @param position The page position to be instantiated.
     * @return Returns an Object representing the new page.  This does not
     * need to be a View, but can be some other container of the page.
     */
        @Override
        public Object instantiateItem(ViewGroup collection, int position) {
        	LayoutInflater inflater = (LayoutInflater) collection.getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);    
        	 int resId = 0;
            switch (position) {
            case 0:
                resId = R.layout.quick_welcome;
                break;
            case 1:
                resId = R.layout.quick_gesture;
                break;
            case 2:
                resId = R.layout.quick_popups;
                break;
            case 3:
            	resId = R.layout.quick_end;
            	break;
            }
            View view = inflater.inflate(resId, null);
            ((ViewPager) collection).addView(view , 0);
            
            switch (position) {
            case 0:
            	Button b = (Button) view.findViewById (R.id.button1);
                b.setOnClickListener (new View.OnClickListener(){

					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						new AlertDialog.Builder(QuickStart.this)
						.setTitle("Notice")
						.setIcon(android.R.drawable.ic_dialog_info)
						.setMessage("Quick Guide can be accessed again from Help or Settings")
						.setPositiveButton(android.R.string.ok, new OnClickListener(){

							@Override
							public void onClick(DialogInterface arg0, int arg1) {
								// TODO Auto-generated method stub
								finish();
							}})
						.show();						
					}});
                break;
            case 3:
            	Button end = (Button) view.findViewById (R.id.button1);
                end.setOnClickListener (new View.OnClickListener(){

					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						finish();
					}});
            	break;
            }
            
            return view;
            
        	/*
                TextView tv = new TextView(getBaseContext());
                tv.setText(getString(R.string.app_name) + position);
                tv.setTextColor(Color.WHITE);
                tv.setTextSize(30);
               
                collection.addView(tv,0);
               
                return tv;
                */
        }
        

    /**
     * Remove a page for the given position.  The adapter is responsible
     * for removing the view from its container, although it only must ensure
     * this is done by the time it returns from {@link #finishUpdate(android.view.ViewGroup)}.
     *
     * @param collection The containing View from which the page will be removed.
     * @param position The page position to be removed.
     * @param view The same object that was returned by
     * {@link #instantiateItem(android.view.View, int)}.
     */
        @Override
        public void destroyItem(View arg0, int arg1, Object arg2) {
            ((ViewPager) arg0).removeView((View) arg2);
        }


/**
 * Determines whether a page View is associated with a specific key object
 * as returned by instantiateItem(ViewGroup, int). This method is required
 * for a PagerAdapter to function properly.
 * @param view Page View to check for association with object
 * @param object Object to check for association with view
 * @return
 */
        @Override
        public boolean isViewFromObject(View view, Object object) {
                return (view==object);
        }

       
    /**
     * Called when the a change in the shown pages has been completed.  At this
     * point you must ensure that all of the pages have actually been added or
     * removed from the container as appropriate.
     * @param arg0 The containing View which is displaying this adapter's
     * page views.
     */
        @Override
        public void finishUpdate(ViewGroup arg0) {}
       

        @Override
        public void restoreState(Parcelable arg0, ClassLoader arg1) {}

        @Override
        public Parcelable saveState() {
                return null;
        }

        @Override
        public void startUpdate(ViewGroup arg0) {}

}

}
