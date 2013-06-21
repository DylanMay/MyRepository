package com.fab.environment;

import com.fab.utils.FabUtils;

public final class FabEnvironment
{
  public static final String CRASH_REPORT_URL = "http://fab.com/mobile/crash-report/?app_version=3.0";
  public static final boolean ENABLE_LOG = false;
  public static final int ENV = 1;
  public static final String FB_APP_ID = FabUtils.getFBAppId();
  public static final String[] FB_CONNECT_PERMISSIONS;
  public static final String FB_EMAIL = "fb_email";
  public static final String FB_ID = "fb_id";
  public static final String FB_KEY = "facebook-credentials";
  public static final String FB_NAME = "fb_name";
  public static final String[] FB_PERMISSIONS = new String[0];
  public static final String[] FB_TIMELINE_PERMISSIONS = arrayOfString2;
  public static final String FB_TOKEN = "access_token";
  public static final String FB_TOKEN_EXPIRES = "expires_in";
  public static final String GOOGLE_ANALYTICS_ID = "UA-19838455-1";
  public static final String GUEST_PWD = "189d054da940e4d07ea80b62e6d0a525";
  public static final String GUEST_USER = "guestuser@fab.com";
  public static final int TWEET_MAX_LENGTH = 140;
  public static final String TW_CONSUMER_KEY = "CME4WeRSOF7Y2a45xMTYYg";
  public static final String TW_CONSUMER_SECRET = "LxcTuzxcwTKMZCR6wWnHvh9isIXl1VVm6lG83NHx2e0";
  public static final String TW_PREFS_NAME = "twitterLogin";

  static
  {
    String[] arrayOfString1 = new String[2];
    arrayOfString1[0] = "publish_actions";
    arrayOfString1[1] = "email";
    FB_CONNECT_PERMISSIONS = arrayOfString1;
    String[] arrayOfString2 = new String[1];
    arrayOfString2[0] = "publish_actions";
  }

  public static final String baseDomain()
  {
    return baseUrl().split(":")[0];
  }

  public static final String baseUrl()
  {
    return "fab.com";
  }

  public static final String reqAuth()
  {
    if (baseUrl().split(":")[0].matches("glistr.com"));
    for (String str = "Basic ZmFiMjAxMXdlYiE6ZmFiLXN0YWdpbmctbnlAMjAxMiE="; ; str = "")
      return str;
  }
}

/* Location:           C:\Documents and Settings\Administrator\桌面\private\反编译\apk2java\dex2java\dex-translator-0.0.9.3\dex-translator-0.0.9.3\classes_dex2jar.jar
 * Qualified Name:     com.fab.environment.FabEnvironment
 * JD-Core Version:    0.6.2
 */