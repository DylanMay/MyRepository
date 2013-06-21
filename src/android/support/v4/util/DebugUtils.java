package android.support.v4.util;

public class DebugUtils
{
  public static void buildShortClassTag(Object paramObject, StringBuilder paramStringBuilder)
  {
    if (paramObject == null)
      paramStringBuilder.append("null");
    while (true)
    {
      return;
      String str = paramObject.getClass().getSimpleName();
      if ((str == null) || (str.length() <= 0))
      {
        str = paramObject.getClass().getName();
        int i = str.lastIndexOf('.');
        if (i > 0)
          str = str.substring(i + 1);
      }
      paramStringBuilder.append(str);
      paramStringBuilder.append('{');
      paramStringBuilder.append(Integer.toHexString(System.identityHashCode(paramObject)));
    }
  }
}

/* Location:           C:\Documents and Settings\Administrator\桌面\private\反编译\apk2java\dex2java\dex-translator-0.0.9.3\dex-translator-0.0.9.3\classes_dex2jar.jar
 * Qualified Name:     android.support.v4.util.DebugUtils
 * JD-Core Version:    0.6.2
 */