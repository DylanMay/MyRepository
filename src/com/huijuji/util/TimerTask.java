package com.fab.utils;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import com.fab.backgroundservice.TimerNotificationService;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class TimerTask extends AsyncTask<Void, Void, Void>
{
  private Calendar cal;
  private Context context;
  private Handler mHandler;
  private long time;

  public TimerTask(Context paramContext, Handler paramHandler, long paramLong)
  {
    this.mHandler = paramHandler;
    this.time = paramLong;
    this.context = paramContext;
    this.cal = new GregorianCalendar();
  }

  // ERROR //
  protected Void doInBackground(Void[] paramArrayOfVoid)
  {
    // Byte code:
    //   0: aload_0
    //   1: invokevirtual 47	com/fab/utils/TimerTask:isCancelled	()Z
    //   4: ifne +21 -> 25
    //   7: aload_0
    //   8: getfield 23	com/fab/utils/TimerTask:time	J
    //   11: aload_0
    //   12: getfield 30	com/fab/utils/TimerTask:cal	Ljava/util/Calendar;
    //   15: invokestatic 53	com/fab/utils/FabUtils:getSecondsLeftForTimer	(JLjava/util/Calendar;)J
    //   18: ldc2_w 54
    //   21: lcmp
    //   22: ifge +5 -> 27
    //   25: aconst_null
    //   26: areturn
    //   27: iconst_0
    //   28: putstatic 61	com/fab/utils/FabHelper:timerTaskCompleted	Z
    //   31: new 63	android/os/Message
    //   34: dup
    //   35: invokespecial 64	android/os/Message:<init>	()V
    //   38: astore_2
    //   39: new 66	android/os/Bundle
    //   42: dup
    //   43: invokespecial 67	android/os/Bundle:<init>	()V
    //   46: astore_3
    //   47: aload_3
    //   48: ldc 69
    //   50: iconst_1
    //   51: invokevirtual 73	android/os/Bundle:putInt	(Ljava/lang/String;I)V
    //   54: aload_3
    //   55: ldc 75
    //   57: new 77	java/lang/StringBuilder
    //   60: dup
    //   61: aload_0
    //   62: getfield 23	com/fab/utils/TimerTask:time	J
    //   65: aload_0
    //   66: getfield 30	com/fab/utils/TimerTask:cal	Ljava/util/Calendar;
    //   69: invokestatic 81	com/fab/utils/FabUtils:getTimeLeft	(JLjava/util/Calendar;)Ljava/lang/String;
    //   72: invokestatic 87	java/lang/String:valueOf	(Ljava/lang/Object;)Ljava/lang/String;
    //   75: invokespecial 90	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   78: invokevirtual 94	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   81: invokevirtual 98	android/os/Bundle:putString	(Ljava/lang/String;Ljava/lang/String;)V
    //   84: aload_3
    //   85: ldc 100
    //   87: iconst_1
    //   88: invokevirtual 104	android/os/Bundle:putBoolean	(Ljava/lang/String;Z)V
    //   91: aload_2
    //   92: aload_3
    //   93: invokevirtual 108	android/os/Message:setData	(Landroid/os/Bundle;)V
    //   96: aload_0
    //   97: getfield 21	com/fab/utils/TimerTask:mHandler	Landroid/os/Handler;
    //   100: aload_2
    //   101: invokevirtual 114	android/os/Handler:sendMessage	(Landroid/os/Message;)Z
    //   104: pop
    //   105: aload_0
    //   106: getfield 23	com/fab/utils/TimerTask:time	J
    //   109: aload_0
    //   110: getfield 30	com/fab/utils/TimerTask:cal	Ljava/util/Calendar;
    //   113: invokestatic 53	com/fab/utils/FabUtils:getSecondsLeftForTimer	(JLjava/util/Calendar;)J
    //   116: ldc2_w 115
    //   119: lcmp
    //   120: ifne +106 -> 226
    //   123: invokestatic 122	com/fab/connection/FabHttpConnection:getDefaultClient	()Lcom/fab/connection/FabHttpConnection;
    //   126: astore 12
    //   128: aload 12
    //   130: iconst_0
    //   131: ldc 124
    //   133: ldc 126
    //   135: invokestatic 130	com/fab/utils/FabUtils:requestUrl	(ZLjava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   138: aconst_null
    //   139: aconst_null
    //   140: ldc 132
    //   142: invokevirtual 136	com/fab/connection/FabHttpConnection:executeRequest	(Ljava/lang/String;Ljava/util/HashMap;Ljava/util/HashMap;Ljava/lang/String;)Ljava/io/InputStream;
    //   145: invokestatic 142	com/fab/utils/FabParser:parseJSONResponse	(Ljava/io/InputStream;)Lorg/json/JSONObject;
    //   148: astore 15
    //   150: aload 15
    //   152: ifnull +74 -> 226
    //   155: aload 15
    //   157: ldc 144
    //   159: invokevirtual 150	org/json/JSONObject:getJSONObject	(Ljava/lang/String;)Lorg/json/JSONObject;
    //   162: astore 17
    //   164: aload 17
    //   166: ldc 152
    //   168: invokevirtual 156	org/json/JSONObject:getInt	(Ljava/lang/String;)I
    //   171: invokestatic 160	com/fab/utils/FabHelper:setCartCount	(I)V
    //   174: aload 17
    //   176: ldc 162
    //   178: invokevirtual 156	org/json/JSONObject:getInt	(Ljava/lang/String;)I
    //   181: i2l
    //   182: aload_0
    //   183: getfield 30	com/fab/utils/TimerTask:cal	Ljava/util/Calendar;
    //   186: invokestatic 53	com/fab/utils/FabUtils:getSecondsLeftForTimer	(JLjava/util/Calendar;)J
    //   189: ldc2_w 54
    //   192: lcmp
    //   193: iflt +33 -> 226
    //   196: aload 17
    //   198: ldc 162
    //   200: invokevirtual 156	org/json/JSONObject:getInt	(Ljava/lang/String;)I
    //   203: i2l
    //   204: invokestatic 166	com/fab/utils/FabHelper:setCartTime	(J)V
    //   207: aload_0
    //   208: getfield 25	com/fab/utils/TimerTask:context	Landroid/content/Context;
    //   211: aload 17
    //   213: ldc 162
    //   215: invokevirtual 156	org/json/JSONObject:getInt	(Ljava/lang/String;)I
    //   218: i2l
    //   219: aload_0
    //   220: getfield 21	com/fab/utils/TimerTask:mHandler	Landroid/os/Handler;
    //   223: invokestatic 170	com/fab/utils/FabHelper:stopAndRestartCartTimer	(Landroid/content/Context;JLandroid/os/Handler;)V
    //   226: aload_0
    //   227: getfield 23	com/fab/utils/TimerTask:time	J
    //   230: aload_0
    //   231: getfield 30	com/fab/utils/TimerTask:cal	Ljava/util/Calendar;
    //   234: invokestatic 53	com/fab/utils/FabUtils:getSecondsLeftForTimer	(JLjava/util/Calendar;)J
    //   237: ldc2_w 171
    //   240: lcmp
    //   241: ifne +96 -> 337
    //   244: new 63	android/os/Message
    //   247: dup
    //   248: invokespecial 64	android/os/Message:<init>	()V
    //   251: astore 5
    //   253: new 66	android/os/Bundle
    //   256: dup
    //   257: invokespecial 67	android/os/Bundle:<init>	()V
    //   260: astore 6
    //   262: aload 6
    //   264: ldc 69
    //   266: iconst_2
    //   267: invokevirtual 73	android/os/Bundle:putInt	(Ljava/lang/String;I)V
    //   270: aload 6
    //   272: ldc 174
    //   274: ldc 176
    //   276: invokevirtual 98	android/os/Bundle:putString	(Ljava/lang/String;Ljava/lang/String;)V
    //   279: aload 6
    //   281: ldc 178
    //   283: ldc 180
    //   285: invokevirtual 98	android/os/Bundle:putString	(Ljava/lang/String;Ljava/lang/String;)V
    //   288: aload 6
    //   290: ldc 182
    //   292: iconst_1
    //   293: invokevirtual 73	android/os/Bundle:putInt	(Ljava/lang/String;I)V
    //   296: aload 5
    //   298: aload 6
    //   300: invokevirtual 108	android/os/Message:setData	(Landroid/os/Bundle;)V
    //   303: aload_0
    //   304: getfield 21	com/fab/utils/TimerTask:mHandler	Landroid/os/Handler;
    //   307: aload 5
    //   309: invokevirtual 114	android/os/Handler:sendMessage	(Landroid/os/Message;)Z
    //   312: pop
    //   313: ldc2_w 183
    //   316: invokestatic 189	java/lang/Thread:sleep	(J)V
    //   319: goto -319 -> 0
    //   322: astore 8
    //   324: goto -324 -> 0
    //   327: astore 14
    //   329: goto -103 -> 226
    //   332: astore 13
    //   334: aload 13
    //   336: athrow
    //   337: aload_0
    //   338: getfield 23	com/fab/utils/TimerTask:time	J
    //   341: aload_0
    //   342: getfield 30	com/fab/utils/TimerTask:cal	Ljava/util/Calendar;
    //   345: invokestatic 53	com/fab/utils/FabUtils:getSecondsLeftForTimer	(JLjava/util/Calendar;)J
    //   348: ldc2_w 54
    //   351: lcmp
    //   352: ifne -39 -> 313
    //   355: new 63	android/os/Message
    //   358: dup
    //   359: invokespecial 64	android/os/Message:<init>	()V
    //   362: astore 9
    //   364: new 66	android/os/Bundle
    //   367: dup
    //   368: invokespecial 67	android/os/Bundle:<init>	()V
    //   371: astore 10
    //   373: aload 10
    //   375: ldc 69
    //   377: iconst_2
    //   378: invokevirtual 73	android/os/Bundle:putInt	(Ljava/lang/String;I)V
    //   381: aload 10
    //   383: ldc 174
    //   385: ldc 191
    //   387: invokevirtual 98	android/os/Bundle:putString	(Ljava/lang/String;Ljava/lang/String;)V
    //   390: aload 10
    //   392: ldc 178
    //   394: ldc 193
    //   396: invokevirtual 98	android/os/Bundle:putString	(Ljava/lang/String;Ljava/lang/String;)V
    //   399: aload 10
    //   401: ldc 182
    //   403: iconst_1
    //   404: invokevirtual 73	android/os/Bundle:putInt	(Ljava/lang/String;I)V
    //   407: aload 10
    //   409: ldc 195
    //   411: iconst_1
    //   412: invokevirtual 104	android/os/Bundle:putBoolean	(Ljava/lang/String;Z)V
    //   415: aload 9
    //   417: aload 10
    //   419: invokevirtual 108	android/os/Message:setData	(Landroid/os/Bundle;)V
    //   422: aload_0
    //   423: getfield 21	com/fab/utils/TimerTask:mHandler	Landroid/os/Handler;
    //   426: aload 9
    //   428: invokevirtual 114	android/os/Handler:sendMessage	(Landroid/os/Message;)Z
    //   431: pop
    //   432: goto -119 -> 313
    //   435: astore 16
    //   437: goto -211 -> 226
    //
    // Exception table:
    //   from	to	target	type
    //   313	319	322	java/lang/InterruptedException
    //   128	150	327	com/fab/utils/exception/FabException
    //   155	226	327	com/fab/utils/exception/FabException
    //   128	150	332	finally
    //   155	226	332	finally
    //   155	226	435	org/json/JSONException
  }

  public Handler getHandler()
  {
    return this.mHandler;
  }

  public long getTime()
  {
    return this.time;
  }

  protected void onPostExecute(Void paramVoid)
  {
    FabHelper.timerTaskCompleted = true;
  }

  protected void onPreExecute()
  {
    if (FabUtils.getSecondsLeftForTimer(this.time, this.cal) >= 0L)
    {
      Intent localIntent = new Intent(this.context, TimerNotificationService.class);
      this.context.startService(localIntent);
    }
    super.onPreExecute();
  }

  public void setHandler(Handler paramHandler)
  {
    this.mHandler = paramHandler;
  }

  public void setTime(long paramLong)
  {
    this.time = paramLong;
  }
}

/* Location:           C:\Documents and Settings\Administrator\桌面\private\反编译\apk2java\dex2java\dex-translator-0.0.9.3\dex-translator-0.0.9.3\classes_dex2jar.jar
 * Qualified Name:     com.fab.utils.TimerTask
 * JD-Core Version:    0.6.2
 */