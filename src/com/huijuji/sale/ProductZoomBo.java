package com.fab.sale;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class ProductZoomBo
  implements Parcelable
{
  public static final Parcelable.Creator<ProductZoomBo> CREATOR = new Parcelable.Creator()
  {
    public ProductZoomBo createFromParcel(Parcel paramAnonymousParcel)
    {
      return new ProductZoomBo(paramAnonymousParcel, null);
    }

    public ProductZoomBo[] newArray(int paramAnonymousInt)
    {
      return new ProductZoomBo[paramAnonymousInt];
    }
  };
  private Bitmap bitmap;
  private String galleryImageUrl;
  private String imageStoreUrl;
  private String imageUrl;
  private int isPrimary;
  private int originalSize;
  private String zoomImageUrl;
  private int zoomSize;

  public ProductZoomBo()
  {
  }

  private ProductZoomBo(Parcel paramParcel)
  {
    this.imageUrl = paramParcel.readString();
    this.zoomImageUrl = paramParcel.readString();
    this.imageStoreUrl = paramParcel.readString();
    this.zoomSize = paramParcel.readInt();
    this.originalSize = paramParcel.readInt();
    this.galleryImageUrl = paramParcel.readString();
    this.isPrimary = paramParcel.readInt();
  }

  public int describeContents()
  {
    return 0;
  }

  public Bitmap getBitmap()
  {
    return this.bitmap;
  }

  public String getGalleryImageUrl()
  {
    return this.galleryImageUrl;
  }

  public String getImageStoreUrl()
  {
    return this.imageStoreUrl;
  }

  public String getImageUrl()
  {
    return this.imageUrl;
  }

  public int getOriginalSize()
  {
    return this.originalSize;
  }

  public String getZoomImageUrl()
  {
    return this.zoomImageUrl;
  }

  public int getZoomSize()
  {
    return this.zoomSize;
  }

  public int isPrimary()
  {
    return this.isPrimary;
  }

  public void setBitmap(Bitmap paramBitmap)
  {
    this.bitmap = paramBitmap;
  }

  public void setGalleryImageUrl(String paramString)
  {
    this.galleryImageUrl = paramString;
  }

  public void setImageStoreUrl(String paramString)
  {
    this.imageStoreUrl = paramString;
  }

  public void setImageUrl(String paramString)
  {
    this.imageUrl = paramString;
  }

  public void setOriginalSize(int paramInt)
  {
    this.originalSize = paramInt;
  }

  public void setPrimary(int paramInt)
  {
    this.isPrimary = paramInt;
  }

  public void setZoomImageUrl(String paramString)
  {
    this.zoomImageUrl = paramString;
  }

  public void setZoomSize(int paramInt)
  {
    this.zoomSize = paramInt;
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    paramParcel.writeString(this.imageUrl);
    paramParcel.writeString(this.zoomImageUrl);
    paramParcel.writeString(this.imageStoreUrl);
    paramParcel.writeInt(this.zoomSize);
    paramParcel.writeInt(this.originalSize);
    paramParcel.writeString(this.galleryImageUrl);
    paramParcel.writeInt(this.isPrimary);
  }
}

/* Location:           C:\Documents and Settings\Administrator\桌面\private\反编译\apk2java\dex2java\dex-translator-0.0.9.3\dex-translator-0.0.9.3\classes_dex2jar.jar
 * Qualified Name:     com.fab.sale.ProductZoomBo
 * JD-Core Version:    0.6.2
 */