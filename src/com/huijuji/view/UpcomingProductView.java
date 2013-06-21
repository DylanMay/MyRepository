package com.huijuji.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

public class UpcomingProductView extends LinearLayout
{
  private ImageView prodImage;

  public UpcomingProductView(Context paramContext)
  {
    super(paramContext);
    init();
  }

  public UpcomingProductView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    init();
  }

  private void init()
  {
    View localView = inflate(getContext(), 2130903102, null);
    this.prodImage = ((ImageView)localView.findViewById(2131361845));
    addView(localView, new LinearLayout.LayoutParams(-2, -2));
  }

  public ImageView getProdImage()
  {
    return this.prodImage;
  }

  public void setProdImage(Bitmap paramBitmap)
  {
    this.prodImage.setImageBitmap(paramBitmap);
  }

  public void setProdImage(Drawable paramDrawable)
  {
    this.prodImage.setImageDrawable(paramDrawable);
  }
}

/* Location:           C:\Documents and Settings\Administrator\桌面\private\反编译\apk2java\dex2java\dex-translator-0.0.9.3\dex-translator-0.0.9.3\classes_dex2jar.jar
 * Qualified Name:     com.fab.views.UpcomingProductView
 * JD-Core Version:    0.6.2
 */