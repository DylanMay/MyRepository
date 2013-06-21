package com.fab.webviews;

public class JavaScriptInterface
{
  private String TAG = "JS_INTERFACE";
  private ButtonListener listener;

  public JavaScriptInterface()
  {
  }

  public JavaScriptInterface(ButtonListener paramButtonListener)
  {
    this.listener = paramButtonListener;
  }

  public void call(String paramString)
  {
    setButtonData(paramString);
  }

  public void setButtonData(String paramString)
  {
    this.listener.onSetButtonData(paramString);
  }

  public void setButtonListener(ButtonListener paramButtonListener)
  {
    this.listener = paramButtonListener;
  }
}

/* Location:           C:\Documents and Settings\Administrator\桌面\private\反编译\apk2java\dex2java\dex-translator-0.0.9.3\dex-translator-0.0.9.3\classes_dex2jar.jar
 * Qualified Name:     com.fab.webviews.JavaScriptInterface
 * JD-Core Version:    0.6.2
 */