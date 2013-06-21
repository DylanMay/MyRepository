package com.huijuji.view;

import android.content.Context;
import android.text.Html;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

public class ErrorMessage extends LinearLayout
{
  private Context context;
  private FabTextView errorMsg;
  private FabTextView oops;

  public ErrorMessage(Context paramContext)
  {
    super(paramContext);
    this.context = paramContext;
    init();
  }

  public ErrorMessage(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    this.context = paramContext;
    init();
  }

  private void init()
  {
    if (!isInEditMode())
    {
      View localView = inflate(getContext(), 2130903051, null);
      this.oops = ((FabTextView)localView.findViewById(2131361863));
      this.errorMsg = ((FabTextView)localView.findViewById(2131361862));
      this.oops.setFont("HelveticaNeu_bold.ttf");
      this.errorMsg.setFont("HelveticaNeu_bold.ttf");
      addView(localView, new LinearLayout.LayoutParams(-1, -2));
    }
  }

  public void setErrorMsg(String paramString)
  {
    this.errorMsg.setText(Html.fromHtml(paramString));
  }
}

/* Location:           C:\Documents and Settings\Administrator\桌面\private\反编译\apk2java\dex2java\dex-translator-0.0.9.3\dex-translator-0.0.9.3\classes_dex2jar.jar
 * Qualified Name:     com.fab.views.ErrorMessage
 * JD-Core Version:    0.6.2
 */