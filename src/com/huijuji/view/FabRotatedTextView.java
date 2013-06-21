package com.huijuji.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;

public class FabRotatedTextView extends FabTextView
{
  public FabRotatedTextView(Context paramContext)
  {
    super(paramContext);
  }

  public FabRotatedTextView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }

  public FabRotatedTextView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }

  protected void onDraw(Canvas paramCanvas)
  {
    paramCanvas.save();
    float f = getHeight() / 2.0F;
    paramCanvas.rotate(-45.0F, getWidth() / 2.0F, f);
    super.onDraw(paramCanvas);
    paramCanvas.restore();
  }
}

/* Location:           C:\Documents and Settings\Administrator\桌面\private\反编译\apk2java\dex2java\dex-translator-0.0.9.3\dex-translator-0.0.9.3\classes_dex2jar.jar
 * Qualified Name:     com.fab.views.FabRotatedTextView
 * JD-Core Version:    0.6.2
 */