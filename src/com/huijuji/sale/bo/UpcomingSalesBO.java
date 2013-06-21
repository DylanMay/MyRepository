package com.fab.sale.bo;

import com.fab.utils.FabUtils;

public class UpcomingSalesBO
{
  private String dateSeparator;
  private boolean header;
  private boolean lastProduct;
  private String primaryTitle;
  private String ribbonText;
  private int saleIconResId;
  private int saleId;
  private String saleImageUrl;
  private String saleName;
  private String saleTypeHeader;
  private boolean subHeader;

  public String getDateSeparator()
  {
    return this.dateSeparator;
  }

  public String getPrimaryTitle()
  {
    return this.primaryTitle;
  }

  public String getRibbonText()
  {
    return this.ribbonText;
  }

  public int getSaleIconResId()
  {
    return this.saleIconResId;
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

  public String getSaleTypeHeader()
  {
    return this.saleTypeHeader;
  }

  public boolean isHeader()
  {
    return this.header;
  }

  public boolean isLastProduct()
  {
    return this.lastProduct;
  }

  public boolean isSubHeader()
  {
    return this.subHeader;
  }

  public void setDateSeparator(String paramString)
  {
    this.dateSeparator = FabUtils.getReadableDate(paramString);
  }

  public void setHeader(boolean paramBoolean)
  {
    this.header = paramBoolean;
  }

  public void setLastProduct(boolean paramBoolean)
  {
    this.lastProduct = paramBoolean;
  }

  public void setPrimaryTitle(String paramString)
  {
    this.primaryTitle = paramString;
  }

  public void setRibbonText(String paramString)
  {
    this.ribbonText = paramString;
  }

  public void setSaleIconResId(int paramInt)
  {
    this.saleIconResId = paramInt;
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

  public void setSaleTypeHeader(String paramString)
  {
    this.saleTypeHeader = paramString;
  }

  public void setSubHeader(boolean paramBoolean)
  {
    this.subHeader = paramBoolean;
  }
}

/* Location:           C:\Documents and Settings\Administrator\桌面\private\反编译\apk2java\dex2java\dex-translator-0.0.9.3\dex-translator-0.0.9.3\classes_dex2jar.jar
 * Qualified Name:     com.fab.sale.bo.UpcomingSalesBO
 * JD-Core Version:    0.6.2
 */