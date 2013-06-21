package com.fab.webviews;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import com.fab.MoreWebViewActivity;

public class FabWebChromeClient extends WebChromeClient
{
  public boolean onJsAlert(WebView paramWebView, String paramString1, String paramString2, final JsResult paramJsResult)
  {
    if ((FabWebViewActivity.isActivityRunning) || (MoreWebViewActivity.isActivityRunning))
      new AlertDialog.Builder(paramWebView.getContext()).setTitle("Alert").setMessage(paramString2).setOnCancelListener(new DialogInterface.OnCancelListener()
      {
        public void onCancel(DialogInterface paramAnonymousDialogInterface)
        {
          paramJsResult.cancel();
        }
      }).setPositiveButton(17039370, new DialogInterface.OnClickListener()
      {
        public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
        {
          paramJsResult.confirm();
        }
      }).setCancelable(false).create().show();
    return true;
  }

  public boolean onJsConfirm(WebView paramWebView, String paramString1, String paramString2, final JsResult paramJsResult)
  {
    if ((FabWebViewActivity.isActivityRunning) || (MoreWebViewActivity.isActivityRunning))
      new AlertDialog.Builder(paramWebView.getContext()).setTitle("Confirm").setMessage(paramString2).setOnCancelListener(new DialogInterface.OnCancelListener()
      {
        public void onCancel(DialogInterface paramAnonymousDialogInterface)
        {
          paramJsResult.cancel();
        }
      }).setPositiveButton(17039370, new DialogInterface.OnClickListener()
      {
        public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
        {
          paramJsResult.confirm();
        }
      }).setNegativeButton(17039360, new DialogInterface.OnClickListener()
      {
        public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
        {
          paramJsResult.cancel();
        }
      }).create().show();
    return true;
  }
}

/* Location:           C:\Documents and Settings\Administrator\桌面\private\反编译\apk2java\dex2java\dex-translator-0.0.9.3\dex-translator-0.0.9.3\classes_dex2jar.jar
 * Qualified Name:     com.fab.webviews.FabWebChromeClient
 * JD-Core Version:    0.6.2
 */