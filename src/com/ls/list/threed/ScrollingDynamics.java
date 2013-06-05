package com.ls.list.threed;

public class ScrollingDynamics
{
    public final static String TAG = ScrollingDynamics.class.getName();
    
    private final static int NUMBER_OF_STEAPS = 10;
    
    private int currentPosition;
    private int endPosition;
    private int viewToCenterPosition;
    
    private int delta;
    private ThreeDListView parent;
    
    public ScrollingDynamics(final ThreeDListView theParent)
    {
        this.parent = theParent;
    }
    
    public void reSetParameters(final int theCurrentPosition,final int theEndPosiiton,final int theViewToCenterPosition)
    {
        this.currentPosition = theCurrentPosition;
        this.endPosition = theEndPosiiton;
        this.viewToCenterPosition = theViewToCenterPosition; 
        this.delta = (endPosition - currentPosition) / NUMBER_OF_STEAPS;
        if(this.delta == 0)
        {
            if(endPosition > currentPosition)
                this.delta = 1;
            else
                this.delta = -1;
        }     
    }
    
    public boolean update()
    {
        this.currentPosition += delta;        
        if(Math.abs(this.currentPosition - this.endPosition)< Math.abs((float)(delta * 1.1)))
        {
            this.parent.setSelectionFromTop(viewToCenterPosition, endPosition);
            return false;
        }else {
            this.parent.setSelectionFromTop(viewToCenterPosition, currentPosition);
            return true;
        }
    }
}
