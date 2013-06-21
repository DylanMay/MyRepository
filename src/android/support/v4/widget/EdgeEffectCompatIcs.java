package android.support.v4.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.widget.EdgeEffect;

class EdgeEffectCompatIcs
{
  public static boolean draw(Object paramObject, Canvas paramCanvas)
  {
    return ((EdgeEffect)paramObject).draw(paramCanvas);
  }

  public static void finish(Object paramObject)
  {
    ((EdgeEffect)paramObject).finish();
  }

  public static boolean isFinished(Object paramObject)
  {
    return ((EdgeEffect)paramObject).isFinished();
  }

  public static Object newEdgeEffect(Context paramContext)
  {
    return new EdgeEffect(paramContext);
  }

  public static boolean onAbsorb(Object paramObject, int paramInt)
  {
    ((EdgeEffect)paramObject).onAbsorb(paramInt);
    return true;
  }

  public static boolean onPull(Object paramObject, float paramFloat)
  {
    ((EdgeEffect)paramObject).onPull(paramFloat);
    return true;
  }

  public static boolean onRelease(Object paramObject)
  {
    EdgeEffect localEdgeEffect = (EdgeEffect)paramObject;
    localEdgeEffect.onRelease();
    return localEdgeEffect.isFinished();
  }

  public static void setSize(Object paramObject, int paramInt1, int paramInt2)
  {
    ((EdgeEffect)paramObject).setSize(paramInt1, paramInt2);
  }
}

/* Location:           C:\Documents and Settings\Administrator\桌面\private\反编译\apk2java\dex2java\dex-translator-0.0.9.3\dex-translator-0.0.9.3\classes_dex2jar.jar
 * Qualified Name:     android.support.v4.widget.EdgeEffectCompatIcs
 * JD-Core Version:    0.6.2
 */