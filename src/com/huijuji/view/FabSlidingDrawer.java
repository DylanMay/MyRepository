package com.huijuji.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.SlidingDrawer;

public class FabSlidingDrawer extends SlidingDrawer
{
  public FabSlidingDrawer(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }

  public FabSlidingDrawer(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }

  public boolean onInterceptTouchEvent(MotionEvent paramMotionEvent)
  {
    return false;
  }
}

/* Location:           C:\Documents and Settings\Administrator\桌面\private\反编译\apk2java\dex2java\dex-translator-0.0.9.3\dex-translator-0.0.9.3\classes_dex2jar.jar
 * Qualified Name:     com.fab.views.FabSlidingDrawer
 * JD-Core Version:    0.6.2
 */