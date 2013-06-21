package com.huijuji.view;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class FabTextView extends TextView
{
  private String font = "HelveticaNeu_normal.ttf";

  public FabTextView(Context paramContext)
  {
    super(paramContext);
    init();
  }

  public FabTextView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    init();
  }

  public FabTextView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    init();
  }

  private void init()
  {
    if (!isInEditMode())
      setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/" + this.font));
  }

  public String getFont()
  {
    return this.font;
  }

  public void setFont(String paramString)
  {
    this.font = paramString;
    init();
  }
}

/* Location:           C:\Documents and Settings\Administrator\桌面\private\反编译\apk2java\dex2java\dex-translator-0.0.9.3\dex-translator-0.0.9.3\classes_dex2jar.jar
 * Qualified Name:     com.fab.views.FabTextView
 * JD-Core Version:    0.6.2
 */