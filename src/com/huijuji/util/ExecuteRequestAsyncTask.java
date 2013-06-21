package com.fab.utils;

import android.content.Context;
import android.content.res.Resources;
import android.os.AsyncTask;
import com.fab.connection.FabHttpConnection;
import com.fab.utils.exception.FabException;
import java.util.HashMap;
import org.json.JSONObject;

public class ExecuteRequestAsyncTask extends AsyncTask<String, Void, JSONObject>
{
  private static final String TAG = "ExecuteRequestAsyncTask";
  private Context context;
  private String errMsg;
  private HashMap<String, String> headers;
  private String method;
  private HashMap<String, String> parameters;
  private TaskCompleteListener taskCompleteListener;

  public ExecuteRequestAsyncTask(Context paramContext, String paramString)
  {
    this.context = paramContext;
    this.method = paramString;
  }

  protected JSONObject doInBackground(String[] paramArrayOfString)
  {
    FabHttpConnection localFabHttpConnection = FabHttpConnection.getDefaultClient();
    while (true)
    {
      int j;
      try
      {
        int i = paramArrayOfString.length;
        j = 0;
        Object localObject3;
        if (j >= i)
        {
          localFabHttpConnection.closeConnection();
          localObject3 = null;
          return localObject3;
        }
        String str = paramArrayOfString[j];
        try
        {
          if ((!isCancelled()) && (str != null))
          {
            FabUtils.log("ExecuteRequestAsyncTask", str);
            JSONObject localJSONObject = FabParser.parseJSONResponse(localFabHttpConnection.executeRequest(str, this.headers, this.parameters, this.method));
            localFabHttpConnection.closeConnection();
            localObject3 = localJSONObject;
            continue;
          }
        }
        catch (FabException localFabException)
        {
          localFabException = localFabException;
          if (!FabUtils.checkInternetConnection(this.context))
          {
            this.errMsg = this.context.getResources().getString(2131165202);
            break label152;
          }
          this.errMsg = this.context.getResources().getString(2131165203);
          break label152;
        }
        finally
        {
        }
      }
      finally
      {
        localFabHttpConnection.closeConnection();
      }
      label152: j++;
    }
  }

  public String getErrMsg()
  {
    return this.errMsg;
  }

  public TaskCompleteListener getTaskCompleteListener()
  {
    return this.taskCompleteListener;
  }

  protected void onPostExecute(JSONObject paramJSONObject)
  {
    if (this.taskCompleteListener != null)
      this.taskCompleteListener.onTaskComplete(paramJSONObject);
  }

  public void setHeaders(HashMap<String, String> paramHashMap)
  {
    this.headers = paramHashMap;
  }

  public void setParameters(HashMap<String, String> paramHashMap)
  {
    this.parameters = paramHashMap;
  }

  public void setTaskCompleteListener(TaskCompleteListener paramTaskCompleteListener)
  {
    this.taskCompleteListener = paramTaskCompleteListener;
  }
}

/* Location:           C:\Documents and Settings\Administrator\桌面\private\反编译\apk2java\dex2java\dex-translator-0.0.9.3\dex-translator-0.0.9.3\classes_dex2jar.jar
 * Qualified Name:     com.fab.utils.ExecuteRequestAsyncTask
 * JD-Core Version:    0.6.2
 */