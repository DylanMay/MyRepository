package com.huijuji.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;

public class FabListViewWithViewPager extends ListView
{
  private float lastX;
  private float lastY;
  private float xDistance;
  private float yDistance;

  public FabListViewWithViewPager(Context paramContext)
  {
    super(paramContext);
  }

  public FabListViewWithViewPager(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }

  public FabListViewWithViewPager(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }

  public boolean onInterceptTouchEvent(MotionEvent paramMotionEvent)
  {
    switch (paramMotionEvent.getAction())
    {
    case 1:
    default:
    case 0:
    case 2:
    }
    for (boolean bool = super.onInterceptTouchEvent(paramMotionEvent); ; bool = false)
    {
      return bool;
      this.yDistance = 0.0F;
      this.xDistance = 0.0F;
      this.lastX = paramMotionEvent.getX();
      this.lastY = paramMotionEvent.getY();
      break;
      float f1 = paramMotionEvent.getX();
      float f2 = paramMotionEvent.getY();
      this.xDistance += Math.abs(f1 - this.lastX);
      this.yDistance += Math.abs(f2 - this.lastY);
      this.lastX = f1;
      this.lastY = f2;
      if (this.xDistance <= this.yDistance)
        break;
    }
  }
}

/* Location:           C:\Documents and Settings\Administrator\桌面\private\反编译\apk2java\dex2java\dex-translator-0.0.9.3\dex-translator-0.0.9.3\classes_dex2jar.jar
 * Qualified Name:     com.fab.views.FabListViewWithViewPager
 * JD-Core Version:    0.6.2
 */