package com.fab;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
import com.fab.utils.ExecuteRequestAsyncTask;
import com.fab.utils.FabSharedPrefs;
import com.google.android.apps.analytics.GoogleAnalyticsTracker;
import java.util.HashMap;

public class FabInit extends Activity
{
  protected String TAG = "FAB_INIT";
  Context context;
  private HashMap<String, String> parameters;
  private ExecuteRequestAsyncTask task;
  GoogleAnalyticsTracker tracker;

  public void onConfigurationChanged(Configuration paramConfiguration)
  {
    super.onConfigurationChanged(paramConfiguration);
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    this.tracker = GoogleAnalyticsTracker.getInstance();
    this.tracker.startNewSession("UA-19838455-1", this);
    setContentView(2130903055);
    this.context = this;
    if (TextUtils.isEmpty(FabSharedPrefs.getDeviceDensity()))
    {
      DisplayMetrics localDisplayMetrics = new DisplayMetrics();
      getWindowManager().getDefaultDisplay().getMetrics(localDisplayMetrics);
      FabSharedPrefs.setDeviceDensity(localDisplayMetrics.densityDpi);
    }
    new Thread()
    {
      // ERROR //
      public void run()
      {
        // Byte code:
        //   0: ldc2_w 23
        //   3: invokestatic 28	com/fab/FabInit$1:sleep	(J)V
        //   6: aload_0
        //   7: getfield 16	com/fab/FabInit$1:this$0	Lcom/fab/FabInit;
        //   10: invokevirtual 31	com/fab/FabInit:finish	()V
        //   13: aload_0
        //   14: getfield 16	com/fab/FabInit$1:this$0	Lcom/fab/FabInit;
        //   17: getfield 35	com/fab/FabInit:tracker	Lcom/google/android/apps/analytics/GoogleAnalyticsTracker;
        //   20: ldc 37
        //   22: invokestatic 43	com/fab/utils/FabUtils:gATracUrl	(Ljava/lang/String;)Ljava/lang/String;
        //   25: invokevirtual 49	com/google/android/apps/analytics/GoogleAnalyticsTracker:trackPageView	(Ljava/lang/String;)V
        //   28: aload_0
        //   29: getfield 16	com/fab/FabInit$1:this$0	Lcom/fab/FabInit;
        //   32: getfield 53	com/fab/FabInit:context	Landroid/content/Context;
        //   35: invokestatic 57	com/fab/utils/FabUtils:isLoggedIn	(Landroid/content/Context;)Z
        //   38: ifeq +219 -> 257
        //   41: new 59	android/content/Intent
        //   44: dup
        //   45: aload_0
        //   46: getfield 16	com/fab/FabInit$1:this$0	Lcom/fab/FabInit;
        //   49: getfield 53	com/fab/FabInit:context	Landroid/content/Context;
        //   52: ldc 61
        //   54: invokespecial 64	android/content/Intent:<init>	(Landroid/content/Context;Ljava/lang/Class;)V
        //   57: astore 7
        //   59: aload_0
        //   60: getfield 16	com/fab/FabInit$1:this$0	Lcom/fab/FabInit;
        //   63: aload 7
        //   65: invokevirtual 68	com/fab/FabInit:startActivity	(Landroid/content/Intent;)V
        //   68: return
        //   69: astore 4
        //   71: aload_0
        //   72: getfield 16	com/fab/FabInit$1:this$0	Lcom/fab/FabInit;
        //   75: invokevirtual 31	com/fab/FabInit:finish	()V
        //   78: aload_0
        //   79: getfield 16	com/fab/FabInit$1:this$0	Lcom/fab/FabInit;
        //   82: getfield 35	com/fab/FabInit:tracker	Lcom/google/android/apps/analytics/GoogleAnalyticsTracker;
        //   85: ldc 37
        //   87: invokestatic 43	com/fab/utils/FabUtils:gATracUrl	(Ljava/lang/String;)Ljava/lang/String;
        //   90: invokevirtual 49	com/google/android/apps/analytics/GoogleAnalyticsTracker:trackPageView	(Ljava/lang/String;)V
        //   93: aload_0
        //   94: getfield 16	com/fab/FabInit$1:this$0	Lcom/fab/FabInit;
        //   97: getfield 53	com/fab/FabInit:context	Landroid/content/Context;
        //   100: invokestatic 57	com/fab/utils/FabUtils:isLoggedIn	(Landroid/content/Context;)Z
        //   103: ifeq +33 -> 136
        //   106: new 59	android/content/Intent
        //   109: dup
        //   110: aload_0
        //   111: getfield 16	com/fab/FabInit$1:this$0	Lcom/fab/FabInit;
        //   114: getfield 53	com/fab/FabInit:context	Landroid/content/Context;
        //   117: ldc 61
        //   119: invokespecial 64	android/content/Intent:<init>	(Landroid/content/Context;Ljava/lang/Class;)V
        //   122: astore 5
        //   124: aload_0
        //   125: getfield 16	com/fab/FabInit$1:this$0	Lcom/fab/FabInit;
        //   128: aload 5
        //   130: invokevirtual 68	com/fab/FabInit:startActivity	(Landroid/content/Intent;)V
        //   133: goto -65 -> 68
        //   136: new 59	android/content/Intent
        //   139: dup
        //   140: aload_0
        //   141: getfield 16	com/fab/FabInit$1:this$0	Lcom/fab/FabInit;
        //   144: getfield 53	com/fab/FabInit:context	Landroid/content/Context;
        //   147: ldc 70
        //   149: invokespecial 64	android/content/Intent:<init>	(Landroid/content/Context;Ljava/lang/Class;)V
        //   152: astore 6
        //   154: aload_0
        //   155: getfield 16	com/fab/FabInit$1:this$0	Lcom/fab/FabInit;
        //   158: aload 6
        //   160: invokevirtual 68	com/fab/FabInit:startActivity	(Landroid/content/Intent;)V
        //   163: goto -95 -> 68
        //   166: astore_1
        //   167: aload_0
        //   168: getfield 16	com/fab/FabInit$1:this$0	Lcom/fab/FabInit;
        //   171: invokevirtual 31	com/fab/FabInit:finish	()V
        //   174: aload_0
        //   175: getfield 16	com/fab/FabInit$1:this$0	Lcom/fab/FabInit;
        //   178: getfield 35	com/fab/FabInit:tracker	Lcom/google/android/apps/analytics/GoogleAnalyticsTracker;
        //   181: ldc 37
        //   183: invokestatic 43	com/fab/utils/FabUtils:gATracUrl	(Ljava/lang/String;)Ljava/lang/String;
        //   186: invokevirtual 49	com/google/android/apps/analytics/GoogleAnalyticsTracker:trackPageView	(Ljava/lang/String;)V
        //   189: aload_0
        //   190: getfield 16	com/fab/FabInit$1:this$0	Lcom/fab/FabInit;
        //   193: getfield 53	com/fab/FabInit:context	Landroid/content/Context;
        //   196: invokestatic 57	com/fab/utils/FabUtils:isLoggedIn	(Landroid/content/Context;)Z
        //   199: ifeq +30 -> 229
        //   202: new 59	android/content/Intent
        //   205: dup
        //   206: aload_0
        //   207: getfield 16	com/fab/FabInit$1:this$0	Lcom/fab/FabInit;
        //   210: getfield 53	com/fab/FabInit:context	Landroid/content/Context;
        //   213: ldc 61
        //   215: invokespecial 64	android/content/Intent:<init>	(Landroid/content/Context;Ljava/lang/Class;)V
        //   218: astore_2
        //   219: aload_0
        //   220: getfield 16	com/fab/FabInit$1:this$0	Lcom/fab/FabInit;
        //   223: aload_2
        //   224: invokevirtual 68	com/fab/FabInit:startActivity	(Landroid/content/Intent;)V
        //   227: aload_1
        //   228: athrow
        //   229: new 59	android/content/Intent
        //   232: dup
        //   233: aload_0
        //   234: getfield 16	com/fab/FabInit$1:this$0	Lcom/fab/FabInit;
        //   237: getfield 53	com/fab/FabInit:context	Landroid/content/Context;
        //   240: ldc 70
        //   242: invokespecial 64	android/content/Intent:<init>	(Landroid/content/Context;Ljava/lang/Class;)V
        //   245: astore_3
        //   246: aload_0
        //   247: getfield 16	com/fab/FabInit$1:this$0	Lcom/fab/FabInit;
        //   250: aload_3
        //   251: invokevirtual 68	com/fab/FabInit:startActivity	(Landroid/content/Intent;)V
        //   254: goto -27 -> 227
        //   257: new 59	android/content/Intent
        //   260: dup
        //   261: aload_0
        //   262: getfield 16	com/fab/FabInit$1:this$0	Lcom/fab/FabInit;
        //   265: getfield 53	com/fab/FabInit:context	Landroid/content/Context;
        //   268: ldc 70
        //   270: invokespecial 64	android/content/Intent:<init>	(Landroid/content/Context;Ljava/lang/Class;)V
        //   273: astore 8
        //   275: aload_0
        //   276: getfield 16	com/fab/FabInit$1:this$0	Lcom/fab/FabInit;
        //   279: aload 8
        //   281: invokevirtual 68	com/fab/FabInit:startActivity	(Landroid/content/Intent;)V
        //   284: goto -216 -> 68
        //
        // Exception table:
        //   from	to	target	type
        //   0	6	69	java/lang/InterruptedException
        //   0	6	166	finally
      }
    }
    .start();
  }

  protected void onDestroy()
  {
    super.onDestroy();
    this.tracker.stopSession();
  }
}

/* Location:           C:\Documents and Settings\Administrator\桌面\private\反编译\apk2java\dex2java\dex-translator-0.0.9.3\dex-translator-0.0.9.3\classes_dex2jar.jar
 * Qualified Name:     com.fab.FabInit
 * JD-Core Version:    0.6.2
 */