package com.fab.sale.bo;

import com.fab.utils.FabUtils;

public class SalesBO
{
  private String dateSeparator;
  private String description;
  private String fileStoreUrl;
  private Boolean header;
  private boolean isSliderOpen;
  private String primaryTitle;
  private String ribbonText;
  private String saleDuration;
  private int saleId;
  private String saleImageUrl;
  private String saleName;
  private String type;

  public String getDateSeparator()
  {
    return this.dateSeparator;
  }

  public String getDescription()
  {
    return this.description;
  }

  public String getFileStoreUrl()
  {
    return this.fileStoreUrl;
  }

  public String getPrimaryTitle()
  {
    return this.primaryTitle;
  }

  public String getRibbonText()
  {
    return this.ribbonText;
  }

  public String getSaleDuration()
  {
    return this.saleDuration;
  }

  public int getSaleId()
  {
    return this.saleId;
  }

  public String getSaleImageUrl()
  {
    return this.saleImageUrl;
  }

  public String getSaleName()
  {
    return this.saleName;
  }

  public String getType()
  {
    return this.type;
  }

  public Boolean isHeader()
  {
    return this.header;
  }

  public boolean isSliderOpen()
  {
    return this.isSliderOpen;
  }

  public void setDateSeparator(String paramString)
  {
    this.dateSeparator = FabUtils.getReadableDate(paramString);
  }

  public void setDescription(String paramString)
  {
    this.description = paramString;
  }

  public void setFileStoreUrl(String paramString)
  {
    this.fileStoreUrl = paramString;
  }

  public void setHeader(boolean paramBoolean)
  {
    this.header = Boolean.valueOf(paramBoolean);
  }

  public void setPrimaryTitle(String paramString)
  {
    this.primaryTitle = paramString;
  }

  public void setRibbonText(String paramString)
  {
    this.ribbonText = paramString;
  }

  public void setSaleDuration(String paramString)
  {
    this.saleDuration = paramString;
  }

  public void setSaleId(int paramInt)
  {
    this.saleId = paramInt;
  }

  public void setSaleImageUrl(String paramString)
  {
    this.saleImageUrl = paramString;
  }

  public void setSaleName(String paramString)
  {
    this.saleName = paramString;
  }

  public void setSliderOpen(boolean paramBoolean)
  {
    this.isSliderOpen = paramBoolean;
  }

  public void setType(String paramString)
  {
    this.type = paramString;
  }
}

/* Location:           C:\Documents and Settings\Administrator\桌面\private\反编译\apk2java\dex2java\dex-translator-0.0.9.3\dex-translator-0.0.9.3\classes_dex2jar.jar
 * Qualified Name:     com.fab.sale.bo.SalesBO
 * JD-Core Version:    0.6.2
 */