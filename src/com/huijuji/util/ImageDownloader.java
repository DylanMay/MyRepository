package com.fab.utils;

import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.widget.ProgressBar;
import com.fab.connection.FabHttpConnection;
import com.fab.sale.WeeklyShopsAdapter;
import com.fab.utils.exception.FabException;
import java.io.InputStream;

public class ImageDownloader extends AsyncTask<String, Void, Void>
{
  FabBaseAdapter adapter;
  Context context;
  private int count;
  boolean isForceDownload;
  ProgressBar pBar;
  TaskCompleteListener taskCompleteListener;
  private WeeklyShopsAdapter weeklyShopsAdapter;

  public ImageDownloader(Context paramContext, WeeklyShopsAdapter paramWeeklyShopsAdapter, boolean paramBoolean, ProgressBar paramProgressBar, int paramInt)
  {
    this.context = paramContext;
    this.weeklyShopsAdapter = paramWeeklyShopsAdapter;
    this.isForceDownload = paramBoolean;
    this.pBar = paramProgressBar;
  }

  public ImageDownloader(Context paramContext, FabBaseAdapter paramFabBaseAdapter, boolean paramBoolean, ProgressBar paramProgressBar)
  {
    this.context = paramContext;
    this.adapter = paramFabBaseAdapter;
    this.isForceDownload = paramBoolean;
    this.pBar = paramProgressBar;
  }

  protected Void doInBackground(String[] paramArrayOfString)
  {
    int i = 0;
    this.count = i;
    FabHttpConnection localFabHttpConnection = FabHttpConnection.getDefaultClient();
    FabHelper localFabHelper = FabHelper.getFabHelper();
    while (true)
    {
      try
      {
        int j = paramArrayOfString.length;
        if (i >= j)
          return null;
        String str = paramArrayOfString[i];
        try
        {
          if (!isCancelled())
          {
            if ((str == null) || ((!TextUtils.isEmpty(localFabHelper.doesFileExists(this.context, str))) && (!this.isForceDownload)))
              break label229;
            InputStream localInputStream = localFabHttpConnection.executeRequest(str, null, null, "GET");
            localFabHelper.writeToCache(this.context, localInputStream, str);
            FabUtils.log("ImageDownloader", str, "I");
            if (this.adapter != null)
            {
              Void[] arrayOfVoid2 = new Void[1];
              arrayOfVoid2[0] = null;
              publishProgress(arrayOfVoid2);
            }
            if (this.weeklyShopsAdapter != null)
            {
              Void[] arrayOfVoid1 = new Void[1];
              arrayOfVoid1[0] = null;
              publishProgress(arrayOfVoid1);
            }
            this.count = (1 + this.count);
            this.isForceDownload = false;
            break label229;
          }
          FabUtils.log("ImageDownloader", "Thread Stopped", "I");
        }
        catch (FabException localFabException)
        {
          if (FabUtils.checkInternetConnection(this.context))
            break label229;
        }
        FabUtils.log("ImageDownloader", "Thread Stopped", "I");
        continue;
      }
      finally
      {
        localFabHttpConnection.closeConnection();
        ((String[])null);
      }
      label229: i++;
    }
  }

  public TaskCompleteListener getTaskCompleteListener()
  {
    return this.taskCompleteListener;
  }

  protected void onCancelled()
  {
    if (this.adapter != null)
      this.adapter.setAllDownloading(false);
    if (this.weeklyShopsAdapter != null)
      this.weeklyShopsAdapter.setAllDownloading(false);
    super.onCancelled();
  }

  protected void onPostExecute(Void paramVoid)
  {
    if ((!this.isForceDownload) && (this.adapter != null))
    {
      if (this.count > 0)
        this.adapter.notifyDataSetChanged();
      this.adapter.setAllDownloading(false);
    }
    if ((!this.isForceDownload) && (this.weeklyShopsAdapter != null))
    {
      if (this.count > 0)
        this.weeklyShopsAdapter.notifyDataSetChanged();
      this.weeklyShopsAdapter.setAllDownloading(false);
    }
    if (this.pBar != null)
      this.pBar.setVisibility(4);
    if (this.taskCompleteListener != null)
      this.taskCompleteListener.onTaskComplete();
  }

  protected void onProgressUpdate(Void[] paramArrayOfVoid)
  {
    if (this.adapter != null)
      this.adapter.notifyDataSetChanged();
    if (this.weeklyShopsAdapter != null)
      this.weeklyShopsAdapter.notifyDataSetChanged();
    super.onProgressUpdate(paramArrayOfVoid);
  }

  public void setTaskCompleteListener(TaskCompleteListener paramTaskCompleteListener)
  {
    this.taskCompleteListener = paramTaskCompleteListener;
  }
}

/* Location:           C:\Documents and Settings\Administrator\桌面\private\反编译\apk2java\dex2java\dex-translator-0.0.9.3\dex-translator-0.0.9.3\classes_dex2jar.jar
 * Qualified Name:     com.fab.utils.ImageDownloader
 * JD-Core Version:    0.6.2
 */