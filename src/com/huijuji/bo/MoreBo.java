package com.fab.bo;

public class MoreBo
{
  private String heading;
  private boolean isBackButtonControlled;
  private boolean isCBEnabled;
  private boolean isSectionSeparator;
  private boolean isSecure;
  private int notificationType = -1;
  private String sectionSeparatorText;
  private String settingType;
  private String subHeading;
  private String url;

  public String getHeading()
  {
    return this.heading;
  }

  public int getNotificationType()
  {
    return this.notificationType;
  }

  public String getSectionSeparatorText()
  {
    return this.sectionSeparatorText;
  }

  public String getSettingType()
  {
    return this.settingType;
  }

  public String getSubHeading()
  {
    return this.subHeading;
  }

  public String getUrl()
  {
    return this.url;
  }

  public boolean isBackButtonControlled()
  {
    return this.isBackButtonControlled;
  }

  public boolean isCBEnabled()
  {
    return this.isCBEnabled;
  }

  public boolean isSectionSeparator()
  {
    return this.isSectionSeparator;
  }

  public boolean isSecure()
  {
    return this.isSecure;
  }

  public void setBackButtonControlled(boolean paramBoolean)
  {
    this.isBackButtonControlled = paramBoolean;
  }

  public void setCBEnabled(boolean paramBoolean)
  {
    this.isCBEnabled = paramBoolean;
  }

  public void setHeading(String paramString)
  {
    this.heading = paramString;
  }

  public void setNotificationType(int paramInt)
  {
    this.notificationType = paramInt;
  }

  public void setSectionSeparator(boolean paramBoolean)
  {
    this.isSectionSeparator = paramBoolean;
  }

  public void setSectionSeparatorText(String paramString)
  {
    this.sectionSeparatorText = paramString;
  }

  public void setSecure(boolean paramBoolean)
  {
    this.isSecure = paramBoolean;
  }

  public void setSettingType(String paramString)
  {
    this.settingType = paramString;
  }

  public void setSubHeading(String paramString)
  {
    this.subHeading = paramString;
  }

  public void setUrl(String paramString)
  {
    this.url = paramString;
  }
}

/* Location:           C:\Documents and Settings\Administrator\桌面\private\反编译\apk2java\dex2java\dex-translator-0.0.9.3\dex-translator-0.0.9.3\classes_dex2jar.jar
 * Qualified Name:     com.fab.bo.MoreBo
 * JD-Core Version:    0.6.2
 */