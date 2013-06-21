package com.fab.webviews;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.fab.MoreWebViewActivity;
import com.fab.utils.FabUtils;

public class FabWebViewClient extends WebViewClient
{
  public static ProgressDialog dialog;
  public static boolean hasPageLoaded;
  private String TAG = "FAB_WEB_VIEW_CLIENT";
  private Activity activity;
  Context context;
  private boolean hasHistory;
  final String offlineMessageHtml = "Please connect to the internet.";
  private boolean showProgressBar;
  final String timeoutMessageHtml = "Network request timed out";

  public FabWebViewClient(Activity paramActivity, Context paramContext, boolean paramBoolean1, boolean paramBoolean2)
  {
    this.activity = paramActivity;
    this.context = paramContext;
    this.hasHistory = paramBoolean1;
    this.showProgressBar = paramBoolean2;
    dialog = new ProgressDialog(paramContext);
    hasPageLoaded = false;
  }

  public void onPageFinished(WebView paramWebView, String paramString)
  {
    super.onPageFinished(paramWebView, paramString);
    hasPageLoaded = true;
    paramWebView.setVisibility(0);
    if (((FabWebViewActivity.isActivityRunning) || (MoreWebViewActivity.isActivityRunning)) && (dialog != null) && (dialog.isShowing()))
      dialog.cancel();
    if (!this.hasHistory)
      paramWebView.clearHistory();
    if (FabWebViewActivity.mHandler != null)
      FabWebViewActivity.mHandler.sendEmptyMessage(9);
  }

  public void onPageStarted(WebView paramWebView, String paramString, Bitmap paramBitmap)
  {
    FabUtils.log(this.TAG, paramString);
    super.onPageStarted(paramWebView, paramString, paramBitmap);
    if ((this.activity != null) && (this.showProgressBar) && ((FabWebViewActivity.isActivityRunning) || (MoreWebViewActivity.isActivityRunning)))
    {
      dialog.setCancelable(true);
      dialog.setMessage("Loading...");
      dialog.show();
    }
  }

  public boolean shouldOverrideUrlLoading(WebView paramWebView, String paramString)
  {
    if (paramString.startsWith("tel:"))
    {
      Intent localIntent = new Intent("android.intent.action.DIAL", Uri.parse(paramString));
      this.context.startActivity(localIntent);
    }
    while (true)
    {
      return true;
      if ((paramString.startsWith("http:")) || (paramString.startsWith("https:")))
        paramWebView.loadUrl(paramString);
    }
  }
}

/* Location:           C:\Documents and Settings\Administrator\桌面\private\反编译\apk2java\dex2java\dex-translator-0.0.9.3\dex-translator-0.0.9.3\classes_dex2jar.jar
 * Qualified Name:     com.fab.webviews.FabWebViewClient
 * JD-Core Version:    0.6.2
 */