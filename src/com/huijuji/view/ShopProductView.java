package com.huijuji.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ShopProductView extends LinearLayout
{
  private FabRotatedTextView discount;
  private ImageView discountPctRibbon;
  private Drawable drawable;
  private ImageView dummyProductHolder;
  private FabTextView fab;
  private FabTextView fabPrice;
  private FabTextView originalPrice;
  private ImageView prodImage;
  private FabTextView prodName;
  private FabTextView prodStatus;
  private FabTextView productBy;
  private RelativeLayout productContainer;

  public ShopProductView(Context paramContext)
  {
    super(paramContext);
    init();
  }

  public ShopProductView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    init();
  }

  private void init()
  {
    if (!isInEditMode())
    {
      View localView = inflate(getContext(), 2130903093, null);
      this.drawable = getContext().getResources().getDrawable(2130837695);
      this.productContainer = ((RelativeLayout)localView.findViewById(2131361844));
      this.prodImage = ((ImageView)localView.findViewById(2131361845));
      this.discountPctRibbon = ((ImageView)localView.findViewById(2131361999));
      this.discount = ((FabRotatedTextView)localView.findViewById(2131361992));
      this.discount.setFont("HelveticaNeu_bold.ttf");
      this.prodStatus = ((FabTextView)localView.findViewById(2131362000));
      this.prodName = ((FabTextView)localView.findViewById(2131362001));
      this.productBy = ((FabTextView)localView.findViewById(2131362056));
      this.fabPrice = ((FabTextView)localView.findViewById(2131361973));
      this.fab = ((FabTextView)localView.findViewById(2131362004));
      this.originalPrice = ((FabTextView)localView.findViewById(2131361976));
      this.dummyProductHolder = ((ImageView)localView.findViewById(2131361853));
      this.prodName.setFont("HelveticaNeu_normal.ttf");
      this.productBy.setFont("HelveticaNeu_normal.ttf");
      this.fabPrice.setFont("HelveticaNeu_bold.ttf");
      this.fab.setFont("HelveticaNeu_bold.ttf");
      this.originalPrice.setFont("HelveticaNeu_normal.ttf");
      this.prodStatus.setFont("HelveticaNeu_bold.ttf");
      addView(localView, new LinearLayout.LayoutParams(-2, -2));
    }
  }

  public ImageView getDiscountPctRibbon()
  {
    return this.discountPctRibbon;
  }

  public Drawable getDrawable()
  {
    return this.drawable;
  }

  public ImageView getDummyProductHolder()
  {
    return this.dummyProductHolder;
  }

  public FabTextView getOriginalPrice()
  {
    return this.originalPrice;
  }

  public ImageView getProdImage()
  {
    return this.prodImage;
  }

  public TextView getProdStatus()
  {
    return this.prodStatus;
  }

  public RelativeLayout getProductContainer()
  {
    return this.productContainer;
  }

  public void setDiscount(String paramString)
  {
    if (TextUtils.isEmpty(paramString))
      this.discount.setVisibility(8);
    while (true)
    {
      return;
      this.discount.setText(paramString);
      this.discount.setVisibility(0);
    }
  }

  public void setFab(String paramString)
  {
    this.fab.setText(paramString);
    this.fab.setVisibility(0);
  }

  public void setFabPrice(String paramString)
  {
    this.fabPrice.setText(paramString);
    this.fabPrice.setVisibility(0);
  }

  public void setOriginalPrice(String paramString)
  {
    this.originalPrice.setText(paramString + " retail price");
    this.originalPrice.setVisibility(0);
  }

  public void setProdBy(String paramString)
  {
    this.productBy.setText(paramString);
    this.productBy.setVisibility(0);
  }

  public void setProdImage(Bitmap paramBitmap)
  {
    this.prodImage.setImageBitmap(paramBitmap);
  }

  public void setProdImage(Drawable paramDrawable)
  {
    this.prodImage.setImageDrawable(paramDrawable);
  }

  public void setProdName(String paramString)
  {
    this.prodName.setText(paramString);
    this.prodName.setVisibility(0);
  }

  public void setProdStatus(String paramString)
  {
    this.prodStatus.setText(paramString);
    this.prodStatus.setVisibility(0);
  }
}

/* Location:           C:\Documents and Settings\Administrator\桌面\private\反编译\apk2java\dex2java\dex-translator-0.0.9.3\dex-translator-0.0.9.3\classes_dex2jar.jar
 * Qualified Name:     com.fab.views.ShopProductView
 * JD-Core Version:    0.6.2
 */