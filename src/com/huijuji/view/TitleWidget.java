package com.huijuji.view;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import com.fab.FabHome;
import com.fab.webviews.FabWebViewActivity;

public class TitleWidget extends LinearLayout
{
  protected String TAG = "TITLE_WIDGET";
  private FabButton browseButton;
  private FabButton cartButton;
  private Context context;
  protected FabShopsDialog fabDialog;
  private ImageView fabLogo;
  private RelativeLayout headerHolder;
  private FabTextView pageTitle;
  private FabButton registerButton;

  public TitleWidget(Context paramContext)
  {
    super(paramContext);
    this.context = paramContext;
    init();
  }

  public TitleWidget(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    this.context = paramContext;
    init();
  }

  private void init()
  {
    if (!isInEditMode())
    {
      View localView = inflate(getContext(), 2130903048, null);
      this.fabLogo = ((ImageView)localView.findViewById(2131361841));
      this.fabLogo.setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramAnonymousView)
        {
          Intent localIntent = new Intent(TitleWidget.this.context, FabHome.class);
          localIntent.setFlags(67108864);
          TitleWidget.this.context.startActivity(localIntent);
        }
      });
      this.headerHolder = ((RelativeLayout)localView.findViewById(2131361839));
      BitmapDrawable localBitmapDrawable = new BitmapDrawable(BitmapFactory.decodeResource(getResources(), 2130837596));
      localBitmapDrawable.setTileModeX(Shader.TileMode.REPEAT);
      this.headerHolder.setBackgroundDrawable(localBitmapDrawable);
      this.cartButton = ((FabButton)localView.findViewById(2131361840));
      this.cartButton.setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramAnonymousView)
        {
          Intent localIntent = new Intent(TitleWidget.this.context, FabWebViewActivity.class);
          TitleWidget.this.context.startActivity(localIntent);
        }
      });
      this.browseButton = ((FabButton)localView.findViewById(2131361842));
      this.browseButton.setFont("HelveticaNeu_bold.ttf");
      this.browseButton.setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramAnonymousView)
        {
          TitleWidget.this.fabDialog = new FabShopsDialog(TitleWidget.this.context, TitleWidget.this.browseButton);
          TitleWidget.this.fabDialog.setBrowseShops();
        }
      });
      this.pageTitle = ((FabTextView)localView.findViewById(2131361838));
      this.registerButton = ((FabButton)localView.findViewById(2131361843));
      this.registerButton.setFont("HelveticaNeu_bold.ttf");
      addView(localView, new LinearLayout.LayoutParams(-1, -2));
    }
  }

  public void clearCartButton()
  {
    this.cartButton.setBackgroundResource(this.context.getResources().getIdentifier("cart_button_sel", "drawable", "com.fab"));
    this.cartButton.setText("");
  }

  public Button getBrowseButton()
  {
    return this.browseButton;
  }

  protected void getBrowseCategories()
  {
    this.fabDialog.show();
    this.browseButton.setEnabled(false);
    this.fabDialog.setBrowseShops();
  }

  public FabButton getCartButton()
  {
    return this.cartButton;
  }

  public ImageView getFabLogo()
  {
    return this.fabLogo;
  }

  public FabTextView getPageTitle()
  {
    return this.pageTitle;
  }

  public FabButton getRegisterButton()
  {
    return this.registerButton;
  }

  public void setBrowseButton(FabButton paramFabButton)
  {
    this.browseButton = paramFabButton;
  }

  public void setCartButton(FabButton paramFabButton)
  {
    this.cartButton = paramFabButton;
  }

  public void setFabLogo(ImageView paramImageView)
  {
    this.fabLogo = paramImageView;
  }

  public void setPageTitle(FabTextView paramFabTextView)
  {
    this.pageTitle = paramFabTextView;
  }
}

/* Location:           C:\Documents and Settings\Administrator\桌面\private\反编译\apk2java\dex2java\dex-translator-0.0.9.3\dex-translator-0.0.9.3\classes_dex2jar.jar
 * Qualified Name:     com.fab.views.TitleWidget
 * JD-Core Version:    0.6.2
 */