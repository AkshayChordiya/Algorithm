package com.ls.list.threed;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

public class ThreeDListView extends ListView
{   
    private static class StubView extends LinearLayout
    {
        public final static String TAG = "StubView";
        public StubView(final Context context)
        {
            super(context);    
            this.setOrientation(LinearLayout.VERTICAL);
            this.setTag(TAG);
        }        
    }
    
    public final static int MAX_ELEMENT_PADDING = 17;
    
    public final static String TAG = ThreeDListView.class.getName();
    
    private final static int INITIAL_DISTANCE_FROM_CENTER = -1;
    
    private final static int MAX_ALPHA = 255;
    
    private final static int MIN_ALPHA = 0; 
    
    
    private final static int SCROLLING_TICK_TIME = 16;     
    
    private ScrollingDynamics dynamics;
    
    private ListAdapterWrapper listAdapterWrapper;       
    
    private int highlightedPosition;  
    
    private int highlightedItemCurrentPaddingTop;
    
    private View childToSelect;  
        
    private StubView footerView ;
            
    private StubView headerView ; 
    
    private float minDstanceFromCenter;
    
    private boolean onAutoScroll;
    
    private boolean onNativeScroll;
    
    private boolean useChildDrawingCache;
    
    private Paint paint;
    
    private OnScrollListener scrollListener;    
    
    
    public ThreeDListView(final Context context)
    {
        super(context);        
        initView(context);           
    }
       
    public ThreeDListView(final Context context,final AttributeSet theSet)
    {
        super(context,theSet);        
        initView(context);    
    }
    
    public ThreeDListView(final Context context,final AttributeSet theSet,final int defStyle)
    {
        super(context,theSet,defStyle);        
        initView(context);    
    }
 
    public void disableOverScroll()
    {
        int sdkVersion = Build.VERSION.SDK_INT;        
        
        if (sdkVersion >= 9) {
            try {
                Method m = this.getClass().getMethod("setOverScrollMode",
                        new Class[] { int.class });
                m.invoke(this, Integer.valueOf(2));
            } catch (SecurityException e2) {
                e2.printStackTrace();
            } catch (NoSuchMethodException e2) {
                e2.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        
    }
        
    @Override 
    public boolean drawChild(final Canvas canvas, final View child, final long drawingTime)
    {           
        Object tag = child.getTag();       
        if(tag != null)
        {
            if(tag == StubView.TAG)
            {                
                return false;
            }
        }       
                       
        final int childWidth = child.getWidth();
        final int childHeight = child.getHeight();       
        final int centerY = childHeight / 2;           
        final int top = child.getTop();
        final int left = child.getLeft();
        final float halfHeight = getHeight() / 2f;      
        final float distFromCenter = Math.abs(( top + centerY - halfHeight)/ halfHeight);  
            
        validateInitialSlection(child, halfHeight, centerY);
        
        if(distFromCenter < this.minDstanceFromCenter||
                (this.minDstanceFromCenter == INITIAL_DISTANCE_FROM_CENTER))
        {
            this.minDstanceFromCenter = distFromCenter;   
            this.childToSelect = child;  
            this.highlightedPosition = this.getPositionForView(child);
            this.highlightedItemCurrentPaddingTop = top;
        }    
        
        Bitmap bitmap = null;
        
        if(useChildDrawingCache)
        {
            bitmap = child.getDrawingCache();
        }
        
        final float scaleCoef = getEllipticValue(distFromCenter);
        if (bitmap == null) {               
            bitmap = Bitmap.createBitmap(childWidth,childHeight,Bitmap.Config.ARGB_8888);            
            Canvas view = new Canvas(bitmap);           
            child.draw(view);                    
        }        
       
        paint.setAntiAlias(true);
        paint.setAlpha((int)((MAX_ALPHA - MIN_ALPHA) * scaleCoef + MIN_ALPHA));         
        canvas.drawBitmap(bitmap, null, getBounds(top, (int)(left-((1 - scaleCoef) * MAX_ELEMENT_PADDING)),
                                        childWidth, childHeight, scaleCoef), paint);        
        return false;
    }
    
    private Rect getBounds(final int theTop,final int theLeft,final int theWidth,final int theHeight,final float theScaleF)
    {    	
        final int scaledWidth = (int)(theWidth * theScaleF);
        final int scaledHeight = (int)(theHeight * theScaleF);
        final int paddingTop = (theHeight - scaledHeight)/2;        
        Rect bounds = new Rect(theLeft , theTop + paddingTop,theLeft + scaledWidth, theTop + paddingTop +scaledHeight);
        return bounds;
    }
    
    
    @Override
    public ListAdapter getAdapter()
    {
        if(this.listAdapterWrapper != null)
        {
            return this.listAdapterWrapper.getAdapter();
        } else {           
            return null;
        }
    }
    
    public int getLastHighlightedItemPosition()
    {
        return this.highlightedPosition;
    }
    
    @Override
    public void onDraw(final Canvas theCanvas)
    {
        this.minDstanceFromCenter = INITIAL_DISTANCE_FROM_CENTER;      
        super.onDraw(theCanvas); 
    }
    
    
    @Override
    public boolean onTrackballEvent(MotionEvent theEvent)
    {
    	return true;
    }
   
    @Override
    public void onSizeChanged(final int theW,final int theH,final int theOldW,final int theOldH)
    {
        super.onSizeChanged(theW, theH, theOldW, theOldH);
        applyNewSize();
    }
    
    @Override
    public boolean onTouchEvent(final MotionEvent theEvent)
    {
        boolean result = super.onTouchEvent(theEvent);
        if(theEvent.getAction()==MotionEvent.ACTION_DOWN)
        {
            this.onAutoScroll = false;          
        }
        
        if(theEvent.getAction()==MotionEvent.ACTION_UP)
        {
            if(!this.onNativeScroll)
            {
                this.lockPosition(this.childToSelect);
            }
        }
        return result;
    }
    
    @Override 
    public void setAdapter(final ListAdapter theAdapter)
    {
    	if(this.footerView == null)
    	{
    		footerView = new StubView(this.getContext());         
            super.addFooterView(footerView);
    	}
    	
        this.listAdapterWrapper.setAdapter(theAdapter);     
        if(theAdapter != null)
        {
            super.setAdapter(this.listAdapterWrapper);
        } else {
            super.setAdapter(null);
        }
    }
    
    
    @Override public void setOnScrollListener(final OnScrollListener theListener)
    {
        this.scrollListener = theListener;
    }
   
    /**
     * Enabling of drawing cache will increase performance, but cached views should be without transparency
     * @param theUse if true - usage of drawing cache will be enabled
     */
    public void useCHildDrawingCache(final boolean theUse)
    {
        this.useChildDrawingCache = theUse;       
    }  
   
    private void applyNewSize()
    {
        int stubsHeight = this.getHeight()/2;
        this.offsetTopAndBottom( - stubsHeight);        
        footerView.setMinimumHeight(stubsHeight);
        headerView.setMinimumHeight(stubsHeight);        
    }
    
    private float getEllipticValue(final float theY)
    {
        float result = (float)Math.sqrt(1 - theY);
        if(result > 1)
        {
            return 1;
        } else
        {
            return result;
        }       
    }
    
    private void initListAdapterWrapper()
    {
        this.listAdapterWrapper = new ListAdapterWrapper(this);       
    }
  
    private void initHeaderStub(final Context theContext)
    {        
        headerView = new StubView(theContext);       
        super.addHeaderView(headerView);       
    }
       
    private void initView(final Context theContext)
    {        
        paint = new Paint();
        dynamics = new ScrollingDynamics(this);
        this.useChildDrawingCache = true;
        this.onNativeScroll = false;
        this.setDividerHeight(0);
        this.setSelector(new ColorDrawable(Color.TRANSPARENT));       
        this.setScrollListener();  
        this.initHeaderStub(theContext);        
        this.initListAdapterWrapper();
        this.disableOverScroll();
    }
    
    private synchronized void lockPosition(final View theChildtoSelect)
    {     	
        if((theChildtoSelect!= null)&&(!this.onAutoScroll)&&
                (this.minDstanceFromCenter != INITIAL_DISTANCE_FROM_CENTER))
        {             
            this.onAutoScroll = true;              
            this.dynamics.reSetParameters(this.highlightedItemCurrentPaddingTop, 
                    this.getHeight()/2 - theChildtoSelect.getHeight()/2, this.highlightedPosition);
            //this.minDstanceFromCenter = INITIAL_DISTANCE_FROM_CENTER;      
            this.post(new Runnable()
            {                
                @Override
                public void run()
                {                     
                    if(ThreeDListView.this.onAutoScroll)
                    {                        
                        if(ThreeDListView.this.dynamics.update())
                        {                        
                            postDelayed(this, SCROLLING_TICK_TIME);                       
                        }else{      
                        	lockSelection(childToSelect);                           
                        }
                    }
                }
            });
        }             
    }
        
    private void lockSelection(final View theChildtoSelect)
    {                        
        //this.minDstanceFromCenter = INITIAL_DISTANCE_FROM_CENTER;                    
        this.onAutoScroll = false;
    }
    
    private void setScrollListener()
    {
        super.setOnScrollListener(new OnScrollListener()
        {            
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                    int visibleItemCount, int totalItemCount)
            {
                if(ThreeDListView.this.scrollListener != null)
                {
                   ThreeDListView.this.scrollListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
                }                
            }
            
            @Override
            public void onScrollStateChanged(final AbsListView view,final int scrollState)
            {               
                if(scrollState == OnScrollListener.SCROLL_STATE_IDLE)
                {   
                    ThreeDListView.this.onNativeScroll = false;
                    lockPosition(ThreeDListView.this.childToSelect);
                }
                          
                if(scrollState == OnScrollListener.SCROLL_STATE_TOUCH_SCROLL)
                {
                    ThreeDListView.this.onNativeScroll = true;                   
                }
                
                if(ThreeDListView.this.scrollListener != null)
                {
                   ThreeDListView.this.scrollListener.onScrollStateChanged(view, scrollState); 
                }               
            }
        });      
    }
    
    private void validateInitialSlection(final View child,final float halfHeight,final float centerY)
    {        
        if(this.childToSelect == null)
        {           
            this.minDstanceFromCenter = 0 ;
            this.childToSelect = child;
            this.setSelectionFromTop(1,(int)(halfHeight - centerY));
            this.lockSelection(child);
        }
    }
    
    /**
     * You should call superclass method, if overriden
     * @param scrollX
     * @param scrollY
     * @param clampedX
     * @param clampedY
     */
    protected void onOverScrolled (final int scrollX, final int scrollY, final boolean clampedX,final  boolean clampedY)
    {
         lockPosition(this.childToSelect);
    }   
}
