package com.fab.backgroundservice;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import com.fab.utils.FabHelper;

public class SalePollNotificationService extends Service
{
  private static final String TAG = "Sale Poll Notification Service";
  private static Context context;

  public void handleCommand(Intent paramIntent, int paramInt)
  {
    if (paramIntent != null)
      new Thread(new Runnable()
      {
        public void run()
        {
          FabHelper.sendRequestAndSetAlarmForNotifications(SalePollNotificationService.context);
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
    context = getApplicationContext();
    Log.i("Sale Poll Notification Service", "Sale Poll Notification Service Started");
  }

  public void onStart(Intent paramIntent, int paramInt)
  {
    handleCommand(paramIntent, paramInt);
  }

  public int onStartCommand(Intent paramIntent, int paramInt1, int paramInt2)
  {
    handleCommand(paramIntent, paramInt2);
    return 2;
  }
}

/* Location:           C:\Documents and Settings\Administrator\桌面\private\反编译\apk2java\dex2java\dex-translator-0.0.9.3\dex-translator-0.0.9.3\classes_dex2jar.jar
 * Qualified Name:     com.fab.backgroundservice.SalePollNotificationService
 * JD-Core Version:    0.6.2
 */