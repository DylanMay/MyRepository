package com.fab.bo;

public class FBPreferencesBo
{
  private String heading;
  private boolean isCBEnabled;
  private boolean isChecked;
  private boolean isSecure;
  private String url;

  public String getHeading()
  {
    return this.heading;
  }

  public String getUrl()
  {
    return this.url;
  }

  public boolean isCBEnabled()
  {
    return this.isCBEnabled;
  }

  public boolean isChecked()
  {
    return this.isChecked;
  }

  public boolean isSecure()
  {
    return this.isSecure;
  }

  public void setCBEnabled(boolean paramBoolean)
  {
    this.isCBEnabled = paramBoolean;
  }

  public void setChecked(boolean paramBoolean)
  {
    this.isChecked = paramBoolean;
  }

  public void setHeading(String paramString)
  {
    this.heading = paramString;
  }

  public void setSecure(boolean paramBoolean)
  {
    this.isSecure = paramBoolean;
  }

  public void setUrl(String paramString)
  {
    this.url = paramString;
  }
}

/* Location:           C:\Documents and Settings\Administrator\桌面\private\反编译\apk2java\dex2java\dex-translator-0.0.9.3\dex-translator-0.0.9.3\classes_dex2jar.jar
 * Qualified Name:     com.fab.bo.FBPreferencesBo
 * JD-Core Version:    0.6.2
 */