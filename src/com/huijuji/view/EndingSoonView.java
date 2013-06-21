package com.huijuji.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.SlidingDrawer;

public class EndingSoonView extends LinearLayout
{
  private Context context;
  private FabSlidingDrawer drawer;
  private Button drawerHandle;
  private ImageView dummyProductHolder;
  private ViewGroup mHandleLayout;
  private final Rect mHitRect = new Rect();
  private FabTextView primaryTitle;
  private ImageView prodImage;
  private RelativeLayout productContainer;
  private FabTextView ribbonTextView;
  private RelativeLayout slidingDrawerHolder;
  private FabTextView tvDuration;

  public EndingSoonView(Context paramContext)
  {
    super(paramContext);
    this.context = paramContext;
    init();
  }

  public EndingSoonView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    this.context = paramContext;
    init();
  }

  private void init()
  {
    if (!isInEditMode())
    {
      View localView = inflate(getContext(), 2130903049, null);
      this.productContainer = ((RelativeLayout)localView.findViewById(2131361844));
      this.prodImage = ((ImageView)localView.findViewById(2131361845));
      this.primaryTitle = ((FabTextView)localView.findViewById(2131361852));
      this.drawer = ((FabSlidingDrawer)localView.findViewById(2131361847));
      this.drawerHandle = ((Button)this.drawer.getHandle());
      this.tvDuration = ((FabTextView)this.drawer.getContent());
      this.tvDuration.setFont("georgiai.ttf");
      this.tvDuration.setSingleLine(true);
      this.slidingDrawerHolder = ((RelativeLayout)localView.findViewById(2131361846));
      this.ribbonTextView = ((FabTextView)localView.findViewById(2131361854));
      this.dummyProductHolder = ((ImageView)localView.findViewById(2131361853));
      addView(localView, new LinearLayout.LayoutParams(-2, -2));
    }
  }

  public SlidingDrawer getDrawer()
  {
    return this.drawer;
  }

  public Button getDrawerHandle()
  {
    return this.drawerHandle;
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

  public FabTextView getRibbonTextView()
  {
    return this.ribbonTextView;
  }

  public FabTextView getTvDuration()
  {
    return this.tvDuration;
  }

  public void setDrawerHandle(Button paramButton)
  {
    this.drawerHandle = paramButton;
  }

  public void setProdImage(Bitmap paramBitmap)
  {
    this.prodImage.setImageBitmap(paramBitmap);
  }

  public void setProdImage(Drawable paramDrawable)
  {
    this.prodImage.setImageDrawable(paramDrawable);
  }

  public void setSlidingDrawerVisibility(int paramInt)
  {
    this.slidingDrawerHolder.setVisibility(paramInt);
  }
}

/* Location:           C:\Documents and Settings\Administrator\桌面\private\反编译\apk2java\dex2java\dex-translator-0.0.9.3\dex-translator-0.0.9.3\classes_dex2jar.jar
 * Qualified Name:     com.fab.views.EndingSoonView
 * JD-Core Version:    0.6.2
 */