package com.fab;

import android.app.ProgressDialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import com.fab.bo.FBPreferencesBo;
import com.fab.utils.ExecuteRequestAsyncTask;
import com.fab.utils.FabUtils;
import com.fab.utils.TaskCompleteListener;
import com.fab.views.FabTextView;
import java.util.ArrayList;
import org.json.JSONException;
import org.json.JSONObject;

public class ShareSettingsAdapter extends BaseAdapter
{
  private static final String TAG = "ShareSettingsAdapter";
  private Context context;
  private ProgressDialog dialog;
  ArrayList<FBPreferencesBo> settingsData;
  private ExecuteRequestAsyncTask task;

  public ShareSettingsAdapter(Context paramContext, ArrayList<FBPreferencesBo> paramArrayList)
  {
    this.context = paramContext;
    this.settingsData = paramArrayList;
    this.dialog = new ProgressDialog(paramContext);
  }

  public int getCount()
  {
    return this.settingsData.size();
  }

  public FBPreferencesBo getItem(int paramInt)
  {
    return (FBPreferencesBo)this.settingsData.get(paramInt);
  }

  public long getItemId(int paramInt)
  {
    return paramInt;
  }

  public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    ShareSettingsHolder localShareSettingsHolder;
    if (paramView == null)
    {
      paramView = ((LayoutInflater)this.context.getSystemService("layout_inflater")).inflate(2130903090, paramViewGroup, false);
      localShareSettingsHolder = new ShareSettingsHolder();
      localShareSettingsHolder.heading = ((FabTextView)paramView.findViewById(2131362050));
      localShareSettingsHolder.checkbox = ((CheckBox)paramView.findViewById(2131362051));
      paramView.setTag(localShareSettingsHolder);
    }
    while (true)
    {
      final FBPreferencesBo localFBPreferencesBo = (FBPreferencesBo)this.settingsData.get(paramInt);
      localShareSettingsHolder.heading.setText(localFBPreferencesBo.getHeading());
      localShareSettingsHolder.checkbox.setChecked(localFBPreferencesBo.isChecked());
      localShareSettingsHolder.checkbox.setEnabled(localFBPreferencesBo.isCBEnabled());
      if ((localFBPreferencesBo.isCBEnabled()) && (!TextUtils.isEmpty(localFBPreferencesBo.getUrl())))
        localShareSettingsHolder.checkbox.setOnClickListener(new View.OnClickListener()
        {
          public void onClick(View paramAnonymousView)
          {
            if (ShareSettingsActivity.isActivityAlive)
            {
              ShareSettingsAdapter.this.dialog.show();
              ShareSettingsAdapter.this.dialog.setMessage("Updating...");
              ShareSettingsAdapter.this.dialog.setCancelable(true);
            }
            final CheckBox localCheckBox = (CheckBox)paramAnonymousView;
            if (localCheckBox.isChecked());
            for (final int i = 1; ; i = 0)
            {
              ShareSettingsAdapter.this.task = new ExecuteRequestAsyncTask(ShareSettingsAdapter.this.context, "GET");
              ShareSettingsAdapter.this.task.setTaskCompleteListener(new TaskCompleteListener()
              {
                public void onTaskComplete()
                {
                }

                public void onTaskComplete(Object paramAnonymous2Object)
                {
                  int i = 1;
                  JSONObject localJSONObject = (JSONObject)paramAnonymous2Object;
                  Object localObject = null;
                  try
                  {
                    String str = localJSONObject.getString("err_msg");
                    localObject = str;
                    if (!TextUtils.isEmpty(localObject))
                    {
                      CheckBox localCheckBox = localCheckBox;
                      if (i == i)
                        i = 0;
                      localCheckBox.setChecked(i);
                    }
                    if ((ShareSettingsActivity.isActivityAlive) && (ShareSettingsAdapter.this.dialog != null) && (ShareSettingsAdapter.this.dialog.isShowing()))
                      ShareSettingsAdapter.this.dialog.cancel();
                    return;
                  }
                  catch (JSONException localJSONException)
                  {
                    while (true)
                      localJSONException.printStackTrace();
                  }
                }
              });
              ExecuteRequestAsyncTask localExecuteRequestAsyncTask = ShareSettingsAdapter.this.task;
              String[] arrayOfString = new String[1];
              arrayOfString[0] = FabUtils.requestUrl(false, "/mobile/" + localFBPreferencesBo.getUrl() + "&setting_preference=" + i, "");
              localExecuteRequestAsyncTask.execute(arrayOfString);
              return;
            }
          }
        });
      return paramView;
      localShareSettingsHolder = (ShareSettingsHolder)paramView.getTag();
    }
  }

  public static class ShareSettingsHolder
  {
    public CheckBox checkbox;
    public FabTextView heading;
  }
}

/* Location:           C:\Documents and Settings\Administrator\桌面\private\反编译\apk2java\dex2java\dex-translator-0.0.9.3\dex-translator-0.0.9.3\classes_dex2jar.jar
 * Qualified Name:     com.fab.ShareSettingsAdapter
 * JD-Core Version:    0.6.2
 */