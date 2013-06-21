package com.huijuji.view;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnDismissListener;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.ImageView;
import com.fab.utils.FabHelper;
import com.fab.utils.FabUtils;
import com.fab.utils.ImageDownloader;
import com.fab.utils.TaskCompleteListener;
import com.google.android.apps.analytics.GoogleAnalyticsTracker;
import org.json.JSONException;
import org.json.JSONObject;

public class FabFBTimelineDialog extends Dialog
  implements DialogInterface.OnDismissListener
{
  private Button cancelButton;
  private Context context;
  private FabButton dontShowAgainButton;
  public FabHelper helper = FabHelper.getFabHelper();
  private Button noThanksButton;
  private FabTextView noteText;
  private ImageView productImage;
  private JSONObject timelineInfo;
  private FabTextView timelineText;
  private GoogleAnalyticsTracker tracker;
  private Button tryItOutButton;
  private JSONObject userTimelineInfo;

  public FabFBTimelineDialog(Context paramContext, int paramInt)
  {
    super(paramContext, paramInt);
    this.context = paramContext;
  }

  public FabFBTimelineDialog(Context paramContext, JSONObject paramJSONObject)
  {
    super(paramContext);
    this.context = paramContext;
    this.timelineInfo = paramJSONObject;
  }

  public FabFBTimelineDialog(Context paramContext, boolean paramBoolean, DialogInterface.OnCancelListener paramOnCancelListener)
  {
    super(paramContext, paramBoolean, paramOnCancelListener);
    this.context = paramContext;
  }

  public Button getCancelButton()
  {
    return this.cancelButton;
  }

  public FabButton getDontShowAgainButton()
  {
    return this.dontShowAgainButton;
  }

  public Button getNoThanksButton()
  {
    return this.noThanksButton;
  }

  public Button getTryItOutButton()
  {
    return this.tryItOutButton;
  }

  public JSONObject getUserTimelineInfo()
  {
    return this.userTimelineInfo;
  }

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    requestWindowFeature(1);
    setContentView(2130903052);
    this.context = getContext();
    setOnDismissListener(this);
    this.tracker = GoogleAnalyticsTracker.getInstance();
    this.tracker.startNewSession("UA-19838455-1", 10, this.context);
    this.tracker.trackPageView(FabUtils.gATracUrl("fabDialog"));
    this.cancelButton = ((Button)findViewById(2131361877));
    this.timelineText = ((FabTextView)findViewById(2131361868));
    this.timelineText.setFont("HelveticaNeu_bold.ttf");
    this.productImage = ((ImageView)findViewById(2131361850));
    try
    {
      final String str1 = this.timelineInfo.getString("product_image_url");
      String str2 = this.helper.doesFileExists(this.context, str1);
      if (!TextUtils.isEmpty(str2))
        this.productImage.setImageBitmap(this.helper.decodeFileForBitmap(str2));
      while (true)
      {
        label162: this.noteText = ((FabTextView)findViewById(2131361869));
        this.noteText.setFont("georgiai.ttf");
        this.tryItOutButton = ((Button)findViewById(2131361870));
        this.noThanksButton = ((Button)findViewById(2131361871));
        this.dontShowAgainButton = ((FabButton)findViewById(2131361872));
        this.dontShowAgainButton.setFont("georgiai.ttf");
        return;
        ImageDownloader localImageDownloader = new ImageDownloader(this.context, null, true, null);
        localImageDownloader.setTaskCompleteListener(new TaskCompleteListener()
        {
          public void onTaskComplete()
          {
            String str = FabFBTimelineDialog.this.helper.doesFileExists(FabFBTimelineDialog.this.context, str1);
            if (!TextUtils.isEmpty(str))
            {
              System.gc();
              FabFBTimelineDialog.this.productImage.setImageBitmap(FabFBTimelineDialog.this.helper.decodeFileForBitmap(str));
            }
          }

          public void onTaskComplete(Object paramAnonymousObject)
          {
          }
        });
        String[] arrayOfString = new String[1];
        arrayOfString[0] = str1;
        localImageDownloader.execute(arrayOfString);
      }
    }
    catch (JSONException localJSONException)
    {
      break label162;
    }
  }

  public void onDismiss(DialogInterface paramDialogInterface)
  {
    this.tracker.stopSession();
  }
}

/* Location:           C:\Documents and Settings\Administrator\桌面\private\反编译\apk2java\dex2java\dex-translator-0.0.9.3\dex-translator-0.0.9.3\classes_dex2jar.jar
 * Qualified Name:     com.fab.views.FabFBTimelineDialog
 * JD-Core Version:    0.6.2
 */