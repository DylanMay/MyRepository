package com.fab.sale.bo;

public class PairedProducts
{
  private ProductDetailsBO firstProduct;
  private ProductDetailsBO secondProduct;

  public ProductDetailsBO getFirstProduct()
  {
    return this.firstProduct;
  }

  public ProductDetailsBO getSecondProduct()
  {
    return this.secondProduct;
  }

  public void setFirstProduct(ProductDetailsBO paramProductDetailsBO)
  {
    this.firstProduct = paramProductDetailsBO;
  }

  public void setSecondProduct(ProductDetailsBO paramProductDetailsBO)
  {
    this.secondProduct = paramProductDetailsBO;
  }
}

/* Location:           C:\Documents and Settings\Administrator\桌面\private\反编译\apk2java\dex2java\dex-translator-0.0.9.3\dex-translator-0.0.9.3\classes_dex2jar.jar
 * Qualified Name:     com.fab.sale.bo.PairedProducts
 * JD-Core Version:    0.6.2
 */