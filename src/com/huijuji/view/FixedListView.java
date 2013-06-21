package com.huijuji.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.ListView;

public class FixedListView extends ListView
{
  Context context;

  public FixedListView(Context paramContext)
  {
    super(paramContext);
    this.context = paramContext;
  }

  public FixedListView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    this.context = paramContext;
  }

  protected void dispatchDraw(Canvas paramCanvas)
  {
    try
    {
      super.dispatchDraw(paramCanvas);
      label5: return;
    }
    catch (IndexOutOfBoundsException localIndexOutOfBoundsException)
    {
      break label5;
    }
  }
}

/* Location:           C:\Documents and Settings\Administrator\桌面\private\反编译\apk2java\dex2java\dex-translator-0.0.9.3\dex-translator-0.0.9.3\classes_dex2jar.jar
 * Qualified Name:     com.fab.views.FixedListView
 * JD-Core Version:    0.6.2
 */