package com.fab.utils.exception;

public class FabException extends Exception
{
  private static final long serialVersionUID = 1L;

  public FabException()
  {
  }

  public FabException(String paramString)
  {
    super(paramString);
  }

  public FabException(String paramString, Throwable paramThrowable)
  {
    super(paramString, paramThrowable);
  }

  public FabException(Throwable paramThrowable)
  {
    super(paramThrowable);
  }

  public String getLocalizedMessage()
  {
    return super.getLocalizedMessage();
  }
}

/* Location:           C:\Documents and Settings\Administrator\桌面\private\反编译\apk2java\dex2java\dex-translator-0.0.9.3\dex-translator-0.0.9.3\classes_dex2jar.jar
 * Qualified Name:     com.fab.utils.exception.FabException
 * JD-Core Version:    0.6.2
 */