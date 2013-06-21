package com.fab.utils;

import java.io.InputStream;
import org.json.JSONException;
import org.json.JSONObject;

public class FabParser
{
  // ERROR //
  public static String convertStreamToString(InputStream paramInputStream)
  {
    // Byte code:
    //   0: aload_0
    //   1: ifnull +100 -> 101
    //   4: new 15	java/io/BufferedReader
    //   7: dup
    //   8: new 17	java/io/InputStreamReader
    //   11: dup
    //   12: aload_0
    //   13: invokespecial 20	java/io/InputStreamReader:<init>	(Ljava/io/InputStream;)V
    //   16: invokespecial 23	java/io/BufferedReader:<init>	(Ljava/io/Reader;)V
    //   19: astore_1
    //   20: new 25	java/lang/StringBuilder
    //   23: dup
    //   24: invokespecial 26	java/lang/StringBuilder:<init>	()V
    //   27: astore_2
    //   28: aload_1
    //   29: invokevirtual 30	java/io/BufferedReader:readLine	()Ljava/lang/String;
    //   32: astore 8
    //   34: aload 8
    //   36: ifnonnull +16 -> 52
    //   39: aload_0
    //   40: invokevirtual 35	java/io/InputStream:close	()V
    //   43: aload_2
    //   44: invokevirtual 38	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   47: astore 7
    //   49: aload 7
    //   51: areturn
    //   52: aload_2
    //   53: new 25	java/lang/StringBuilder
    //   56: dup
    //   57: aload 8
    //   59: invokestatic 44	java/lang/String:valueOf	(Ljava/lang/Object;)Ljava/lang/String;
    //   62: invokespecial 47	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   65: ldc 49
    //   67: invokevirtual 53	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   70: invokevirtual 38	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   73: invokevirtual 53	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   76: pop
    //   77: goto -49 -> 28
    //   80: astore 5
    //   82: aload_0
    //   83: invokevirtual 35	java/io/InputStream:close	()V
    //   86: goto -43 -> 43
    //   89: astore 6
    //   91: goto -48 -> 43
    //   94: astore_3
    //   95: aload_0
    //   96: invokevirtual 35	java/io/InputStream:close	()V
    //   99: aload_3
    //   100: athrow
    //   101: ldc 55
    //   103: astore 7
    //   105: goto -56 -> 49
    //   108: astore 4
    //   110: goto -11 -> 99
    //   113: astore 10
    //   115: goto -72 -> 43
    //
    // Exception table:
    //   from	to	target	type
    //   28	34	80	java/io/IOException
    //   52	77	80	java/io/IOException
    //   82	86	89	java/io/IOException
    //   28	34	94	finally
    //   52	77	94	finally
    //   95	99	108	java/io/IOException
    //   39	43	113	java/io/IOException
  }

  public static JSONObject parseJSONResponse(InputStream paramInputStream)
  {
    String str = convertStreamToString(paramInputStream);
    Object localObject1 = null;
    if (str != null);
    try
    {
      JSONObject localJSONObject = new JSONObject(str);
      localObject1 = localJSONObject;
      for (Object localObject2 = localObject1; ; localObject2 = null)
        return localObject2;
    }
    catch (JSONException localJSONException)
    {
      while (true)
        localJSONException.printStackTrace();
    }
  }
}

/* Location:           C:\Documents and Settings\Administrator\桌面\private\反编译\apk2java\dex2java\dex-translator-0.0.9.3\dex-translator-0.0.9.3\classes_dex2jar.jar
 * Qualified Name:     com.fab.utils.FabParser
 * JD-Core Version:    0.6.2
 */