package android.support.v4.view;

import android.view.MotionEvent;

class MotionEventCompatEclair
{
  public static int findPointerIndex(MotionEvent paramMotionEvent, int paramInt)
  {
    return paramMotionEvent.findPointerIndex(paramInt);
  }

  public static int getPointerId(MotionEvent paramMotionEvent, int paramInt)
  {
    return paramMotionEvent.getPointerId(paramInt);
  }

  public static float getX(MotionEvent paramMotionEvent, int paramInt)
  {
    return paramMotionEvent.getX(paramInt);
  }

  public static float getY(MotionEvent paramMotionEvent, int paramInt)
  {
    return paramMotionEvent.getY(paramInt);
  }
}

/* Location:           C:\Documents and Settings\Administrator\桌面\private\反编译\apk2java\dex2java\dex-translator-0.0.9.3\dex-translator-0.0.9.3\classes_dex2jar.jar
 * Qualified Name:     android.support.v4.view.MotionEventCompatEclair
 * JD-Core Version:    0.6.2
 */