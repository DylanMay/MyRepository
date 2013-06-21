package com.fab.backgroundservice;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import com.fab.utils.FabHelper;

public class ClearSDCardService extends Service
{
  private static final String TAG = "Clear SDCard Service";
  private Bundle extra;

  public void handleCommand(final Intent paramIntent, final int paramInt)
  {
    if (paramIntent != null)
      new Thread(new Runnable()
      {
        public void run()
        {
          ClearSDCardService.this.extra = paramIntent.getExtras();
          boolean bool = false;
          if (ClearSDCardService.this.extra != null)
            bool = ClearSDCardService.this.extra.getBoolean("FORCE_CLEAR");
          FabHelper localFabHelper = FabHelper.getFabHelper();
          if (bool)
            localFabHelper.forceClearSDCard();
          while (true)
          {
            ClearSDCardService.this.stopSelf(paramInt);
            return;
            localFabHelper.clearSDCard();
          }
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
    Log.i("Clear SDCard Service", "Clear SDCard Service Started");
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
 * Qualified Name:     com.fab.backgroundservice.ClearSDCardService
 * JD-Core Version:    0.6.2
 */