package com.fab.sale.bo;

public class ShopSortBo
{
  private String headerValue;
  private boolean isChecked;
  private boolean isHeader;
  private String sortValue;
  private String valueUrl;

  public String getHeaderValue()
  {
    return this.headerValue;
  }

  public String getSortValue()
  {
    return this.sortValue;
  }

  public String getValueUrl()
  {
    return this.valueUrl;
  }

  public boolean isChecked()
  {
    return this.isChecked;
  }

  public boolean isHeader()
  {
    return this.isHeader;
  }

  public void setChecked(boolean paramBoolean)
  {
    this.isChecked = paramBoolean;
  }

  public void setHeader(boolean paramBoolean)
  {
    this.isHeader = paramBoolean;
  }

  public void setHeaderValue(String paramString)
  {
    this.headerValue = paramString;
  }

  public void setSortValue(String paramString)
  {
    this.sortValue = paramString;
  }

  public void setValueUrl(String paramString)
  {
    this.valueUrl = paramString;
  }
}

/* Location:           C:\Documents and Settings\Administrator\桌面\private\反编译\apk2java\dex2java\dex-translator-0.0.9.3\dex-translator-0.0.9.3\classes_dex2jar.jar
 * Qualified Name:     com.fab.sale.bo.ShopSortBo
 * JD-Core Version:    0.6.2
 */