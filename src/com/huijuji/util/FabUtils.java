package com.fab.utils;

import android.app.AlarmManager;
import android.app.AlertDialog.Builder;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.database.SQLException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;
import com.fab.FabApplication;
import com.fab.FabSignin;
import com.fab.SaleNotificationWakeReceiver;
import com.fab.SaleWakeReceiver;
import com.fab.WakeReceiver;
import com.fab.backgroundservice.TimerNotificationService;
import com.fab.db.FabDBAdapter;
import com.fab.environment.FabEnvironment;
import java.io.File;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.Random;
import org.json.JSONException;
import org.json.JSONObject;

public class FabUtils
{
  private static final int DEFAULT_SALE = 0;
  private static final int SPECIAL_SALE = 1;
  private static final String TAG = "FAB_UTILS";
  private static final int WEEKLY_SALE = 2;
  private static Random randomNum;

  public static String buildCuratorImageUrl(int paramInt1, int paramInt2)
  {
    return "http://cdn1.fab.com/curator/" + paramInt1 + "-" + paramInt2 + ".png";
  }

  public static void cancelAlarmForAllWeeklyShopNotification(Context paramContext, ArrayList<String> paramArrayList)
  {
    Intent localIntent = new Intent(paramContext, SaleNotificationWakeReceiver.class);
    AlarmManager localAlarmManager = (AlarmManager)paramContext.getSystemService("alarm");
    Iterator localIterator = paramArrayList.iterator();
    while (true)
    {
      if (!localIterator.hasNext())
        return;
      String str = (String)localIterator.next();
      localIntent.setAction("WEEKLY_SHOP_NOTIFICATION_" + str);
      localAlarmManager.cancel(PendingIntent.getBroadcast(paramContext, 1, localIntent, 134217728));
    }
  }

  public static void cancelAlarmForPullNotification(Context paramContext)
  {
    Intent localIntent = new Intent(paramContext, SaleWakeReceiver.class);
    localIntent.setAction("FAB_PULL_NOTIFICATION_ALARM");
    PendingIntent localPendingIntent = PendingIntent.getBroadcast(paramContext, 1, localIntent, 0);
    ((AlarmManager)paramContext.getSystemService("alarm")).cancel(localPendingIntent);
  }

  public static void cancelAlarmForSaleNotification(Context paramContext)
  {
    Intent localIntent = new Intent(paramContext, SaleNotificationWakeReceiver.class);
    AlarmManager localAlarmManager = (AlarmManager)paramContext.getSystemService("alarm");
    localIntent.setAction("FAB_SALE_NOTIFICATION_ALARM_0");
    int i = FabSharedPrefs.getDailyNotifCount();
    for (int j = 1; ; j++)
    {
      if (j > i)
      {
        FabSharedPrefs.setDailyNotifCount(0);
        return;
      }
      localAlarmManager.cancel(PendingIntent.getBroadcast(paramContext, j, localIntent, 134217728));
    }
  }

  public static void cancelAlarmForSpecialNotification(Context paramContext)
  {
    Intent localIntent = new Intent(paramContext, SaleNotificationWakeReceiver.class);
    AlarmManager localAlarmManager = (AlarmManager)paramContext.getSystemService("alarm");
    localIntent.setAction("FAB_SALE_NOTIFICATION_ALARM_1");
    int i = FabSharedPrefs.getSpecialNotifCount();
    for (int j = 1; ; j++)
    {
      if (j > i)
      {
        FabSharedPrefs.setSpecialNotifCount(0);
        return;
      }
      localAlarmManager.cancel(PendingIntent.getBroadcast(paramContext, j, localIntent, 134217728));
    }
  }

  public static void cancelAlarmForWeeklyShopNotification(Context paramContext, String paramString)
  {
    Intent localIntent = new Intent(paramContext, SaleNotificationWakeReceiver.class);
    localIntent.setAction("WEEKLY_SHOP_NOTIFICATION_" + paramString);
    PendingIntent localPendingIntent = PendingIntent.getBroadcast(paramContext, 1, localIntent, 134217728);
    ((AlarmManager)paramContext.getSystemService("alarm")).cancel(localPendingIntent);
  }

  public static boolean checkInternetConnection(Context paramContext)
  {
    boolean bool1 = false;
    if (paramContext != null);
    try
    {
      ConnectivityManager localConnectivityManager = (ConnectivityManager)paramContext.getSystemService("connectivity");
      if ((localConnectivityManager != null) && (localConnectivityManager.getActiveNetworkInfo() != null) && (localConnectivityManager.getActiveNetworkInfo().isAvailable()))
      {
        boolean bool2 = localConnectivityManager.getActiveNetworkInfo().isConnected();
        if (bool2)
          bool1 = true;
      }
      label53: return bool1;
    }
    catch (NullPointerException localNullPointerException)
    {
      break label53;
    }
  }

  public static boolean deleteDir(File paramFile)
  {
    boolean bool = false;
    String[] arrayOfString;
    if ((paramFile != null) && (paramFile.isDirectory()))
      arrayOfString = paramFile.list();
    for (int i = 0; ; i++)
    {
      if (i >= arrayOfString.length)
        if (paramFile != null)
          bool = paramFile.delete();
      while (!deleteDir(new File(paramFile, arrayOfString[i])))
        return bool;
    }
  }

  public static String gATracUrl(String paramString)
  {
    return "/mobile/android/" + paramString + "?app_version=" + FabApplication.APP_VERSION + "&device_model=" + URLEncoder.encode(Build.MODEL) + "&device_os=" + URLEncoder.encode(Build.VERSION.RELEASE);
  }

  public static String[] getCuratorMessage(String paramString, int paramInt)
  {
    String[] arrayOfString;
    if (paramString.length() > paramInt)
    {
      int i = paramString.substring(0, paramInt).lastIndexOf(" ");
      arrayOfString = new String[2];
      arrayOfString[0] = paramString.substring(0, i);
      arrayOfString[1] = paramString.substring(i);
    }
    while (true)
    {
      return arrayOfString;
      arrayOfString = new String[1];
      arrayOfString[0] = paramString;
    }
  }

  public static String getFBAppId()
  {
    return "105523866144321";
  }

  public static String getReadableDate(String paramString)
  {
    String[] arrayOfString = paramString.split("-");
    SimpleDateFormat localSimpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat localSimpleDateFormat2 = new SimpleDateFormat("EEEE MM/dd");
    String str;
    if (arrayOfString.length != 3)
      str = "";
    while (true)
    {
      return str;
      try
      {
        Date localDate = localSimpleDateFormat1.parse(paramString);
        str = localSimpleDateFormat2.format(localDate);
      }
      catch (ParseException localParseException)
      {
        str = "";
      }
    }
  }

  public static String getSaleDurationString(int paramInt1, int paramInt2)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    Date localDate = new Date();
    GregorianCalendar localGregorianCalendar = new GregorianCalendar();
    localGregorianCalendar.setTime(localDate);
    long l1 = localGregorianCalendar.getTimeInMillis() / 1000L;
    long l2 = 0L;
    long l4;
    String str3;
    label140: String str1;
    if ((paramInt1 > 0) && (paramInt1 > l1))
    {
      localStringBuilder.append("Sale starts in ");
      l2 = paramInt1 - l1;
      long l3 = l2 / 86400L;
      l4 = (l2 - 86400L * l3) / 3600L;
      if (l3 > 0L)
      {
        localStringBuilder.append(l3);
        if (l3 != 1L)
          break label248;
        str3 = " day";
        localStringBuilder.append(str3);
      }
      if (l4 > 0L)
      {
        if (l3 <= 0L)
          break label256;
        str1 = " and " + l4;
        label185: localStringBuilder.append(str1);
        if (l4 != 1L)
          break label276;
      }
    }
    label256: label276: for (String str2 = " hour"; ; str2 = " hours")
    {
      localStringBuilder.append(str2);
      return localStringBuilder.toString();
      if ((paramInt2 <= 0) || (paramInt2 <= l1))
        break;
      localStringBuilder.append("Sale ends in ");
      l2 = paramInt2 - l1;
      break;
      label248: str3 = " days";
      break label140;
      str1 = l4;
      break label185;
    }
  }

  public static long getSecondsLeftForTimer(long paramLong, Calendar paramCalendar)
  {
    try
    {
      paramCalendar.setTime(new Date());
      long l1 = paramCalendar.getTimeInMillis() / 1000L;
      if (paramLong - l1 >= 0L)
      {
        l2 = paramLong - l1;
        return l2;
      }
      long l2 = -1L;
    }
    finally
    {
    }
  }

  public static String getTimeLeft(long paramLong, Calendar paramCalendar)
  {
    paramCalendar.setTime(new Date());
    long l1 = paramLong - paramCalendar.getTimeInMillis() / 1000L;
    if (l1 <= 0L);
    Object[] arrayOfObject;
    for (String str = "00:00"; ; str = String.format("%02d:%02d", arrayOfObject))
    {
      return str;
      long l2 = l1 / 60L;
      long l3 = l1 - 60L * l2;
      arrayOfObject = new Object[2];
      arrayOfObject[0] = Long.valueOf(l2);
      arrayOfObject[1] = Long.valueOf(l3);
    }
  }

  public static JSONObject getUserInfo(Context paramContext)
  {
    try
    {
      FabDBAdapter localFabDBAdapter = new FabDBAdapter(paramContext);
      localFabDBAdapter.open();
      localJSONObject = localFabDBAdapter.getUserInfo();
      localFabDBAdapter.close();
      return localJSONObject;
    }
    catch (Exception localException)
    {
      while (true)
        JSONObject localJSONObject = new JSONObject();
    }
  }

  public static String getUserName(Context paramContext)
  {
    FabDBAdapter localFabDBAdapter = new FabDBAdapter(paramContext);
    localFabDBAdapter.open();
    JSONObject localJSONObject = localFabDBAdapter.getUserInfo();
    localFabDBAdapter.close();
    if (localJSONObject.length() > 0);
    while (true)
    {
      try
      {
        log("FAB_UTILS", "getting user info");
        log("FAB_UTILS", localJSONObject.toString());
        String str2 = localJSONObject.getString("username");
        str1 = str2;
        return str1;
      }
      catch (JSONException localJSONException)
      {
      }
      String str1 = "";
    }
  }

  public static boolean isAppRated(Context paramContext)
  {
    FabDBAdapter localFabDBAdapter = new FabDBAdapter(paramContext);
    localFabDBAdapter.open();
    boolean bool = localFabDBAdapter.isAppRated();
    localFabDBAdapter.close();
    return bool;
  }

  public static boolean isLogEnabled()
  {
    return false;
  }

  // ERROR //
  public static boolean isLoggedIn(Context paramContext)
  {
    // Byte code:
    //   0: iconst_1
    //   1: istore_1
    //   2: invokestatic 365	com/fab/utils/FabSharedPrefs:getFabCookies	()Ljava/lang/String;
    //   5: invokestatic 371	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   8: ifne +13 -> 21
    //   11: ldc 22
    //   13: ldc_w 373
    //   16: invokestatic 349	com/fab/utils/FabUtils:log	(Ljava/lang/String;Ljava/lang/String;)V
    //   19: iload_1
    //   20: ireturn
    //   21: new 323	com/fab/db/FabDBAdapter
    //   24: dup
    //   25: aload_0
    //   26: invokespecial 325	com/fab/db/FabDBAdapter:<init>	(Landroid/content/Context;)V
    //   29: astore_2
    //   30: aload_2
    //   31: invokevirtual 329	com/fab/db/FabDBAdapter:open	()Lcom/fab/db/FabDBAdapter;
    //   34: pop
    //   35: aload_2
    //   36: invokevirtual 332	com/fab/db/FabDBAdapter:getUserInfo	()Lorg/json/JSONObject;
    //   39: astore 5
    //   41: aload_2
    //   42: invokevirtual 335	com/fab/db/FabDBAdapter:close	()V
    //   45: aload 5
    //   47: invokevirtual 343	org/json/JSONObject:length	()I
    //   50: ifle +71 -> 121
    //   53: ldc 22
    //   55: ldc_w 345
    //   58: invokestatic 349	com/fab/utils/FabUtils:log	(Ljava/lang/String;Ljava/lang/String;)V
    //   61: ldc 22
    //   63: aload 5
    //   65: invokevirtual 350	org/json/JSONObject:toString	()Ljava/lang/String;
    //   68: invokestatic 349	com/fab/utils/FabUtils:log	(Ljava/lang/String;Ljava/lang/String;)V
    //   71: ldc 22
    //   73: aload 5
    //   75: ldc_w 375
    //   78: invokevirtual 355	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   81: invokestatic 349	com/fab/utils/FabUtils:log	(Ljava/lang/String;Ljava/lang/String;)V
    //   84: aload 5
    //   86: ldc_w 375
    //   89: invokevirtual 355	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   92: invokestatic 378	com/fab/utils/FabSharedPrefs:setFabCookies	(Ljava/lang/String;)V
    //   95: ldc 22
    //   97: ldc_w 380
    //   100: invokestatic 349	com/fab/utils/FabUtils:log	(Ljava/lang/String;Ljava/lang/String;)V
    //   103: invokestatic 365	com/fab/utils/FabSharedPrefs:getFabCookies	()Ljava/lang/String;
    //   106: invokestatic 371	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   109: istore 7
    //   111: iload 7
    //   113: ifeq -94 -> 19
    //   116: iconst_0
    //   117: istore_1
    //   118: goto -99 -> 19
    //   121: iconst_0
    //   122: istore_1
    //   123: goto -104 -> 19
    //   126: astore_3
    //   127: iconst_0
    //   128: istore_1
    //   129: goto -110 -> 19
    //   132: astore 6
    //   134: goto -39 -> 95
    //
    // Exception table:
    //   from	to	target	type
    //   30	71	126	android/database/SQLException
    //   71	95	126	android/database/SQLException
    //   95	111	126	android/database/SQLException
    //   71	95	132	java/lang/Exception
  }

  public static void log(String paramString1, String paramString2)
  {
  }

  public static void log(String paramString1, String paramString2, String paramString3)
  {
  }

  public static void logoutUser(Context paramContext, final Handler paramHandler)
  {
    ExecuteRequestAsyncTask localExecuteRequestAsyncTask = new ExecuteRequestAsyncTask(paramContext, "GET");
    localExecuteRequestAsyncTask.setTaskCompleteListener(new TaskCompleteListener()
    {
      public void onTaskComplete()
      {
      }

      public void onTaskComplete(Object paramAnonymousObject)
      {
        JSONObject localJSONObject = (JSONObject)paramAnonymousObject;
        if (localJSONObject != null);
        try
        {
          if (TextUtils.isEmpty(localJSONObject.getString("err_msg")))
          {
            FabSharedPrefs.clearFabCookies();
            FabSharedPrefs.unlinkTwitter();
            FabUtils.log("FAB_UTILS", "shared pref cleared");
            FabDBAdapter localFabDBAdapter = new FabDBAdapter(FabUtils.this);
            localFabDBAdapter.open();
            localFabDBAdapter.emptyUsers();
            localFabDBAdapter.close();
            FabUtils.log("FAB_UTILS", "db cleared");
            FabHelper.logoutFromFB(FabUtils.this, paramHandler);
            FabUtils.stopTimerService(FabUtils.this);
            FabUtils.cancelAlarmForPullNotification(FabUtils.this);
            FabUtils.cancelAlarmForSaleNotification(FabUtils.this);
            FabUtils.cancelAlarmForSpecialNotification(FabUtils.this);
          }
          label110: return;
        }
        catch (JSONException localJSONException)
        {
          break label110;
        }
        catch (SQLException localSQLException)
        {
          break label110;
        }
      }
    });
    String[] arrayOfString = new String[1];
    arrayOfString[0] = requestUrl(true, "/mobile/logout/", "");
    localExecuteRequestAsyncTask.execute(arrayOfString);
  }

  public static void print(String paramString)
  {
  }

  public static long rateApp(Context paramContext, int paramInt)
  {
    FabDBAdapter localFabDBAdapter = new FabDBAdapter(paramContext);
    localFabDBAdapter.open();
    long l = localFabDBAdapter.updateRateApp(paramInt);
    localFabDBAdapter.close();
    return l;
  }

  public static String requestPostUrl(boolean paramBoolean, String paramString)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    if (paramBoolean)
      localStringBuilder.append("https://");
    while (true)
    {
      localStringBuilder.append(FabEnvironment.baseUrl());
      localStringBuilder.append(paramString);
      log("FAB_UTILS", localStringBuilder.toString());
      return localStringBuilder.toString();
      localStringBuilder.append("http://");
    }
  }

  public static String requestUrl(boolean paramBoolean, String paramString1, String paramString2)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    if (paramBoolean)
    {
      localStringBuilder.append("https://");
      localStringBuilder.append(FabEnvironment.baseUrl());
      if ((paramString1.contains("?")) && (paramString2.contains("?")) && (paramString2.indexOf("?") == 0))
      {
        paramString1 = paramString1 + paramString2.replace("?", "&");
        paramString2 = "";
      }
      localStringBuilder.append(paramString1);
      localStringBuilder.append(paramString2);
      if ((!TextUtils.isEmpty(paramString2)) || (paramString1.contains("?")))
        break label215;
    }
    label215: for (String str = "?d="; ; str = "&d=")
    {
      localStringBuilder.append(str);
      localStringBuilder.append(FabSharedPrefs.getDeviceDensity());
      localStringBuilder.append("&app_version=" + FabApplication.APP_VERSION + "&device_model=" + URLEncoder.encode(Build.MODEL) + "&device_os=" + URLEncoder.encode(Build.VERSION.RELEASE));
      log("FAB_UTILS", localStringBuilder.toString());
      return localStringBuilder.toString();
      localStringBuilder.append("http://");
      break;
    }
  }

  public static String s3MobileIconsUrl(String paramString)
  {
    return "http://cdn1.fab.com/mobile-images/android/" + paramString + ".png";
  }

  public static String s3SalesImageUrl(String paramString1, int paramInt1, String paramString2, int paramInt2, String paramString3)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("http://cdn1.fab.com/");
    if (paramString1.equalsIgnoreCase("p"))
    {
      localStringBuilder.append("product");
      localStringBuilder.append("/");
      localStringBuilder.append(paramInt1);
      localStringBuilder.append("-");
      localStringBuilder.append(paramString2);
      localStringBuilder.append("-");
      localStringBuilder.append(paramInt2);
      localStringBuilder.append("-");
      localStringBuilder.append(paramString3);
    }
    while (true)
    {
      localStringBuilder.append(".png");
      return localStringBuilder.toString();
      if (paramString1.equalsIgnoreCase("s"))
      {
        localStringBuilder.append("sale");
        localStringBuilder.append("/");
        localStringBuilder.append(paramString3);
        localStringBuilder.append("/");
        localStringBuilder.append(paramInt1);
        localStringBuilder.append("-");
        localStringBuilder.append(paramString2);
        localStringBuilder.append("-");
        localStringBuilder.append(paramInt2);
      }
    }
  }

  public static void setAlarm(Context paramContext)
  {
    Intent localIntent = new Intent(paramContext, WakeReceiver.class);
    localIntent.setAction("FAB_ALARM");
    PendingIntent localPendingIntent = PendingIntent.getBroadcast(paramContext, 1, localIntent, 0);
    AlarmManager localAlarmManager = (AlarmManager)paramContext.getSystemService("alarm");
    localAlarmManager.cancel(localPendingIntent);
    Calendar localCalendar = Calendar.getInstance();
    localCalendar.add(5, 1);
    localCalendar.set(11, 8);
    localCalendar.set(12, 0);
    localCalendar.set(13, 0);
    localAlarmManager.setRepeating(0, localCalendar.getTimeInMillis(), 86400000L, localPendingIntent);
  }

  public static void setAlarmForPullNotification(Context paramContext, long paramLong)
  {
    Intent localIntent = new Intent(paramContext, SaleWakeReceiver.class);
    localIntent.setAction("FAB_PULL_NOTIFICATION_ALARM");
    PendingIntent localPendingIntent = PendingIntent.getBroadcast(paramContext, 1, localIntent, 0);
    AlarmManager localAlarmManager = (AlarmManager)paramContext.getSystemService("alarm");
    localAlarmManager.cancel(localPendingIntent);
    Calendar localCalendar = Calendar.getInstance();
    randomNum = new Random();
    if (paramLong == 0L)
    {
      localCalendar.set(11, 0);
      localCalendar.set(12, 0);
      localCalendar.set(13, 0);
    }
    while (true)
    {
      localCalendar.getTime();
      localAlarmManager.set(0, localCalendar.getTimeInMillis(), localPendingIntent);
      return;
      if (1000L * paramLong > localCalendar.getTimeInMillis())
      {
        localCalendar.setTimeInMillis(1000L * (paramLong + randomNum.nextInt(3600)));
      }
      else
      {
        localCalendar.add(5, 1);
        localCalendar.set(11, 0);
        localCalendar.set(12, randomNum.nextInt(60));
        localCalendar.set(13, randomNum.nextInt(60));
      }
    }
  }

  public static void setAlarmForSaleNotification(Context paramContext, long paramLong, String paramString1, String paramString2, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, String paramString3, String paramString4, int paramInt6, int paramInt7)
  {
    Calendar localCalendar2;
    AlarmManager localAlarmManager;
    Intent localIntent;
    if (paramLong > 0L)
    {
      Calendar localCalendar1 = Calendar.getInstance();
      localCalendar2 = Calendar.getInstance();
      localCalendar2.setTimeInMillis(1000L * paramLong);
      localCalendar2.getTime();
      if (localCalendar2.after(localCalendar1))
      {
        localAlarmManager = (AlarmManager)paramContext.getSystemService("alarm");
        localIntent = new Intent(paramContext, SaleNotificationWakeReceiver.class);
        localIntent.setAction("FAB_SALE_NOTIFICATION_ALARM_" + paramInt3);
        localIntent.putExtra("SALE_TYPE", paramString1);
        localIntent.putExtra("MSG", paramString2);
        localIntent.putExtra("SALE_ID", paramInt1);
        localIntent.putExtra("PROD_ID", paramInt2);
        localIntent.putExtra("SHOP_ID", paramInt4);
        localIntent.putExtra("WEEKLY_SHOP_ID", paramInt5);
        localIntent.putExtra("GO_TO", paramString3);
        localIntent.putExtra("GO_TO_URL", paramString4);
        localIntent.putExtra("IS_SSL", paramInt6);
        localIntent.putExtra("IS_CHECK_LOGIN", paramInt7);
        if (paramInt3 != 0)
          break label246;
        int j = 1 + FabSharedPrefs.getDailyNotifCount();
        FabSharedPrefs.setDailyNotifCount(j);
        PendingIntent localPendingIntent2 = PendingIntent.getBroadcast(paramContext, j, localIntent, 134217728);
        localAlarmManager.setRepeating(0, localCalendar2.getTimeInMillis(), 86400000L, localPendingIntent2);
      }
    }
    while (true)
    {
      return;
      label246: int i = 1 + FabSharedPrefs.getSpecialNotifCount();
      FabSharedPrefs.setSpecialNotifCount(i);
      PendingIntent localPendingIntent1 = PendingIntent.getBroadcast(paramContext, i, localIntent, 134217728);
      localAlarmManager.set(0, localCalendar2.getTimeInMillis(), localPendingIntent1);
    }
  }

  public static void setAlarmForWeeklyShopNotification(Context paramContext, String paramString1, long paramLong, String paramString2, int paramInt)
  {
    long l = paramLong * 1000L;
    Intent localIntent = new Intent(paramContext, SaleNotificationWakeReceiver.class);
    localIntent.setAction("WEEKLY_SHOP_NOTIFICATION_" + paramString1);
    localIntent.putExtra("SALE_TYPE", "shops");
    localIntent.putExtra("MSG", paramString2);
    localIntent.putExtra("WEEKLY_SHOP_ID", paramInt);
    PendingIntent localPendingIntent = PendingIntent.getBroadcast(paramContext, 1, localIntent, 134217728);
    AlarmManager localAlarmManager = (AlarmManager)paramContext.getSystemService("alarm");
    localAlarmManager.cancel(localPendingIntent);
    Calendar localCalendar = Calendar.getInstance();
    while (true)
    {
      if (l >= localCalendar.getTimeInMillis())
      {
        localCalendar.setTimeInMillis(l);
        localCalendar.getTime();
        localAlarmManager.setRepeating(0, localCalendar.getTimeInMillis(), 604800000L, localPendingIntent);
        return;
      }
      l += 604800000L;
    }
  }

  public static String setWebViewStyle(String paramString1, String paramString2, String paramString3)
  {
    String str = paramString1.replaceAll("%", "%25").replaceAll("#", "%23").replaceAll("\\\\", "%27");
    return "<span style=\"font-family:" + paramString2 + ";font-size:" + paramString3 + "\">" + str + "</span>";
  }

  public static void showAlert(Context paramContext, String paramString)
  {
    Toast.makeText(paramContext, paramString, 0).show();
  }

  public static void showGuestAlert(Context paramContext, String paramString1, String paramString2, final boolean paramBoolean)
  {
    AlertDialog.Builder localBuilder = new AlertDialog.Builder(paramContext);
    localBuilder.setIcon(2130837703);
    if (TextUtils.isEmpty(paramString1))
      paramString1 = "Fab";
    localBuilder.setTitle(paramString1);
    localBuilder.setMessage(paramString2);
    localBuilder.setPositiveButton("Cancel", null);
    localBuilder.setNeutralButton("Sign-In", new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
      {
        Intent localIntent = new Intent(FabUtils.this, FabSignin.class);
        localIntent.setFlags(67108864);
        localIntent.putExtra("IS_FROM_GUEST", true);
        localIntent.putExtra("REFRESH_PREVIOUS_ACTIVITY", paramBoolean);
        FabUtils.this.startActivity(localIntent);
      }
    });
    localBuilder.show();
  }

  public static void showRateAppDialog(Context paramContext)
  {
    if (!isAppRated(paramContext))
    {
      AlertDialog.Builder localBuilder = new AlertDialog.Builder(paramContext);
      localBuilder.setIcon(2130837703);
      localBuilder.setTitle("Rate Fab");
      localBuilder.setMessage("If you enjoy Fab.com, then please take a minute to rate it. Thanks for your support!");
      localBuilder.setPositiveButton("Rate Fab", new DialogInterface.OnClickListener()
      {
        public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
        {
          FabUtils.rateApp(FabUtils.this, 1);
          Intent localIntent = new Intent("android.intent.action.VIEW");
          localIntent.setData(Uri.parse("market://details?id=com.fab"));
          FabUtils.this.startActivity(localIntent);
        }
      });
      localBuilder.setNeutralButton("Remind Me Later", null);
      localBuilder.setNegativeButton("No, Thanks", new DialogInterface.OnClickListener()
      {
        public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
        {
          FabUtils.rateApp(FabUtils.this, 1);
        }
      });
      localBuilder.show();
    }
  }

  public static void stopTimerService(Context paramContext)
  {
    Log.i("FAB_UTILS", "Stopping TimerNotificationService Service");
    Intent localIntent = new Intent(paramContext, TimerNotificationService.class);
    try
    {
      paramContext.stopService(localIntent);
      FabHelper.setCartTime(0L);
      return;
    }
    catch (Exception localException)
    {
      while (true)
        Log.i("FAB_UTILS", "Error while stopping TimerNotificationService Service");
    }
  }

  public static void trimCache(Context paramContext)
  {
    try
    {
      File localFile = paramContext.getCacheDir();
      if ((localFile != null) && (localFile.isDirectory()))
        deleteDir(localFile);
      label21: return;
    }
    catch (Exception localException)
    {
      break label21;
    }
  }
}

/* Location:           C:\Documents and Settings\Administrator\桌面\private\反编译\apk2java\dex2java\dex-translator-0.0.9.3\dex-translator-0.0.9.3\classes_dex2jar.jar
 * Qualified Name:     com.fab.utils.FabUtils
 * JD-Core Version:    0.6.2
 */