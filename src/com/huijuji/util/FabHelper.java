package com.fab.utils;

import android.content.Context;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Environment;
import android.os.Handler;
import android.text.TextUtils;
import android.widget.ListView;
import com.fab.FabApplication;
import com.fab.db.FabDBAdapter;
import com.fab.facebook.FabFBClient;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.HashMap;
import org.json.JSONException;
import org.json.JSONObject;

public class FabHelper
{
  public static final int ALERT = 2;
  public static final int CANCLE_DIALOG = 9;
  public static final int FAV_IMAGE = 3;
  public static final int FB_CONNECT_COMPLETE = 7;
  public static final int FB_FAILURE = 5;
  public static final int FB_SETTINGS_LOGIN_FAILURE = 11;
  public static final int FB_SETTINGS_LOGIN_SUCCESS = 10;
  public static final int FB_SUCCESS = 4;
  public static final int FINISH = 6;
  public static final int SHOW_DIALOG = 8;
  public static final int SHOW_ERROR_ALERT = 12;
  public static final int TIMER = 1;
  private static int cartCount;
  private static long cartTime;
  private static TimerTask timerTask;
  public static boolean timerTaskCompleted;
  protected boolean isRunning;
  private long reservedTime;
  private String tag = "FabHelper";

  public static FabFBClient connectToFacebook(Context paramContext, boolean paramBoolean1, Handler paramHandler, boolean paramBoolean2)
  {
    FabFBClient localFabFBClient = new FabFBClient(paramContext, true);
    localFabFBClient.setmHandler(paramHandler);
    localFabFBClient.setFromGuest(paramBoolean2);
    if (paramBoolean1)
      localFabFBClient.facebookReConnect();
    while (true)
    {
      return localFabFBClient;
      localFabFBClient.facebookConnect();
    }
  }

  public static FabFBClient connectUsingFbOAuthUserAgent(Context paramContext, Handler paramHandler)
  {
    FabFBClient localFabFBClient = new FabFBClient(paramContext, true);
    localFabFBClient.setmHandler(paramHandler);
    localFabFBClient.fbOAuthUserAgent();
    return localFabFBClient;
  }

  public static long daysBetween(Calendar paramCalendar1, Calendar paramCalendar2)
  {
    Calendar localCalendar = (Calendar)paramCalendar1.clone();
    for (long l = 0L; ; l += 1L)
    {
      if (!localCalendar.before(paramCalendar2))
        return l;
      localCalendar.add(5, 1);
    }
  }

  public static int getCartCount()
  {
    return cartCount;
  }

  public static long getCartTime()
  {
    return cartTime;
  }

  public static FabHelper getFabHelper()
  {
    return new FabHelper();
  }

  public static FabFBClient linkToFacebook(Context paramContext, Handler paramHandler)
  {
    FabFBClient localFabFBClient = new FabFBClient(paramContext, true);
    localFabFBClient.setmHandler(paramHandler);
    localFabFBClient.linkToFaceBook();
    return localFabFBClient;
  }

  public static void logoutFromFB(Context paramContext, Handler paramHandler)
  {
    FabFBClient localFabFBClient = new FabFBClient(paramContext);
    localFabFBClient.setmHandler(paramHandler);
    localFabFBClient.removeFBCredentials();
  }

  public static void onLoginComplete(JSONObject paramJSONObject, Context paramContext)
  {
    FabSharedPrefs.setGuestUser(false);
    FabDBAdapter localFabDBAdapter = new FabDBAdapter(paramContext);
    localFabDBAdapter.open();
    if (!TextUtils.isEmpty(FabSharedPrefs.getFabCookies()));
    try
    {
      localFabDBAdapter.insertUserInfo(paramJSONObject.getInt("id"), paramJSONObject.getString("username"), FabSharedPrefs.getFabCookies(), paramJSONObject.getString("email"));
      localFabDBAdapter.close();
      FabUtils.setAlarmForPullNotification(paramContext, 0L);
      FabUtils.setAlarm(paramContext);
      return;
    }
    catch (JSONException localJSONException)
    {
      while (true)
        localJSONException.printStackTrace();
    }
  }

  /** @deprecated */
  // ERROR //
  public static void sendRequestAndSetAlarmForNotifications(Context paramContext)
  {
    // Byte code:
    //   0: ldc 2
    //   2: monitorenter
    //   3: ldc2_w 84
    //   6: lstore_1
    //   7: invokestatic 188	com/fab/connection/FabHttpConnection:getDefaultClient	()Lcom/fab/connection/FabHttpConnection;
    //   10: astore 4
    //   12: aload 4
    //   14: iconst_0
    //   15: ldc 190
    //   17: ldc 192
    //   19: invokestatic 196	com/fab/utils/FabUtils:requestUrl	(ZLjava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   22: aconst_null
    //   23: aconst_null
    //   24: ldc 198
    //   26: invokevirtual 202	com/fab/connection/FabHttpConnection:executeRequest	(Ljava/lang/String;Ljava/util/HashMap;Ljava/util/HashMap;Ljava/lang/String;)Ljava/io/InputStream;
    //   29: invokestatic 208	com/fab/utils/FabParser:parseJSONResponse	(Ljava/io/InputStream;)Lorg/json/JSONObject;
    //   32: astore 8
    //   34: aload 8
    //   36: ifnull +167 -> 203
    //   39: aload 8
    //   41: ldc 210
    //   43: invokevirtual 158	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   46: invokestatic 144	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   49: pop
    //   50: aload 8
    //   52: ldc 212
    //   54: invokevirtual 216	org/json/JSONObject:getLong	(Ljava/lang/String;)J
    //   57: lstore_1
    //   58: invokestatic 220	java/util/Calendar:getInstance	()Ljava/util/Calendar;
    //   61: astore 62
    //   63: aload 62
    //   65: ldc2_w 221
    //   68: lload_1
    //   69: lmul
    //   70: invokevirtual 226	java/util/Calendar:setTimeInMillis	(J)V
    //   73: aload 62
    //   75: invokevirtual 230	java/util/Calendar:getTime	()Ljava/util/Date;
    //   78: pop
    //   79: aload_0
    //   80: invokestatic 233	com/fab/utils/FabSharedPrefs:initializePreferences	(Landroid/content/Context;)V
    //   83: iconst_0
    //   84: invokestatic 236	com/fab/utils/FabSharedPrefs:setPollingRequired	(Z)V
    //   87: ldc2_w 221
    //   90: lload_1
    //   91: lmul
    //   92: invokestatic 241	java/lang/System:currentTimeMillis	()J
    //   95: lcmp
    //   96: ifle +86 -> 182
    //   99: aload 8
    //   101: ldc 243
    //   103: invokevirtual 247	org/json/JSONObject:getJSONArray	(Ljava/lang/String;)Lorg/json/JSONArray;
    //   106: astore 15
    //   108: aconst_null
    //   109: astore 16
    //   111: ldc2_w 84
    //   114: lstore 17
    //   116: ldc 192
    //   118: astore 19
    //   120: iconst_0
    //   121: istore 20
    //   123: iconst_0
    //   124: istore 21
    //   126: iconst_0
    //   127: istore 22
    //   129: ldc 192
    //   131: astore 23
    //   133: iconst_0
    //   134: istore 24
    //   136: ldc 192
    //   138: astore 25
    //   140: iconst_0
    //   141: istore 26
    //   143: ldc 192
    //   145: astore 27
    //   147: ldc 192
    //   149: astore 28
    //   151: iconst_0
    //   152: istore 29
    //   154: iconst_0
    //   155: istore 30
    //   157: aload_0
    //   158: invokestatic 250	com/fab/utils/FabUtils:cancelAlarmForSaleNotification	(Landroid/content/Context;)V
    //   161: aload_0
    //   162: invokestatic 253	com/fab/utils/FabUtils:cancelAlarmForSpecialNotification	(Landroid/content/Context;)V
    //   165: iconst_0
    //   166: istore 31
    //   168: aload 15
    //   170: invokevirtual 258	org/json/JSONArray:length	()I
    //   173: istore 32
    //   175: iload 31
    //   177: iload 32
    //   179: if_icmplt +28 -> 207
    //   182: ldc2_w 221
    //   185: lload_1
    //   186: lmul
    //   187: lstore 12
    //   189: lload 12
    //   191: invokestatic 241	java/lang/System:currentTimeMillis	()J
    //   194: lcmp
    //   195: ifle +344 -> 539
    //   198: aload_0
    //   199: lload_1
    //   200: invokestatic 173	com/fab/utils/FabUtils:setAlarmForPullNotification	(Landroid/content/Context;J)V
    //   203: ldc 2
    //   205: monitorexit
    //   206: return
    //   207: aload 15
    //   209: iload 31
    //   211: invokevirtual 262	org/json/JSONArray:getJSONObject	(I)Lorg/json/JSONObject;
    //   214: astore 61
    //   216: aload 61
    //   218: astore 16
    //   220: aload 16
    //   222: ifnull +460 -> 682
    //   225: aload 16
    //   227: ldc_w 264
    //   230: invokevirtual 158	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   233: astore 60
    //   235: aload 60
    //   237: astore 23
    //   239: aload 16
    //   241: ldc_w 266
    //   244: invokevirtual 216	org/json/JSONObject:getLong	(Ljava/lang/String;)J
    //   247: lstore 58
    //   249: lload 58
    //   251: lstore 17
    //   253: aload 16
    //   255: ldc_w 268
    //   258: invokevirtual 158	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   261: astore 57
    //   263: aload 57
    //   265: astore 19
    //   267: aload 16
    //   269: ldc_w 270
    //   272: invokevirtual 152	org/json/JSONObject:getInt	(Ljava/lang/String;)I
    //   275: istore 56
    //   277: iload 56
    //   279: istore 20
    //   281: aload 16
    //   283: ldc_w 272
    //   286: invokevirtual 152	org/json/JSONObject:getInt	(Ljava/lang/String;)I
    //   289: istore 55
    //   291: iload 55
    //   293: istore 21
    //   295: aload 16
    //   297: ldc_w 274
    //   300: invokevirtual 152	org/json/JSONObject:getInt	(Ljava/lang/String;)I
    //   303: istore 54
    //   305: iload 54
    //   307: istore 22
    //   309: aload 16
    //   311: ldc_w 276
    //   314: invokevirtual 152	org/json/JSONObject:getInt	(Ljava/lang/String;)I
    //   317: istore 53
    //   319: iload 53
    //   321: istore 24
    //   323: aload 16
    //   325: ldc_w 278
    //   328: invokevirtual 158	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   331: astore 52
    //   333: aload 52
    //   335: astore 25
    //   337: aload 16
    //   339: ldc_w 280
    //   342: invokevirtual 152	org/json/JSONObject:getInt	(Ljava/lang/String;)I
    //   345: istore 51
    //   347: iload 51
    //   349: istore 26
    //   351: aload 16
    //   353: ldc_w 282
    //   356: invokevirtual 158	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   359: astore 50
    //   361: aload 50
    //   363: astore 27
    //   365: aload 16
    //   367: ldc_w 284
    //   370: invokevirtual 158	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   373: astore 49
    //   375: aload 49
    //   377: astore 28
    //   379: aload 16
    //   381: ldc_w 286
    //   384: invokevirtual 152	org/json/JSONObject:getInt	(Ljava/lang/String;)I
    //   387: istore 48
    //   389: iload 48
    //   391: istore 29
    //   393: aload 16
    //   395: ldc_w 288
    //   398: invokevirtual 152	org/json/JSONObject:getInt	(Ljava/lang/String;)I
    //   401: istore 47
    //   403: iload 47
    //   405: istore 30
    //   407: iload 22
    //   409: iconst_2
    //   410: if_icmpne +36 -> 446
    //   413: lload 17
    //   415: ldc2_w 84
    //   418: lcmp
    //   419: ifle +18 -> 437
    //   422: aload_0
    //   423: aload 25
    //   425: lload 17
    //   427: aload 19
    //   429: iload 24
    //   431: invokestatic 292	com/fab/utils/FabUtils:setAlarmForWeeklyShopNotification	(Landroid/content/Context;Ljava/lang/String;JLjava/lang/String;I)V
    //   434: goto +248 -> 682
    //   437: aload_0
    //   438: aload 25
    //   440: invokestatic 296	com/fab/utils/FabUtils:cancelAlarmForWeeklyShopNotification	(Landroid/content/Context;Ljava/lang/String;)V
    //   443: goto +239 -> 682
    //   446: aload_0
    //   447: lload 17
    //   449: aload 23
    //   451: aload 19
    //   453: iload 20
    //   455: iload 21
    //   457: iload 22
    //   459: iload 26
    //   461: iload 24
    //   463: aload 27
    //   465: aload 28
    //   467: iload 29
    //   469: iload 30
    //   471: invokestatic 300	com/fab/utils/FabUtils:setAlarmForSaleNotification	(Landroid/content/Context;JLjava/lang/String;Ljava/lang/String;IIIIILjava/lang/String;Ljava/lang/String;II)V
    //   474: goto +208 -> 682
    //   477: astore 6
    //   479: new 302	java/util/GregorianCalendar
    //   482: dup
    //   483: invokespecial 303	java/util/GregorianCalendar:<init>	()V
    //   486: astore 7
    //   488: aload 7
    //   490: iconst_5
    //   491: iconst_1
    //   492: invokevirtual 304	java/util/GregorianCalendar:add	(II)V
    //   495: aload 7
    //   497: bipush 11
    //   499: iconst_0
    //   500: invokevirtual 307	java/util/GregorianCalendar:set	(II)V
    //   503: aload 7
    //   505: bipush 12
    //   507: iconst_0
    //   508: invokevirtual 307	java/util/GregorianCalendar:set	(II)V
    //   511: aload 7
    //   513: bipush 13
    //   515: iconst_0
    //   516: invokevirtual 307	java/util/GregorianCalendar:set	(II)V
    //   519: aload_0
    //   520: aload 7
    //   522: invokevirtual 310	java/util/GregorianCalendar:getTimeInMillis	()J
    //   525: invokestatic 173	com/fab/utils/FabUtils:setAlarmForPullNotification	(Landroid/content/Context;J)V
    //   528: aload_0
    //   529: invokestatic 233	com/fab/utils/FabSharedPrefs:initializePreferences	(Landroid/content/Context;)V
    //   532: iconst_1
    //   533: invokestatic 236	com/fab/utils/FabSharedPrefs:setPollingRequired	(Z)V
    //   536: goto -333 -> 203
    //   539: new 302	java/util/GregorianCalendar
    //   542: dup
    //   543: invokespecial 303	java/util/GregorianCalendar:<init>	()V
    //   546: astore 14
    //   548: aload 14
    //   550: iconst_5
    //   551: iconst_1
    //   552: invokevirtual 304	java/util/GregorianCalendar:add	(II)V
    //   555: aload 14
    //   557: bipush 11
    //   559: iconst_0
    //   560: invokevirtual 307	java/util/GregorianCalendar:set	(II)V
    //   563: aload 14
    //   565: bipush 12
    //   567: iconst_0
    //   568: invokevirtual 307	java/util/GregorianCalendar:set	(II)V
    //   571: aload 14
    //   573: bipush 13
    //   575: iconst_0
    //   576: invokevirtual 307	java/util/GregorianCalendar:set	(II)V
    //   579: aload_0
    //   580: aload 14
    //   582: invokevirtual 310	java/util/GregorianCalendar:getTimeInMillis	()J
    //   585: invokestatic 173	com/fab/utils/FabUtils:setAlarmForPullNotification	(Landroid/content/Context;J)V
    //   588: goto -385 -> 203
    //   591: astore 5
    //   593: aload 5
    //   595: athrow
    //   596: astore_3
    //   597: ldc 2
    //   599: monitorexit
    //   600: aload_3
    //   601: athrow
    //   602: astore 33
    //   604: goto -384 -> 220
    //   607: astore 46
    //   609: goto -202 -> 407
    //   612: astore 45
    //   614: goto -221 -> 393
    //   617: astore 44
    //   619: goto -240 -> 379
    //   622: astore 43
    //   624: goto -259 -> 365
    //   627: astore 42
    //   629: goto -278 -> 351
    //   632: astore 41
    //   634: goto -297 -> 337
    //   637: astore 40
    //   639: goto -316 -> 323
    //   642: astore 39
    //   644: goto -335 -> 309
    //   647: astore 38
    //   649: goto -354 -> 295
    //   652: astore 37
    //   654: goto -373 -> 281
    //   657: astore 36
    //   659: goto -392 -> 267
    //   662: astore 35
    //   664: goto -411 -> 253
    //   667: astore 34
    //   669: goto -430 -> 239
    //   672: astore 10
    //   674: goto -595 -> 79
    //   677: astore 9
    //   679: goto -629 -> 50
    //   682: iinc 31 1
    //   685: goto -510 -> 175
    //   688: astore 11
    //   690: goto -508 -> 182
    //
    // Exception table:
    //   from	to	target	type
    //   12	34	477	com/fab/utils/exception/FabException
    //   39	50	477	com/fab/utils/exception/FabException
    //   50	79	477	com/fab/utils/exception/FabException
    //   79	175	477	com/fab/utils/exception/FabException
    //   189	203	477	com/fab/utils/exception/FabException
    //   207	216	477	com/fab/utils/exception/FabException
    //   225	235	477	com/fab/utils/exception/FabException
    //   239	249	477	com/fab/utils/exception/FabException
    //   253	263	477	com/fab/utils/exception/FabException
    //   267	277	477	com/fab/utils/exception/FabException
    //   281	291	477	com/fab/utils/exception/FabException
    //   295	305	477	com/fab/utils/exception/FabException
    //   309	319	477	com/fab/utils/exception/FabException
    //   323	333	477	com/fab/utils/exception/FabException
    //   337	347	477	com/fab/utils/exception/FabException
    //   351	361	477	com/fab/utils/exception/FabException
    //   365	375	477	com/fab/utils/exception/FabException
    //   379	389	477	com/fab/utils/exception/FabException
    //   393	403	477	com/fab/utils/exception/FabException
    //   422	474	477	com/fab/utils/exception/FabException
    //   539	588	477	com/fab/utils/exception/FabException
    //   12	34	591	finally
    //   39	50	591	finally
    //   50	79	591	finally
    //   79	175	591	finally
    //   189	203	591	finally
    //   207	216	591	finally
    //   225	235	591	finally
    //   239	249	591	finally
    //   253	263	591	finally
    //   267	277	591	finally
    //   281	291	591	finally
    //   295	305	591	finally
    //   309	319	591	finally
    //   323	333	591	finally
    //   337	347	591	finally
    //   351	361	591	finally
    //   365	375	591	finally
    //   379	389	591	finally
    //   393	403	591	finally
    //   422	474	591	finally
    //   479	536	591	finally
    //   539	588	591	finally
    //   7	12	596	finally
    //   593	596	596	finally
    //   207	216	602	org/json/JSONException
    //   393	403	607	org/json/JSONException
    //   379	389	612	org/json/JSONException
    //   365	375	617	org/json/JSONException
    //   351	361	622	org/json/JSONException
    //   337	347	627	org/json/JSONException
    //   323	333	632	org/json/JSONException
    //   309	319	637	org/json/JSONException
    //   295	305	642	org/json/JSONException
    //   281	291	647	org/json/JSONException
    //   267	277	652	org/json/JSONException
    //   253	263	657	org/json/JSONException
    //   239	249	662	org/json/JSONException
    //   225	235	667	org/json/JSONException
    //   50	79	672	org/json/JSONException
    //   39	50	677	org/json/JSONException
    //   79	175	688	org/json/JSONException
    //   422	474	688	org/json/JSONException
  }

  public static void setCartCount(int paramInt)
  {
    cartCount = paramInt;
  }

  public static void setCartTime(long paramLong)
  {
    cartTime = paramLong;
  }

  public static void setDeviceParamsForPost(HashMap<String, String> paramHashMap)
  {
    paramHashMap.put("d", FabSharedPrefs.getDeviceDensity());
    paramHashMap.put("app_version", FabApplication.APP_VERSION);
    paramHashMap.put("device_model", Build.MODEL);
    paramHashMap.put("device_os", Build.VERSION.RELEASE);
  }

  public static void smoothScroll(ListView paramListView)
  {
    try
    {
      Class localClass = paramListView.getClass();
      Class[] arrayOfClass = new Class[1];
      arrayOfClass[0] = Integer.TYPE;
      Method localMethod = localClass.getMethod("smoothScrollToPosition", arrayOfClass);
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = Integer.valueOf(1);
      localMethod.invoke(paramListView, arrayOfObject);
      label54: return;
    }
    catch (NoSuchMethodError localNoSuchMethodError)
    {
      while (true)
        paramListView.setSelection(0);
    }
    catch (NoSuchMethodException localNoSuchMethodException)
    {
      while (true)
        paramListView.setSelection(1);
    }
    catch (InvocationTargetException localInvocationTargetException)
    {
      break label54;
    }
    catch (IllegalAccessException localIllegalAccessException)
    {
      break label54;
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
      break label54;
    }
    catch (SecurityException localSecurityException)
    {
      break label54;
    }
  }

  public static void stopAndRestartCartTimer(Context paramContext, long paramLong, Handler paramHandler)
  {
    cartTime = paramLong;
    if ((timerTask == null) || (timerTaskCompleted))
    {
      timerTask = new TimerTask(paramContext, paramHandler, paramLong);
      timerTask.execute(new Void[0]);
    }
    while (true)
    {
      return;
      timerTask.setHandler(paramHandler);
      timerTask.setTime(paramLong);
    }
  }

  public static void stopCartTimer()
  {
    if (timerTask != null)
      timerTask.cancel(true);
  }

  public void clearSDCard()
  {
    FabUtils.log(this.tag, "clearing SD Card");
    if (isSDCardAvailable())
    {
      Calendar localCalendar1 = Calendar.getInstance();
      Calendar localCalendar2 = Calendar.getInstance();
      int i;
      int j;
      do
        try
        {
          File localFile1 = new File(Environment.getExternalStorageDirectory(), "/Android/data/com.fab/.Fab");
          if ((!localFile1.exists()) || (localFile1.listFiles() == null))
            break;
          File[] arrayOfFile = localFile1.listFiles();
          i = arrayOfFile.length;
          j = 0;
          continue;
          File localFile2 = arrayOfFile[j];
          localCalendar2.setTimeInMillis(localFile2.lastModified());
          if (daysBetween(localCalendar2, localCalendar1) >= 3L)
            localFile2.delete();
          j++;
        }
        catch (NullPointerException localNullPointerException)
        {
          break;
        }
      while (j < i);
    }
  }

  // ERROR //
  public android.graphics.Bitmap decodeFileForBitmap(String paramString)
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_2
    //   2: new 473	android/graphics/BitmapFactory$Options
    //   5: dup
    //   6: invokespecial 474	android/graphics/BitmapFactory$Options:<init>	()V
    //   9: astore_3
    //   10: aload_3
    //   11: iconst_0
    //   12: putfield 477	android/graphics/BitmapFactory$Options:inDither	Z
    //   15: aload_3
    //   16: iconst_1
    //   17: putfield 480	android/graphics/BitmapFactory$Options:inPurgeable	Z
    //   20: aload_3
    //   21: iconst_1
    //   22: putfield 483	android/graphics/BitmapFactory$Options:inInputShareable	Z
    //   25: aload_3
    //   26: ldc_w 484
    //   29: newarray byte
    //   31: putfield 488	android/graphics/BitmapFactory$Options:inTempStorage	[B
    //   34: new 437	java/io/File
    //   37: dup
    //   38: aload_1
    //   39: invokespecial 491	java/io/File:<init>	(Ljava/lang/String;)V
    //   42: astore 4
    //   44: aconst_null
    //   45: astore 5
    //   47: new 493	java/io/FileInputStream
    //   50: dup
    //   51: aload 4
    //   53: invokespecial 496	java/io/FileInputStream:<init>	(Ljava/io/File;)V
    //   56: astore 6
    //   58: aload 6
    //   60: astore 5
    //   62: aload 5
    //   64: ifnull +60 -> 124
    //   67: aload 5
    //   69: invokevirtual 500	java/io/FileInputStream:getFD	()Ljava/io/FileDescriptor;
    //   72: aconst_null
    //   73: aload_3
    //   74: invokestatic 506	android/graphics/BitmapFactory:decodeFileDescriptor	(Ljava/io/FileDescriptor;Landroid/graphics/Rect;Landroid/graphics/BitmapFactory$Options;)Landroid/graphics/Bitmap;
    //   77: astore 12
    //   79: aload 12
    //   81: astore_2
    //   82: aload 5
    //   84: ifnull +8 -> 92
    //   87: aload 5
    //   89: invokevirtual 507	java/io/FileInputStream:close	()V
    //   92: aload_2
    //   93: areturn
    //   94: astore 10
    //   96: aload 5
    //   98: ifnull -6 -> 92
    //   101: aload 5
    //   103: invokevirtual 507	java/io/FileInputStream:close	()V
    //   106: goto -14 -> 92
    //   109: astore 8
    //   111: aload 5
    //   113: ifnull +8 -> 121
    //   116: aload 5
    //   118: invokevirtual 507	java/io/FileInputStream:close	()V
    //   121: aload 8
    //   123: athrow
    //   124: aload 5
    //   126: ifnull -34 -> 92
    //   129: aload 5
    //   131: invokevirtual 507	java/io/FileInputStream:close	()V
    //   134: goto -42 -> 92
    //   137: astore 13
    //   139: goto -47 -> 92
    //   142: astore 11
    //   144: goto -52 -> 92
    //   147: astore 9
    //   149: goto -28 -> 121
    //   152: astore 7
    //   154: goto -62 -> 92
    //   157: astore 14
    //   159: goto -97 -> 62
    //
    // Exception table:
    //   from	to	target	type
    //   67	79	94	java/io/IOException
    //   67	79	109	finally
    //   87	92	137	java/io/IOException
    //   101	106	142	java/io/IOException
    //   116	121	147	java/io/IOException
    //   129	134	152	java/io/IOException
    //   47	58	157	java/io/FileNotFoundException
  }

  public String doesFileExists(Context paramContext, String paramString)
  {
    String str;
    if (paramString != null)
      try
      {
        File localFile1;
        if (isSDCardAvailable())
        {
          localFile1 = new File(Environment.getExternalStorageDirectory(), "/Android/data/com.fab/.Fab");
          if (!localFile1.exists())
            localFile1.mkdirs();
          File localFile2 = new File(localFile1, paramString.substring(1 + paramString.lastIndexOf("/")));
          if (localFile2.exists())
          {
            FabUtils.log("FABHELPER", localFile2.getAbsolutePath(), "I");
            str = localFile2.getAbsolutePath();
            break label122;
          }
        }
        else
        {
          localFile1 = new File(paramContext.getCacheDir(), ".Fab");
        }
      }
      finally
      {
      }
    else
      str = "";
    label122: return str;
  }

  public void forceClearSDCard()
  {
    FabUtils.log(this.tag, "clearing SD Card");
    File[] arrayOfFile;
    int i;
    if (isSDCardAvailable())
    {
      File localFile = new File(Environment.getExternalStorageDirectory(), "/Android/data/com.fab/.Fab");
      if (localFile.exists())
      {
        arrayOfFile = localFile.listFiles();
        i = arrayOfFile.length;
      }
    }
    for (int j = 0; ; j++)
    {
      if (j >= i)
        return;
      arrayOfFile[j].delete();
    }
  }

  public long getReservedTime()
  {
    return this.reservedTime;
  }

  public boolean isSDCardAvailable()
  {
    if ("mounted".equalsIgnoreCase(Environment.getExternalStorageState()));
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  public void setReservedTime(long paramLong)
  {
    this.reservedTime = paramLong;
  }

  // ERROR //
  public File writeToCache(Context paramContext, java.io.InputStream paramInputStream, String paramString)
  {
    // Byte code:
    //   0: aload_0
    //   1: invokevirtual 435	com/fab/utils/FabHelper:isSDCardAvailable	()Z
    //   4: ifeq +111 -> 115
    //   7: new 437	java/io/File
    //   10: dup
    //   11: invokestatic 443	android/os/Environment:getExternalStorageDirectory	()Ljava/io/File;
    //   14: ldc_w 445
    //   17: invokespecial 448	java/io/File:<init>	(Ljava/io/File;Ljava/lang/String;)V
    //   20: astore 4
    //   22: aload 4
    //   24: invokevirtual 451	java/io/File:exists	()Z
    //   27: ifne +9 -> 36
    //   30: aload 4
    //   32: invokevirtual 512	java/io/File:mkdirs	()Z
    //   35: pop
    //   36: new 437	java/io/File
    //   39: dup
    //   40: aload 4
    //   42: aload_3
    //   43: iconst_1
    //   44: aload_3
    //   45: ldc_w 514
    //   48: invokevirtual 519	java/lang/String:lastIndexOf	(Ljava/lang/String;)I
    //   51: iadd
    //   52: invokevirtual 523	java/lang/String:substring	(I)Ljava/lang/String;
    //   55: invokespecial 448	java/io/File:<init>	(Ljava/io/File;Ljava/lang/String;)V
    //   58: astore 5
    //   60: aconst_null
    //   61: astore 6
    //   63: sipush 20480
    //   66: newarray byte
    //   68: astore 7
    //   70: new 557	java/io/FileOutputStream
    //   73: dup
    //   74: aload 5
    //   76: invokespecial 558	java/io/FileOutputStream:<init>	(Ljava/io/File;)V
    //   79: astore 8
    //   81: aload_2
    //   82: aload 7
    //   84: invokevirtual 564	java/io/InputStream:read	([B)I
    //   87: istore 20
    //   89: iload 20
    //   91: ifgt +43 -> 134
    //   94: aload 8
    //   96: ifnull +8 -> 104
    //   99: aload 8
    //   101: invokevirtual 565	java/io/FileOutputStream:close	()V
    //   104: aconst_null
    //   105: checkcast 566	[B
    //   108: pop
    //   109: invokestatic 569	java/lang/System:gc	()V
    //   112: aload 5
    //   114: areturn
    //   115: new 437	java/io/File
    //   118: dup
    //   119: aload_1
    //   120: invokevirtual 537	android/content/Context:getCacheDir	()Ljava/io/File;
    //   123: ldc_w 539
    //   126: invokespecial 448	java/io/File:<init>	(Ljava/io/File;Ljava/lang/String;)V
    //   129: astore 4
    //   131: goto -109 -> 22
    //   134: aload 8
    //   136: aload 7
    //   138: iconst_0
    //   139: iload 20
    //   141: invokevirtual 573	java/io/FileOutputStream:write	([BII)V
    //   144: goto -63 -> 81
    //   147: astore 13
    //   149: aload 8
    //   151: astore 6
    //   153: aload 13
    //   155: invokevirtual 576	java/io/IOException:getMessage	()Ljava/lang/String;
    //   158: ldc_w 578
    //   161: invokevirtual 581	java/lang/String:contains	(Ljava/lang/CharSequence;)Z
    //   164: ifeq +33 -> 197
    //   167: new 583	android/content/Intent
    //   170: dup
    //   171: aload_1
    //   172: ldc_w 585
    //   175: invokespecial 588	android/content/Intent:<init>	(Landroid/content/Context;Ljava/lang/Class;)V
    //   178: astore 15
    //   180: aload 15
    //   182: ldc_w 590
    //   185: iconst_1
    //   186: invokevirtual 594	android/content/Intent:putExtra	(Ljava/lang/String;Z)Landroid/content/Intent;
    //   189: pop
    //   190: aload_1
    //   191: aload 15
    //   193: invokevirtual 598	android/content/Context:startService	(Landroid/content/Intent;)Landroid/content/ComponentName;
    //   196: pop
    //   197: aload 6
    //   199: ifnull +8 -> 207
    //   202: aload 6
    //   204: invokevirtual 565	java/io/FileOutputStream:close	()V
    //   207: aconst_null
    //   208: checkcast 566	[B
    //   211: pop
    //   212: invokestatic 569	java/lang/System:gc	()V
    //   215: goto -103 -> 112
    //   218: astore 14
    //   220: aload 14
    //   222: astore 10
    //   224: aload 6
    //   226: ifnull +8 -> 234
    //   229: aload 6
    //   231: invokevirtual 565	java/io/FileOutputStream:close	()V
    //   234: aconst_null
    //   235: checkcast 566	[B
    //   238: pop
    //   239: invokestatic 569	java/lang/System:gc	()V
    //   242: aload 10
    //   244: athrow
    //   245: astore 21
    //   247: goto -138 -> 109
    //   250: astore 11
    //   252: goto -13 -> 239
    //   255: astore 9
    //   257: aload 9
    //   259: astore 10
    //   261: aload 8
    //   263: astore 6
    //   265: goto -41 -> 224
    //   268: astore 18
    //   270: goto -58 -> 212
    //   273: astore 13
    //   275: goto -122 -> 153
    //
    // Exception table:
    //   from	to	target	type
    //   81	89	147	java/io/IOException
    //   134	144	147	java/io/IOException
    //   70	81	218	finally
    //   153	197	218	finally
    //   99	109	245	java/io/IOException
    //   229	239	250	java/io/IOException
    //   81	89	255	finally
    //   134	144	255	finally
    //   202	212	268	java/io/IOException
    //   70	81	273	java/io/IOException
  }
}

/* Location:           C:\Documents and Settings\Administrator\桌面\private\反编译\apk2java\dex2java\dex-translator-0.0.9.3\dex-translator-0.0.9.3\classes_dex2jar.jar
 * Qualified Name:     com.fab.utils.FabHelper
 * JD-Core Version:    0.6.2
 */