package com.huijuji;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build.VERSION;
import android.webkit.CookieSyncManager;
import com.fab.utils.FabSharedPrefs;
import com.fab.utils.FabUtils;
import oauth.signpost.OAuthProvider;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import org.acra.ACRA;
import org.acra.ErrorReporter;
import org.acra.ReportingInteractionMode;
import org.acra.annotation.ReportsCrashes;
import org.json.JSONException;
import org.json.JSONObject;
import twitter4j.Twitter;

@ReportsCrashes(customReportContent={org.acra.ReportField.REPORT_ID, org.acra.ReportField.APP_VERSION_CODE, org.acra.ReportField.APP_VERSION_NAME, org.acra.ReportField.PHONE_MODEL, org.acra.ReportField.BRAND, org.acra.ReportField.ANDROID_VERSION, org.acra.ReportField.BUILD, org.acra.ReportField.CUSTOM_DATA, org.acra.ReportField.STACK_TRACE, org.acra.ReportField.USER_EMAIL, org.acra.ReportField.USER_APP_START_DATE, org.acra.ReportField.USER_CRASH_DATE, org.acra.ReportField.DEVICE_ID, org.acra.ReportField.SHARED_PREFERENCES}, formKey="dE8zcFNza2hTR0tnSEtyeEVRYk01SkE6MQ", formUri="http://fab.com/mobile/crash-report/?app_version=3.0", mode=ReportingInteractionMode.SILENT)
public class FabApplication extends Application
{
  public static int API_LEVEL = 1;
  public static String APP_VERSION = "";
  private CommonsHttpOAuthConsumer consumer;
  private Context context;
  private OAuthProvider provider;
  private boolean refreshActivityOnRestart;
  private Twitter twitter;

  public CommonsHttpOAuthConsumer getConsumer()
  {
    return this.consumer;
  }

  public OAuthProvider getProvider()
  {
    return this.provider;
  }

  public Twitter getTwitter()
  {
    return this.twitter;
  }

  public boolean isRefreshActivityOnRestart()
  {
    return this.refreshActivityOnRestart;
  }

  public void onCreate()
  {
    super.onCreate();
    this.context = this;
    CookieSyncManager.createInstance(this.context);
    ACRA.init(this);
    ErrorReporter localErrorReporter = ErrorReporter.getInstance();
    try
    {
      localErrorReporter.putCustomData("UserId", FabUtils.getUserInfo(this.context).getInt("id"));
      localErrorReporter.putCustomData("UserName", FabUtils.getUserInfo(this.context).getString("username"));
      localErrorReporter.putCustomData("UserEmail", FabUtils.getUserInfo(this.context).getString("email"));
      try
      {
        label95: APP_VERSION = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
        label113: API_LEVEL = Build.VERSION.SDK_INT;
        FabSharedPrefs.initializePreferences(this.context);
        FabSharedPrefs.deleteCookies();
        return;
      }
      catch (Exception localException1)
      {
        break label113;
      }
      catch (PackageManager.NameNotFoundException localNameNotFoundException)
      {
        break label113;
      }
    }
    catch (Exception localException2)
    {
      break label95;
    }
    catch (JSONException localJSONException)
    {
      break label95;
    }
  }

  public void setConsumer(CommonsHttpOAuthConsumer paramCommonsHttpOAuthConsumer)
  {
    this.consumer = paramCommonsHttpOAuthConsumer;
  }

  public void setProvider(OAuthProvider paramOAuthProvider)
  {
    this.provider = paramOAuthProvider;
  }

  public void setRefreshActivityOnRestart(boolean paramBoolean)
  {
    this.refreshActivityOnRestart = paramBoolean;
  }

  public void setTwitter(Twitter paramTwitter)
  {
    this.twitter = paramTwitter;
  }
}

/* Location:           C:\Documents and Settings\Administrator\桌面\private\反编译\apk2java\dex2java\dex-translator-0.0.9.3\dex-translator-0.0.9.3\classes_dex2jar.jar
 * Qualified Name:     com.fab.FabApplication
 * JD-Core Version:    0.6.2
 */