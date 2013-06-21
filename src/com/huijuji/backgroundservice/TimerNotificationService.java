package com.fab.backgroundservice;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import com.fab.utils.FabHelper;
import com.fab.webviews.FabWebViewActivity;

public class TimerNotificationService extends Service
{
  private static final String TAG = "Timer Notification Service";

  private void announceCartTimerExpiry(String paramString)
  {
    Notification localNotification = new Notification(2130837670, paramString, System.currentTimeMillis());
    localNotification.setLatestEventInfo(this, "Fab.com", paramString, PendingIntent.getActivity(this, 0, new Intent(this, FabWebViewActivity.class), 0));
    localNotification.defaults = (0x1 | localNotification.defaults);
    localNotification.defaults = (0x2 | localNotification.defaults);
    localNotification.defaults = (0x4 | localNotification.defaults);
    ((NotificationManager)getSystemService("notification")).notify(2131165198, localNotification);
  }

  public void handleCommand(Intent paramIntent, final int paramInt)
  {
    if (FabHelper.getCartTime() != 0L)
      new Thread(new Runnable()
      {
        // ERROR //
        public void run()
        {
          // Byte code:
          //   0: new 34	java/util/GregorianCalendar
          //   3: dup
          //   4: invokespecial 35	java/util/GregorianCalendar:<init>	()V
          //   7: astore_1
          //   8: invokestatic 41	com/fab/utils/FabHelper:getCartTime	()J
          //   11: aload_1
          //   12: invokestatic 47	com/fab/utils/FabUtils:getSecondsLeftForTimer	(JLjava/util/Calendar;)J
          //   15: lstore_3
          //   16: lload_3
          //   17: ldc2_w 48
          //   20: lcmp
          //   21: ifge +25 -> 46
          //   24: aload_0
          //   25: getfield 20	com/fab/backgroundservice/TimerNotificationService$1:this$0	Lcom/fab/backgroundservice/TimerNotificationService;
          //   28: aload_0
          //   29: getfield 22	com/fab/backgroundservice/TimerNotificationService$1:val$startId	I
          //   32: invokevirtual 53	com/fab/backgroundservice/TimerNotificationService:stopSelf	(I)V
          //   35: aload_0
          //   36: getfield 20	com/fab/backgroundservice/TimerNotificationService$1:this$0	Lcom/fab/backgroundservice/TimerNotificationService;
          //   39: invokevirtual 55	com/fab/backgroundservice/TimerNotificationService:stopSelf	()V
          //   42: invokestatic 60	java/lang/System:gc	()V
          //   45: return
          //   46: invokestatic 41	com/fab/utils/FabHelper:getCartTime	()J
          //   49: aload_1
          //   50: invokestatic 47	com/fab/utils/FabUtils:getSecondsLeftForTimer	(JLjava/util/Calendar;)J
          //   53: ldc2_w 61
          //   56: lcmp
          //   57: ifne +84 -> 141
          //   60: invokestatic 68	com/fab/connection/FabHttpConnection:getDefaultClient	()Lcom/fab/connection/FabHttpConnection;
          //   63: astore 6
          //   65: aload 6
          //   67: iconst_0
          //   68: ldc 70
          //   70: ldc 72
          //   72: invokestatic 76	com/fab/utils/FabUtils:requestUrl	(ZLjava/lang/String;Ljava/lang/String;)Ljava/lang/String;
          //   75: aconst_null
          //   76: aconst_null
          //   77: ldc 78
          //   79: invokevirtual 82	com/fab/connection/FabHttpConnection:executeRequest	(Ljava/lang/String;Ljava/util/HashMap;Ljava/util/HashMap;Ljava/lang/String;)Ljava/io/InputStream;
          //   82: invokestatic 88	com/fab/utils/FabParser:parseJSONResponse	(Ljava/io/InputStream;)Lorg/json/JSONObject;
          //   85: astore 9
          //   87: aload 9
          //   89: ifnull +52 -> 141
          //   92: aload 9
          //   94: ldc 90
          //   96: invokevirtual 96	org/json/JSONObject:getJSONObject	(Ljava/lang/String;)Lorg/json/JSONObject;
          //   99: astore 11
          //   101: aload 11
          //   103: ldc 98
          //   105: invokevirtual 102	org/json/JSONObject:getInt	(Ljava/lang/String;)I
          //   108: invokestatic 105	com/fab/utils/FabHelper:setCartCount	(I)V
          //   111: aload 11
          //   113: ldc 107
          //   115: invokevirtual 102	org/json/JSONObject:getInt	(Ljava/lang/String;)I
          //   118: i2l
          //   119: aload_1
          //   120: invokestatic 47	com/fab/utils/FabUtils:getSecondsLeftForTimer	(JLjava/util/Calendar;)J
          //   123: ldc2_w 48
          //   126: lcmp
          //   127: iflt +14 -> 141
          //   130: aload 11
          //   132: ldc 107
          //   134: invokevirtual 102	org/json/JSONObject:getInt	(Ljava/lang/String;)I
          //   137: i2l
          //   138: invokestatic 111	com/fab/utils/FabHelper:setCartTime	(J)V
          //   141: invokestatic 41	com/fab/utils/FabHelper:getCartTime	()J
          //   144: aload_1
          //   145: invokestatic 47	com/fab/utils/FabUtils:getSecondsLeftForTimer	(JLjava/util/Calendar;)J
          //   148: ldc2_w 112
          //   151: lcmp
          //   152: ifne +60 -> 212
          //   155: aload_0
          //   156: getfield 20	com/fab/backgroundservice/TimerNotificationService$1:this$0	Lcom/fab/backgroundservice/TimerNotificationService;
          //   159: ldc 115
          //   161: invokestatic 119	com/fab/backgroundservice/TimerNotificationService:access$0	(Lcom/fab/backgroundservice/TimerNotificationService;Ljava/lang/String;)V
          //   164: ldc2_w 120
          //   167: invokestatic 126	java/lang/Thread:sleep	(J)V
          //   170: goto -162 -> 8
          //   173: astore 5
          //   175: goto -167 -> 8
          //   178: astore 8
          //   180: goto -39 -> 141
          //   183: astore 7
          //   185: aload 7
          //   187: athrow
          //   188: astore_2
          //   189: aload_0
          //   190: getfield 20	com/fab/backgroundservice/TimerNotificationService$1:this$0	Lcom/fab/backgroundservice/TimerNotificationService;
          //   193: aload_0
          //   194: getfield 22	com/fab/backgroundservice/TimerNotificationService$1:val$startId	I
          //   197: invokevirtual 53	com/fab/backgroundservice/TimerNotificationService:stopSelf	(I)V
          //   200: aload_0
          //   201: getfield 20	com/fab/backgroundservice/TimerNotificationService$1:this$0	Lcom/fab/backgroundservice/TimerNotificationService;
          //   204: invokevirtual 55	com/fab/backgroundservice/TimerNotificationService:stopSelf	()V
          //   207: invokestatic 60	java/lang/System:gc	()V
          //   210: aload_2
          //   211: athrow
          //   212: invokestatic 41	com/fab/utils/FabHelper:getCartTime	()J
          //   215: aload_1
          //   216: invokestatic 47	com/fab/utils/FabUtils:getSecondsLeftForTimer	(JLjava/util/Calendar;)J
          //   219: ldc2_w 48
          //   222: lcmp
          //   223: ifne -59 -> 164
          //   226: aload_0
          //   227: getfield 20	com/fab/backgroundservice/TimerNotificationService$1:this$0	Lcom/fab/backgroundservice/TimerNotificationService;
          //   230: ldc 128
          //   232: invokestatic 119	com/fab/backgroundservice/TimerNotificationService:access$0	(Lcom/fab/backgroundservice/TimerNotificationService;Ljava/lang/String;)V
          //   235: goto -71 -> 164
          //   238: astore 10
          //   240: goto -99 -> 141
          //
          // Exception table:
          //   from	to	target	type
          //   164	170	173	java/lang/InterruptedException
          //   65	87	178	com/fab/utils/exception/FabException
          //   92	141	178	com/fab/utils/exception/FabException
          //   65	87	183	finally
          //   92	141	183	finally
          //   0	16	188	finally
          //   46	65	188	finally
          //   141	164	188	finally
          //   164	170	188	finally
          //   185	188	188	finally
          //   212	235	188	finally
          //   92	141	238	org/json/JSONException
        }
      }).start();
  }

  public IBinder onBind(Intent paramIntent)
  {
    return null;
  }

  public void onCreate()
  {
    super.onCreate();
    Log.i("Timer Notification Service", "Timer Notification Service Started");
  }

  public void onStart(Intent paramIntent, int paramInt)
  {
    handleCommand(paramIntent, paramInt);
  }

  public int onStartCommand(Intent paramIntent, int paramInt1, int paramInt2)
  {
    handleCommand(paramIntent, paramInt2);
    return 1;
  }
}

/* Location:           C:\Documents and Settings\Administrator\桌面\private\反编译\apk2java\dex2java\dex-translator-0.0.9.3\dex-translator-0.0.9.3\classes_dex2jar.jar
 * Qualified Name:     com.fab.backgroundservice.TimerNotificationService
 * JD-Core Version:    0.6.2
 */