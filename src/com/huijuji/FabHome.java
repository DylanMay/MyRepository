package com.fab;

import android.app.AlertDialog.Builder;
import android.app.NotificationManager;
import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
import android.widget.TabWidget;
import com.fab.sale.EndingSoonSalesActivity;
import com.fab.sale.FeaturedActivity;
import com.fab.sale.SalesActivity;
import com.fab.sale.UpcomingSalesActivity;
import com.fab.utils.FabSharedPrefs;
import com.fab.views.FabTextView;
import com.fab.views.TitleWidget;

public class FabHome extends TabActivity
  implements TabHost.OnTabChangeListener
{
  private static final String TAG = "Fab";
  public static Context context;
  public static TabHost mTabHost;
  public static ProgressBar progressBar;
  public static String tabLable;
  public static TitleWidget titleWidget;
  private Bundle extras;
  private int tabId;

  private static View createTabView(Context paramContext, String paramString1, String paramString2)
  {
    View localView = LayoutInflater.from(paramContext).inflate(2130903098, null);
    FabTextView localFabTextView = (FabTextView)localView.findViewById(2131362069);
    localFabTextView.setFont("HelveticaNeu_bold.ttf");
    localFabTextView.setText(paramString1);
    localFabTextView.setCompoundDrawablesWithIntrinsicBounds(0, paramContext.getResources().getIdentifier(paramString2, "drawable", "com.fab"), 0, 0);
    return localView;
  }

  private void setupTab(Intent paramIntent, String paramString1, String paramString2)
  {
    paramIntent.putExtra("TAB_BUILDING", true);
    mTabHost.addTab(mTabHost.newTabSpec(paramString1).setIndicator(createTabView(mTabHost.getContext(), paramString1, paramString2)).setContent(paramIntent));
  }

  public void onConfigurationChanged(Configuration paramConfiguration)
  {
    super.onConfigurationChanged(paramConfiguration);
  }

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130903054);
    context = this;
    if (TextUtils.isEmpty(FabSharedPrefs.getDeviceDensity()))
    {
      DisplayMetrics localDisplayMetrics = new DisplayMetrics();
      getWindowManager().getDefaultDisplay().getMetrics(localDisplayMetrics);
      FabSharedPrefs.setDeviceDensity(localDisplayMetrics.densityDpi);
    }
    ((FabApplication)getApplication()).setRefreshActivityOnRestart(false);
    tabLable = "";
    NotificationManager localNotificationManager = (NotificationManager)getSystemService("notification");
    localNotificationManager.cancel(2131165198);
    localNotificationManager.cancel(2131165199);
    this.extras = getIntent().getExtras();
    String str1 = null;
    String str2 = null;
    if (this.extras != null)
    {
      this.tabId = this.extras.getInt("TAB_ID");
      str1 = this.extras.getString("SUCCESS_MSG");
      str2 = this.extras.getString("SUCCESS_MSG_TITLE");
    }
    titleWidget = (TitleWidget)findViewById(2131361810);
    Intent localIntent1 = new Intent(this, SalesActivity.class);
    Intent localIntent2 = new Intent(this, EndingSoonSalesActivity.class);
    Intent localIntent3 = new Intent(this, UpcomingSalesActivity.class);
    Intent localIntent4 = new Intent(this, FeaturedActivity.class);
    mTabHost = getTabHost();
    mTabHost.getTabWidget().setDividerDrawable(2130837757);
    setupTab(localIntent1, "New Sales", "tab_newsales");
    setupTab(localIntent4, "Featured", "tab_featured");
    setupTab(localIntent2, "Ending Soon", "tab_endingsoon");
    setupTab(localIntent3, "Calendar", "tab_calendar");
    mTabHost.setOnTabChangedListener(this);
    AlertDialog.Builder localBuilder;
    if ((str1 != null) && (!TextUtils.isEmpty(str1)))
    {
      localBuilder = new AlertDialog.Builder(context);
      localBuilder.setIcon(2130837507);
      if ((str2 == null) || (TextUtils.isEmpty(str2)))
        break label372;
    }
    while (true)
    {
      localBuilder.setTitle(str2);
      localBuilder.setMessage(str1);
      localBuilder.setPositiveButton("Ok", null);
      localBuilder.show();
      mTabHost.setCurrentTab(this.tabId);
      return;
      label372: str2 = "Success!";
    }
  }

  public void onTabChanged(String paramString)
  {
    tabLable = paramString;
  }
}

/* Location:           C:\Documents and Settings\Administrator\桌面\private\反编译\apk2java\dex2java\dex-translator-0.0.9.3\dex-translator-0.0.9.3\classes_dex2jar.jar
 * Qualified Name:     com.fab.FabHome
 * JD-Core Version:    0.6.2
 */