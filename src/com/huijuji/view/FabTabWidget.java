package com.huijuji.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import com.fab.FabHome;

public class FabTabWidget extends LinearLayout
  implements View.OnClickListener
{
  private static final int ENDING = 2;
  private static final int FEATURED = 1;
  private static final int NEW = 0;
  private static final int UPCOMING = 3;
  private FabTextView btnFeatured;
  private Context context;
  private FabTextView endingBtn;
  private FabTextView newBtn;
  private FabTextView upcomingBtn;

  public FabTabWidget(Context paramContext)
  {
    super(paramContext);
    this.context = paramContext;
    init();
  }

  public FabTabWidget(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    this.context = paramContext;
    init();
  }

  private void init()
  {
    if (!isInEditMode())
    {
      View localView = inflate(getContext(), 2130903099, null);
      this.btnFeatured = ((FabTextView)localView.findViewById(2131362071));
      this.btnFeatured.setFont("HelveticaNeu_bold.ttf");
      this.btnFeatured.setId(1);
      this.btnFeatured.setOnClickListener(this);
      this.newBtn = ((FabTextView)localView.findViewById(2131362070));
      this.newBtn.setFont("HelveticaNeu_bold.ttf");
      this.newBtn.setId(0);
      this.newBtn.setOnClickListener(this);
      this.endingBtn = ((FabTextView)localView.findViewById(2131362072));
      this.endingBtn.setFont("HelveticaNeu_bold.ttf");
      this.endingBtn.setId(2);
      this.endingBtn.setOnClickListener(this);
      this.upcomingBtn = ((FabTextView)localView.findViewById(2131362073));
      this.upcomingBtn.setFont("HelveticaNeu_bold.ttf");
      this.upcomingBtn.setId(3);
      this.upcomingBtn.setOnClickListener(this);
      addView(localView, new LinearLayout.LayoutParams(-1, -2));
    }
  }

  public void onClick(View paramView)
  {
    if ((paramView instanceof FabTextView))
      if (paramView.getId() == -1)
        break label69;
    label69: for (int i = paramView.getId(); ; i = 0)
    {
      Intent localIntent = new Intent(this.context, FabHome.class);
      localIntent.putExtra("TAB_ID", i);
      localIntent.setFlags(67108864);
      this.context.startActivity(localIntent);
      ((Activity)this.context).finish();
      return;
    }
  }
}

/* Location:           C:\Documents and Settings\Administrator\桌面\private\反编译\apk2java\dex2java\dex-translator-0.0.9.3\dex-translator-0.0.9.3\classes_dex2jar.jar
 * Qualified Name:     com.fab.views.FabTabWidget
 * JD-Core Version:    0.6.2
 */