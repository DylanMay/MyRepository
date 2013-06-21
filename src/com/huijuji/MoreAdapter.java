package com.fab;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import com.fab.bo.MoreBo;
import com.fab.utils.ExecuteRequestAsyncTask;
import com.fab.utils.FabSharedPrefs;
import com.fab.utils.FabUtils;
import com.fab.utils.TaskCompleteListener;
import com.fab.views.FabTextView;
import com.fab.webviews.FabWebViewActivity;
import java.util.ArrayList;

public class MoreAdapter extends BaseAdapter
{
  private static final int ITEM_VIEW_TYPE_COUNT = 2;
  private static final int ITEM_VIEW_TYPE_SEPARATOR = 1;
  private static final int ITEM_VIEW_TYPE_SETTING = 0;
  private static final String TAG = "MoreAdapter";
  private Context context;
  private ProgressDialog dialog;
  ArrayList<MoreBo> moreData;
  private ExecuteRequestAsyncTask task;

  public MoreAdapter(Context paramContext, ArrayList<MoreBo> paramArrayList)
  {
    this.context = paramContext;
    this.moreData = paramArrayList;
    this.dialog = new ProgressDialog(paramContext);
  }

  public int getCount()
  {
    return this.moreData.size();
  }

  public ProgressDialog getDialog()
  {
    return this.dialog;
  }

  public MoreBo getItem(int paramInt)
  {
    return (MoreBo)this.moreData.get(paramInt);
  }

  public long getItemId(int paramInt)
  {
    return paramInt;
  }

  public int getItemViewType(int paramInt)
  {
    if (((MoreBo)this.moreData.get(paramInt)).isSectionSeparator());
    for (int i = 1; ; i = 0)
      return i;
  }

  public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    int i = getItemViewType(paramInt);
    int j;
    MoreBo localMoreBo;
    if (paramView == null)
    {
      LayoutInflater localLayoutInflater = (LayoutInflater)this.context.getSystemService("layout_inflater");
      if (i == 1)
      {
        j = 2130903075;
        paramView = localLayoutInflater.inflate(j, paramViewGroup, false);
      }
    }
    else
    {
      localMoreBo = (MoreBo)this.moreData.get(paramInt);
      if (i != 1)
        break label90;
      ((TextView)paramView.findViewById(2131361958)).setText(localMoreBo.getSectionSeparatorText());
    }
    while (true)
    {
      return paramView;
      j = 2130903073;
      break;
      label90: FabTextView localFabTextView1 = (FabTextView)paramView.findViewById(2131361954);
      localFabTextView1.setFont("HelveticaNeu_normal.ttf");
      ImageView localImageView = (ImageView)paramView.findViewById(2131361955);
      final CheckBox localCheckBox = (CheckBox)paramView.findViewById(2131361956);
      FabTextView localFabTextView2 = (FabTextView)paramView.findViewById(2131361953);
      localCheckBox.setVisibility(4);
      localFabTextView2.setVisibility(4);
      localImageView.setVisibility(0);
      localFabTextView1.setTextSize(20.0F);
      localFabTextView1.setText(localMoreBo.getHeading());
      paramView.setClickable(true);
      paramView.setTag(localMoreBo);
      if ("checkbox".equalsIgnoreCase(localMoreBo.getSettingType()))
      {
        localImageView.setVisibility(4);
        localFabTextView2.setVisibility(4);
        localCheckBox.setVisibility(0);
        if (FabSharedPrefs.isGuestUser())
          localCheckBox.setChecked(FabSharedPrefs.isGuestUserNotificationsSet());
        while (true)
        {
          localCheckBox.setTag(localMoreBo);
          paramView.setTag(localCheckBox);
          localCheckBox.setOnClickListener(new View.OnClickListener()
          {
            public void onClick(View paramAnonymousView)
            {
              CheckBox localCheckBox = (CheckBox)paramAnonymousView;
              MoreBo localMoreBo = (MoreBo)paramAnonymousView.getTag();
              if (!localCheckBox.isChecked())
              {
                if (TextUtils.isEmpty(localMoreBo.getUrl()))
                {
                  FabSharedPrefs.setGuestUserNotifications(false);
                  FabUtils.cancelAlarmForPullNotification(MoreAdapter.this.context);
                  FabUtils.cancelAlarmForSaleNotification(MoreAdapter.this.context);
                  FabUtils.cancelAlarmForSpecialNotification(MoreAdapter.this.context);
                }
              }
              else if (localCheckBox.isChecked())
              {
                if (!TextUtils.isEmpty(localMoreBo.getUrl()))
                  break label134;
                FabUtils.setAlarmForPullNotification(MoreAdapter.this.context, 0L);
                FabSharedPrefs.setGuestUserNotifications(true);
              }
              while (true)
              {
                return;
                localMoreBo.setCBEnabled(false);
                MoreAdapter.this.sendRequest(0, localMoreBo.getUrl(), MoreAdapter.this.moreData.indexOf(localMoreBo), localMoreBo.isSecure());
                break;
                label134: localMoreBo.setCBEnabled(true);
                MoreAdapter.this.sendRequest(1, localMoreBo.getUrl(), MoreAdapter.this.moreData.indexOf(localMoreBo), localMoreBo.isSecure());
              }
            }
          });
          paramView.setEnabled(true);
          paramView.setOnClickListener(new View.OnClickListener()
          {
            public void onClick(View paramAnonymousView)
            {
              localCheckBox.performClick();
            }
          });
          break;
          if (localMoreBo.isCBEnabled())
            localCheckBox.setChecked(true);
          else
            localCheckBox.setChecked(false);
        }
      }
      if ("string".equalsIgnoreCase(localMoreBo.getSettingType()))
      {
        localImageView.setVisibility(4);
        localCheckBox.setVisibility(4);
        if (localMoreBo.getSubHeading().length() > 22);
        for (String str = localMoreBo.getSubHeading().substring(0, 19).concat("..."); ; str = localMoreBo.getSubHeading())
        {
          localFabTextView2.setText(str);
          localFabTextView2.setVisibility(0);
          if ((!FabUtils.isLoggedIn(this.context)) || (FabSharedPrefs.isGuestUser()))
            break label409;
          paramView.setEnabled(false);
          break;
        }
        label409: paramView.setEnabled(true);
        paramView.setOnClickListener(new View.OnClickListener()
        {
          public void onClick(View paramAnonymousView)
          {
            Intent localIntent = new Intent(MoreAdapter.this.context, FabSignin.class);
            localIntent.setFlags(603979776);
            localIntent.putExtra("IS_FROM_GUEST", true);
            MoreAdapter.this.context.startActivity(localIntent);
          }
        });
      }
      else if ("button".equalsIgnoreCase(localMoreBo.getSettingType()))
      {
        paramView.setEnabled(true);
        paramView.setOnClickListener(new View.OnClickListener()
        {
          public void onClick(View paramAnonymousView)
          {
            MoreBo localMoreBo = (MoreBo)paramAnonymousView.getTag();
            String str1 = localMoreBo.getUrl();
            if (str1.contains("app:"))
            {
              String str2 = str1.replace("app:", "");
              if ("ShareSettings".equalsIgnoreCase(str2))
              {
                Intent localIntent2 = new Intent(MoreAdapter.this.context, ShareSettingsActivity.class);
                MoreAdapter.this.context.startActivity(localIntent2);
              }
              if ("NotificationSettings".equalsIgnoreCase(str2))
              {
                Intent localIntent3 = new Intent(MoreAdapter.this.context, MoreActivity.class);
                localIntent3.putExtra("TITLE", localMoreBo.getHeading());
                localIntent3.putExtra("REQUEST_URL", "/mobile/edit-notification-settings/");
                MoreAdapter.this.context.startActivity(localIntent3);
              }
              return;
            }
            if (localMoreBo.isBackButtonControlled());
            for (Intent localIntent1 = new Intent(MoreAdapter.this.context, FabWebViewActivity.class); ; localIntent1 = new Intent(MoreAdapter.this.context, MoreWebViewActivity.class))
            {
              localIntent1.putExtra("URL", FabUtils.requestUrl(localMoreBo.isSecure(), "/mobile/" + str1, ""));
              MoreAdapter.this.context.startActivity(localIntent1);
              break;
            }
          }
        });
      }
    }
  }

  public int getViewTypeCount()
  {
    return 2;
  }

  public boolean isEnabled(int paramInt)
  {
    if (getItemViewType(paramInt) == 0);
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  protected void sendRequest(int paramInt1, String paramString, final int paramInt2, boolean paramBoolean)
  {
    if (MoreActivity.isActivityAlive)
    {
      this.dialog.show();
      this.dialog.setMessage("Updating...");
      this.dialog.setCancelable(true);
    }
    this.task = new ExecuteRequestAsyncTask(this.context, "GET");
    this.task.setTaskCompleteListener(new TaskCompleteListener()
    {
      public void onTaskComplete()
      {
      }

      // ERROR //
      public void onTaskComplete(Object paramAnonymousObject)
      {
        // Byte code:
        //   0: iconst_0
        //   1: istore_2
        //   2: aload_1
        //   3: ifnull +279 -> 282
        //   6: aload_1
        //   7: instanceof 31
        //   10: ifeq +272 -> 282
        //   13: aload_1
        //   14: checkcast 31	org/json/JSONObject
        //   17: astore_3
        //   18: aload_3
        //   19: ldc 33
        //   21: invokevirtual 37	org/json/JSONObject:getBoolean	(Ljava/lang/String;)Z
        //   24: pop
        //   25: ldc 39
        //   27: aload_3
        //   28: invokevirtual 43	org/json/JSONObject:toString	()Ljava/lang/String;
        //   31: invokestatic 49	com/fab/utils/FabUtils:log	(Ljava/lang/String;Ljava/lang/String;)V
        //   34: aload_0
        //   35: getfield 20	com/fab/MoreAdapter$5:this$0	Lcom/fab/MoreAdapter;
        //   38: getfield 53	com/fab/MoreAdapter:moreData	Ljava/util/ArrayList;
        //   41: aload_0
        //   42: getfield 22	com/fab/MoreAdapter$5:val$index	I
        //   45: invokevirtual 59	java/util/ArrayList:get	(I)Ljava/lang/Object;
        //   48: checkcast 61	com/fab/bo/MoreBo
        //   51: astore 6
        //   53: aload 6
        //   55: aload_3
        //   56: ldc 63
        //   58: invokevirtual 67	org/json/JSONObject:getInt	(Ljava/lang/String;)I
        //   61: invokevirtual 71	com/fab/bo/MoreBo:setNotificationType	(I)V
        //   64: aload_3
        //   65: ldc 73
        //   67: invokevirtual 77	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
        //   70: invokestatic 83	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
        //   73: ifne +86 -> 159
        //   76: aload_0
        //   77: getfield 20	com/fab/MoreAdapter$5:this$0	Lcom/fab/MoreAdapter;
        //   80: invokestatic 87	com/fab/MoreAdapter:access$1	(Lcom/fab/MoreAdapter;)Landroid/content/Context;
        //   83: aload_3
        //   84: ldc 73
        //   86: invokevirtual 77	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
        //   89: iconst_0
        //   90: invokestatic 93	android/widget/Toast:makeText	(Landroid/content/Context;Ljava/lang/CharSequence;I)Landroid/widget/Toast;
        //   93: invokevirtual 96	android/widget/Toast:show	()V
        //   96: aload 6
        //   98: invokevirtual 100	com/fab/bo/MoreBo:isCBEnabled	()Z
        //   101: ifeq +182 -> 283
        //   104: aload 6
        //   106: iload_2
        //   107: invokevirtual 104	com/fab/bo/MoreBo:setCBEnabled	(Z)V
        //   110: aload_0
        //   111: getfield 20	com/fab/MoreAdapter$5:this$0	Lcom/fab/MoreAdapter;
        //   114: invokevirtual 107	com/fab/MoreAdapter:notifyDataSetChanged	()V
        //   117: getstatic 113	com/fab/MoreActivity:isActivityAlive	Z
        //   120: ifeq +162 -> 282
        //   123: aload_0
        //   124: getfield 20	com/fab/MoreAdapter$5:this$0	Lcom/fab/MoreAdapter;
        //   127: invokestatic 117	com/fab/MoreAdapter:access$2	(Lcom/fab/MoreAdapter;)Landroid/app/ProgressDialog;
        //   130: ifnull +152 -> 282
        //   133: aload_0
        //   134: getfield 20	com/fab/MoreAdapter$5:this$0	Lcom/fab/MoreAdapter;
        //   137: invokestatic 117	com/fab/MoreAdapter:access$2	(Lcom/fab/MoreAdapter;)Landroid/app/ProgressDialog;
        //   140: invokevirtual 122	android/app/ProgressDialog:isShowing	()Z
        //   143: ifeq +139 -> 282
        //   146: aload_0
        //   147: getfield 20	com/fab/MoreAdapter$5:this$0	Lcom/fab/MoreAdapter;
        //   150: invokestatic 117	com/fab/MoreAdapter:access$2	(Lcom/fab/MoreAdapter;)Landroid/app/ProgressDialog;
        //   153: invokevirtual 125	android/app/ProgressDialog:cancel	()V
        //   156: goto +126 -> 282
        //   159: aload_3
        //   160: ldc 127
        //   162: invokevirtual 77	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
        //   165: invokestatic 83	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
        //   168: ifne +114 -> 282
        //   171: aload_0
        //   172: getfield 20	com/fab/MoreAdapter$5:this$0	Lcom/fab/MoreAdapter;
        //   175: invokestatic 87	com/fab/MoreAdapter:access$1	(Lcom/fab/MoreAdapter;)Landroid/content/Context;
        //   178: invokestatic 133	com/fab/utils/FabHelper:sendRequestAndSetAlarmForNotifications	(Landroid/content/Context;)V
        //   181: aload_0
        //   182: getfield 20	com/fab/MoreAdapter$5:this$0	Lcom/fab/MoreAdapter;
        //   185: invokestatic 87	com/fab/MoreAdapter:access$1	(Lcom/fab/MoreAdapter;)Landroid/content/Context;
        //   188: aload_3
        //   189: ldc 127
        //   191: invokevirtual 77	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
        //   194: iconst_0
        //   195: invokestatic 93	android/widget/Toast:makeText	(Landroid/content/Context;Ljava/lang/CharSequence;I)Landroid/widget/Toast;
        //   198: invokevirtual 96	android/widget/Toast:show	()V
        //   201: getstatic 113	com/fab/MoreActivity:isActivityAlive	Z
        //   204: ifeq +36 -> 240
        //   207: aload_0
        //   208: getfield 20	com/fab/MoreAdapter$5:this$0	Lcom/fab/MoreAdapter;
        //   211: invokestatic 117	com/fab/MoreAdapter:access$2	(Lcom/fab/MoreAdapter;)Landroid/app/ProgressDialog;
        //   214: ifnull +26 -> 240
        //   217: aload_0
        //   218: getfield 20	com/fab/MoreAdapter$5:this$0	Lcom/fab/MoreAdapter;
        //   221: invokestatic 117	com/fab/MoreAdapter:access$2	(Lcom/fab/MoreAdapter;)Landroid/app/ProgressDialog;
        //   224: invokevirtual 122	android/app/ProgressDialog:isShowing	()Z
        //   227: ifeq +13 -> 240
        //   230: aload_0
        //   231: getfield 20	com/fab/MoreAdapter$5:this$0	Lcom/fab/MoreAdapter;
        //   234: invokestatic 117	com/fab/MoreAdapter:access$2	(Lcom/fab/MoreAdapter;)Landroid/app/ProgressDialog;
        //   237: invokevirtual 125	android/app/ProgressDialog:cancel	()V
        //   240: aload_3
        //   241: ldc 135
        //   243: invokevirtual 138	org/json/JSONObject:has	(Ljava/lang/String;)Z
        //   246: ifeq +36 -> 282
        //   249: aload_0
        //   250: getfield 20	com/fab/MoreAdapter$5:this$0	Lcom/fab/MoreAdapter;
        //   253: invokestatic 87	com/fab/MoreAdapter:access$1	(Lcom/fab/MoreAdapter;)Landroid/content/Context;
        //   256: checkcast 109	com/fab/MoreActivity
        //   259: aload_3
        //   260: aconst_null
        //   261: invokevirtual 142	com/fab/MoreActivity:populateResponse	(Lorg/json/JSONObject;Ljava/lang/String;)V
        //   264: goto +18 -> 282
        //   267: astore 5
        //   269: goto +13 -> 282
        //   272: astore 7
        //   274: goto -210 -> 64
        //   277: astore 4
        //   279: goto -254 -> 25
        //   282: return
        //   283: iconst_1
        //   284: istore_2
        //   285: goto -181 -> 104
        //
        // Exception table:
        //   from	to	target	type
        //   34	53	267	org/json/JSONException
        //   64	264	267	org/json/JSONException
        //   53	64	272	org/json/JSONException
        //   18	25	277	org/json/JSONException
      }
    });
    ExecuteRequestAsyncTask localExecuteRequestAsyncTask = this.task;
    String[] arrayOfString = new String[1];
    arrayOfString[0] = FabUtils.requestUrl(paramBoolean, "/mobile/" + paramString, "?enable=" + paramInt1);
    localExecuteRequestAsyncTask.execute(arrayOfString);
  }
}

/* Location:           C:\Documents and Settings\Administrator\桌面\private\反编译\apk2java\dex2java\dex-translator-0.0.9.3\dex-translator-0.0.9.3\classes_dex2jar.jar
 * Qualified Name:     com.fab.MoreAdapter
 * JD-Core Version:    0.6.2
 */