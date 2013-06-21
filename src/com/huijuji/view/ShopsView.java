package com.huijuji.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import com.fab.R.styleable;

public class ShopsView extends LinearLayout
{
  private AttributeSet attrs;
  private ImageView clock;
  private FabTextView day;
  private ImageView dummyProductHolder;
  private int layoutId;
  private RelativeLayout overlay;
  private FabTextView primaryTitle;
  private ImageView prodImage;
  private RelativeLayout productContainer;
  private FabTextView ribbonText;

  public ShopsView(Context paramContext)
  {
    super(paramContext);
    init();
  }

  public ShopsView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    this.attrs = paramAttributeSet;
    init();
  }

  private void init()
  {
    if (!isInEditMode())
    {
      if (this.attrs != null)
        this.layoutId = getContext().obtainStyledAttributes(this.attrs, R.styleable.ShopsView).getResourceId(0, 0);
      if (this.layoutId == 0)
        break label203;
    }
    label203: for (View localView = inflate(getContext(), this.layoutId, null); ; localView = inflate(getContext(), 2130903097, null))
    {
      this.productContainer = ((RelativeLayout)localView.findViewById(2131361844));
      this.overlay = ((RelativeLayout)localView.findViewById(2131362101));
      this.prodImage = ((ImageView)localView.findViewById(2131361845));
      this.primaryTitle = ((FabTextView)localView.findViewById(2131361852));
      this.dummyProductHolder = ((ImageView)localView.findViewById(2131361853));
      this.ribbonText = ((FabTextView)localView.findViewById(2131361854));
      if (this.ribbonText != null)
        this.ribbonText.setFont("HelveticaNeu_bold.ttf");
      this.day = ((FabTextView)localView.findViewById(2131362066));
      this.day.setFont("HelveticaNeu_normal.ttf");
      this.clock = ((ImageView)localView.findViewById(2131362067));
      addView(localView, new LinearLayout.LayoutParams(-2, -2));
      return;
    }
  }

  public ImageView getClock()
  {
    return this.clock;
  }

  public FabTextView getDay()
  {
    return this.day;
  }

  public ImageView getDummyProductHolder()
  {
    return this.dummyProductHolder;
  }

  public FabTextView getPrimaryTitle()
  {
    return this.primaryTitle;
  }

  public ImageView getProdImage()
  {
    return this.prodImage;
  }

  public RelativeLayout getProductContainer()
  {
    return this.productContainer;
  }

  public FabTextView getRibbonText()
  {
    return this.ribbonText;
  }

  public void removeOverlay()
  {
    this.overlay.setVisibility(8);
  }

  public void setOverlay()
  {
    this.overlay.setVisibility(0);
  }

  public void setProdImage(Bitmap paramBitmap)
  {
    this.prodImage.setImageBitmap(paramBitmap);
  }

  public void setProdImage(Drawable paramDrawable)
  {
    this.prodImage.setImageDrawable(paramDrawable);
  }

  public void setRibbonText(FabTextView paramFabTextView)
  {
    this.ribbonText = paramFabTextView;
  }
}

/* Location:           C:\Documents and Settings\Administrator\桌面\private\反编译\apk2java\dex2java\dex-translator-0.0.9.3\dex-translator-0.0.9.3\classes_dex2jar.jar
 * Qualified Name:     com.fab.views.ShopsView
 * JD-Core Version:    0.6.2
 */