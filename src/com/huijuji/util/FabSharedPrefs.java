package com.fab.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;

public class FabSharedPrefs
{
  private static final String APP_INSTALL_TIME = "appInstallTime";
  private static final String DAILY_NOTIF_COUNT = "dailynotifcount";
  private static final String DEVICE_DENSITY = "deviceDensityNew";
  private static final String ENABLE_TIMIELINE_AFTER = "enableTimeleAfter";
  private static final String FAB_COOKIES = "fabCookies";
  private static final String FPA_COOKIE = "fpaCookie";
  private static final String GUEST_USER_NOTIFICATIONS = "guestUserNotifications";
  private static final String IS_APP_INSTALL = "isAppInstall";
  private static final String IS_GUEST_USER = "isGuest";
  private static final String NEXT_SALE_TS = "nextSaleTs";
  private static final String POLLING_REQUIRED = "pollingRequired";
  private static final String SALE_NOTIFICATION_NAME = "saleNotificationName";
  private static final String SALE_NOTIFICATION_URL = "saleNotificationUrl";
  private static final String SEND_SALE_NOTIFICATION = "sendSaleNotification";
  private static final String SPECIAL_NOTIF_COUNT = "specialnotifcount";
  private static String TAG = "FAB_SHARED_PREF";
  private static final String TLA_COOKIE = "tlaCookie";
  public static Context appContext;
  private static SharedPreferences preferences;
  static final String sClassName = "android.util.DisplayMetrics";
  private static String sharedPrefsName;

  public static void clearFabCookies()
  {
    if (preferences != null)
    {
      SharedPreferences.Editor localEditor = preferences.edit();
      localEditor.remove("fabCookies");
      localEditor.commit();
    }
  }

  public static void deleteCookies()
  {
    if (preferences != null)
    {
      SharedPreferences.Editor localEditor = preferences.edit();
      localEditor.remove("fpaCookie");
      localEditor.remove("tlaCookie");
      localEditor.commit();
    }
  }

  public static String getAppInstallTime()
  {
    if (preferences != null);
    for (String str = preferences.getString("appInstallTime", ""); ; str = "")
      return str;
  }

  public static int getDailyNotifCount()
  {
    int i = 0;
    if (preferences != null)
      i = preferences.getInt("dailynotifcount", i);
    return i;
  }

  public static String getDeviceDensity()
  {
    if (preferences != null);
    for (String str = preferences.getString("deviceDensityNew", ""); ; str = "")
      return str;
  }

  public static long getEnableTimelineAfter()
  {
    long l = 0L;
    if (preferences != null)
      l = preferences.getLong("enableTimeleAfter", l);
    return l;
  }

  public static String getFabCookies()
  {
    if (preferences != null);
    for (String str = preferences.getString("fabCookies", ""); ; str = "")
      return str;
  }

  public static String getFpaCookie()
  {
    if (preferences != null);
    for (String str = preferences.getString("fpaCookie", ""); ; str = "")
      return str;
  }

  public static long getNextSaleTs()
  {
    long l = 0L;
    if (preferences != null)
      l = preferences.getLong("nextSaleTs", l);
    return l;
  }

  public static boolean getSaleNotification()
  {
    boolean bool = false;
    if (preferences != null)
      bool = preferences.getBoolean("sendSaleNotification", bool);
    return bool;
  }

  public static String getSaleNotificationName()
  {
    if (preferences != null);
    for (String str = preferences.getString("saleNotificationName", ""); ; str = "")
      return str;
  }

  public static String getSaleNotificationUrl()
  {
    if (preferences != null);
    for (String str = preferences.getString("saleNotificationUrl", ""); ; str = "")
      return str;
  }

  public static int getSpecialNotifCount()
  {
    int i = 0;
    if (preferences != null)
      i = preferences.getInt("specialnotifcount", i);
    return i;
  }

  public static String getTlaCookie()
  {
    if (preferences != null);
    for (String str = preferences.getString("tlaCookie", ""); ; str = "")
      return str;
  }

  public static void initializePreferences(Context paramContext)
  {
    if (paramContext != null)
    {
      appContext = paramContext;
      sharedPrefsName = appContext.getResources().getString(2131165185);
      preferences = appContext.getSharedPreferences(sharedPrefsName, 0);
    }
  }

  public static boolean isAppInstall()
  {
    boolean bool = true;
    if (preferences != null)
      bool = preferences.getBoolean("isAppInstall", bool);
    return bool;
  }

  public static boolean isGuestUser()
  {
    boolean bool = false;
    if (preferences != null)
      bool = preferences.getBoolean("isGuest", bool);
    return bool;
  }

  public static boolean isGuestUserNotificationsSet()
  {
    if (preferences != null);
    for (boolean bool = preferences.getBoolean("guestUserNotifications", true); ; bool = false)
      return bool;
  }

  public static boolean isPollingRequired()
  {
    boolean bool = false;
    if (preferences != null)
      bool = preferences.getBoolean("pollingRequired", bool);
    return bool;
  }

  public static void setAppInstall(boolean paramBoolean)
  {
    if (preferences != null)
    {
      SharedPreferences.Editor localEditor = preferences.edit();
      localEditor.putBoolean("isAppInstall", paramBoolean);
      localEditor.commit();
    }
  }

  public static void setAppInstallTime(String paramString)
  {
    if (preferences != null)
    {
      SharedPreferences.Editor localEditor = preferences.edit();
      localEditor.putString("appInstallTime", paramString);
      localEditor.commit();
    }
  }

  public static void setDailyNotifCount(int paramInt)
  {
    if (preferences != null)
    {
      SharedPreferences.Editor localEditor = preferences.edit();
      localEditor.putInt("dailynotifcount", paramInt);
      localEditor.commit();
    }
  }

  public static void setDeviceDensity(int paramInt)
  {
    FabUtils.log(TAG, paramInt);
    String str = "l";
    switch (paramInt)
    {
    default:
    case 120:
    case 160:
    case 240:
    case 320:
    }
    while (true)
    {
      SharedPreferences.Editor localEditor = preferences.edit();
      localEditor.putString("deviceDensityNew", str);
      localEditor.commit();
      return;
      str = "l";
      continue;
      str = "m";
      continue;
      str = "h";
      continue;
      str = "xh";
    }
  }

  public static void setEnableTimelineAfter(long paramLong)
  {
    if (preferences != null)
    {
      SharedPreferences.Editor localEditor = preferences.edit();
      localEditor.putLong("enableTimeleAfter", paramLong);
      localEditor.commit();
    }
  }

  public static void setFabCookies(String paramString)
  {
    if (preferences != null)
    {
      SharedPreferences.Editor localEditor = preferences.edit();
      localEditor.putString("fabCookies", paramString);
      localEditor.commit();
    }
  }

  public static void setFpaCookie(String paramString)
  {
    if (preferences != null)
    {
      SharedPreferences.Editor localEditor = preferences.edit();
      localEditor.putString("fpaCookie", paramString);
      localEditor.commit();
    }
  }

  public static void setGuestUser(boolean paramBoolean)
  {
    if (preferences != null)
    {
      SharedPreferences.Editor localEditor = preferences.edit();
      localEditor.putBoolean("isGuest", paramBoolean);
      localEditor.commit();
    }
  }

  public static void setGuestUserNotifications(boolean paramBoolean)
  {
    if (preferences != null)
    {
      SharedPreferences.Editor localEditor = preferences.edit();
      localEditor.putBoolean("guestUserNotifications", paramBoolean);
      localEditor.commit();
    }
  }

  public static void setNextSaleTs(long paramLong)
  {
    if (preferences != null)
    {
      SharedPreferences.Editor localEditor = preferences.edit();
      localEditor.putLong("nextSaleTs", paramLong);
      localEditor.commit();
    }
  }

  public static void setPollingRequired(boolean paramBoolean)
  {
    if (preferences != null)
    {
      SharedPreferences.Editor localEditor = preferences.edit();
      localEditor.putBoolean("pollingRequired", paramBoolean);
      localEditor.commit();
    }
  }

  public static void setSaleNotification(boolean paramBoolean)
  {
    if (preferences != null)
    {
      SharedPreferences.Editor localEditor = preferences.edit();
      localEditor.putBoolean("sendSaleNotification", paramBoolean);
      localEditor.commit();
    }
  }

  public static void setSaleNotificationName(String paramString)
  {
    if (preferences != null)
    {
      SharedPreferences.Editor localEditor = preferences.edit();
      localEditor.putString("saleNotificationName", paramString);
      localEditor.commit();
    }
  }

  public static void setSaleNotificationUrl(String paramString)
  {
    if (preferences != null)
    {
      SharedPreferences.Editor localEditor = preferences.edit();
      localEditor.putString("saleNotificationUrl", paramString);
      localEditor.commit();
    }
  }

  public static void setSpecialNotifCount(int paramInt)
  {
    if (preferences != null)
    {
      SharedPreferences.Editor localEditor = preferences.edit();
      localEditor.putInt("specialnotifcount", paramInt);
      localEditor.commit();
    }
  }

  public static void setTlaCookie(String paramString)
  {
    if (preferences != null)
    {
      SharedPreferences.Editor localEditor = preferences.edit();
      localEditor.putString("tlaCookie", paramString);
      localEditor.commit();
    }
  }

  public static void unlinkTwitter()
  {
    SharedPreferences.Editor localEditor = appContext.getSharedPreferences("twitterLogin", 0).edit();
    localEditor.remove("accessTokenToken");
    localEditor.remove("accessTokenSecret");
    localEditor.commit();
  }
}

/* Location:           C:\Documents and Settings\Administrator\桌面\private\反编译\apk2java\dex2java\dex-translator-0.0.9.3\dex-translator-0.0.9.3\classes_dex2jar.jar
 * Qualified Name:     com.fab.utils.FabSharedPrefs
 * JD-Core Version:    0.6.2
 */