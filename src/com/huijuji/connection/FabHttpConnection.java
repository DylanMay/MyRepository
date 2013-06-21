package com.fab.connection;

import android.text.TextUtils;
import com.fab.environment.FabEnvironment;
import com.fab.utils.FabSharedPrefs;
import com.fab.utils.exception.FabException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.BasicHttpContext;

public class FabHttpConnection
{
  private static final String TAG = "HttpConnection";
  private static BasicCookieStore cookieStore;
  private static HashMap<String, String> fabCookieHash = new HashMap();
  private static BasicHttpContext localContext;
  private DefaultHttpClient client;
  BasicHttpParams params = new BasicHttpParams();

  static
  {
    String[] arrayOfString = FabSharedPrefs.getFabCookies().split("<==>");
    int i = arrayOfString.length;
    for (int j = 0; ; j++)
    {
      if (j >= i)
        return;
      String str = arrayOfString[j];
      if (!TextUtils.isEmpty(str))
        fabCookieHash.put(str.split("=")[0], str);
    }
  }

  private FabHttpConnection()
  {
    this.params.setBooleanParameter("http.protocol.expect-continue", false);
    this.params.setBooleanParameter("http.connection.stalecheck", false);
    this.params.setIntParameter("http.socket.buffer-size", 8192);
    HttpProtocolParams.setVersion(this.params, HttpVersion.HTTP_1_1);
    HttpProtocolParams.setContentCharset(this.params, "UTF-8");
    this.client = new DefaultHttpClient(this.params);
    this.client.getConnectionManager().closeExpiredConnections();
  }

  public static FabHttpConnection getDefaultClient()
  {
    return new FabHttpConnection();
  }

  private HttpRequestBase getDefaultMethod(String paramString1, ArrayList<BasicNameValuePair> paramArrayList, String paramString2)
    throws FabException
  {
    Object localObject;
    try
    {
      StringBuilder localStringBuilder;
      String[] arrayOfString;
      int i;
      int j;
      if (paramString2.equalsIgnoreCase("POST"))
      {
        localObject = new HttpPost(new URI(paramString1));
        ((HttpRequestBase)localObject).setHeader("Content-type", "application/x-www-form-urlencoded");
        ((HttpRequestBase)localObject).setHeader("User-Agent", "Android");
        if (!TextUtils.isEmpty(FabSharedPrefs.getFabCookies()))
        {
          localStringBuilder = new StringBuilder();
          arrayOfString = FabSharedPrefs.getFabCookies().split("<==>");
          i = 0;
          j = arrayOfString.length;
        }
      }
      while (true)
      {
        if (i >= j)
        {
          ((HttpRequestBase)localObject).setHeader("Cookie", localStringBuilder.toString());
          if ((!TextUtils.isEmpty(FabEnvironment.reqAuth())) && (paramString1.contains(FabEnvironment.baseDomain())))
            ((HttpRequestBase)localObject).setHeader("Authorization", FabEnvironment.reqAuth());
          if ((paramArrayList == null) || (paramArrayList.isEmpty()))
            break label275;
          ((HttpPost)localObject).setEntity(new UrlEncodedFormEntity(paramArrayList, "UTF-8"));
          break label275;
          if (paramString2.equalsIgnoreCase("GET"))
          {
            localObject = new HttpGet(new URI(paramString1));
            break;
          }
          localObject = new HttpGet(new URI(paramString1));
          break;
        }
        localStringBuilder.append(arrayOfString[i].split("<@@>")[0] + "; ");
        i++;
      }
    }
    catch (URISyntaxException localURISyntaxException)
    {
      throw new FabException(localURISyntaxException);
    }
    catch (Exception localException)
    {
      throw new FabException(localException);
    }
    label275: return localObject;
  }

  private static BasicHttpContext getFabHttpContext()
  {
    String[] arrayOfString1;
    int i;
    int j;
    if ((cookieStore == null) || (localContext == null))
    {
      cookieStore = new BasicCookieStore();
      if (!TextUtils.isEmpty(FabSharedPrefs.getFabCookies()))
      {
        arrayOfString1 = FabSharedPrefs.getFabCookies().split("<==>");
        i = 0;
        j = arrayOfString1.length;
      }
    }
    label211: String str1;
    String str2;
    BasicClientCookie localBasicClientCookie1;
    while (true)
    {
      if (i >= j)
      {
        localContext = new BasicHttpContext();
        localContext.setAttribute("http.cookie-store", cookieStore);
        return localContext;
      }
      if (!TextUtils.isEmpty(arrayOfString1[i]));
      try
      {
        String[] arrayOfString2 = arrayOfString1[i].split("<@@>")[1].split("<##>");
        BasicClientCookie localBasicClientCookie2;
        if (arrayOfString2.length == 4)
        {
          localBasicClientCookie2 = new BasicClientCookie(arrayOfString2[0], arrayOfString2[1]);
          localBasicClientCookie2.setVersion(Integer.parseInt(arrayOfString2[3]));
          localBasicClientCookie2.setDomain(FabEnvironment.baseDomain());
          localBasicClientCookie2.setPath("/");
          if (!arrayOfString2[2].equals("false"))
            break label211;
        }
        for (boolean bool2 = false; ; bool2 = true)
        {
          localBasicClientCookie2.setSecure(bool2);
          localBasicClientCookie2.setAttribute("version", arrayOfString2[3]);
          localBasicClientCookie2.setAttribute("domain", FabEnvironment.baseDomain());
          cookieStore.addCookie(localBasicClientCookie2);
          i++;
          break;
        }
      }
      catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException)
      {
        do
        {
          fabCookieHash.clear();
          str1 = arrayOfString1[i].split("=")[0];
          str2 = arrayOfString1[i].split("=")[1].split(";")[0];
        }
        while ((!str1.equalsIgnoreCase("fpa")) && (!str1.equalsIgnoreCase("udv")) && (!str1.equalsIgnoreCase("tla")));
        localBasicClientCookie1 = new BasicClientCookie(str1, str2);
        localBasicClientCookie1.setVersion(0);
        localBasicClientCookie1.setDomain(FabEnvironment.baseDomain());
        localBasicClientCookie1.setPath("/");
        if (!str1.equalsIgnoreCase("tla"))
          break label455;
      }
    }
    boolean bool1 = true;
    label337: localBasicClientCookie1.setSecure(bool1);
    localBasicClientCookie1.setAttribute("version", "0");
    localBasicClientCookie1.setAttribute("domain", FabEnvironment.baseDomain());
    cookieStore.addCookie(localBasicClientCookie1);
    FabSharedPrefs.clearFabCookies();
    StringBuilder localStringBuilder = new StringBuilder(String.valueOf(arrayOfString1[i])).append("<@@>").append(str1).append("<##>").append(str2).append("<##>");
    if (str1.equalsIgnoreCase("tla"));
    for (String str3 = "true"; ; str3 = "false")
    {
      FabSharedPrefs.setFabCookies(str3 + "<##>0<==>");
      break;
      label455: bool1 = false;
      break label337;
    }
  }

  public void closeConnection()
  {
    this.client.getConnectionManager().closeExpiredConnections();
  }

  public InputStream executeRequest(String paramString1, HashMap<String, String> paramHashMap1, HashMap<String, String> paramHashMap2, String paramString2)
    throws FabException
  {
    InputStream localInputStream = null;
    ArrayList localArrayList = new ArrayList();
    Iterator localIterator3;
    if (paramHashMap2 != null)
      localIterator3 = paramHashMap2.keySet().iterator();
    while (true)
    {
      if (!localIterator3.hasNext());
      try
      {
        HttpResponse localHttpResponse = getThreadSafeClient().execute(getDefaultMethod(paramString1, localArrayList, paramString2), getFabHttpContext());
        HttpEntity localHttpEntity = localHttpResponse.getEntity();
        if ((!paramString1.contains("cdn1." + FabEnvironment.baseUrl() + "/")) && ((paramString1.contains("." + FabEnvironment.baseUrl() + "/")) || (paramString1.contains("/" + FabEnvironment.baseUrl() + "/"))))
        {
          List localList = cookieStore.getCookies();
          if (!localList.isEmpty())
          {
            localIterator1 = localList.iterator();
            if (localIterator1.hasNext())
              break label361;
            localStringBuilder = new StringBuilder();
            localIterator2 = fabCookieHash.keySet().iterator();
            if (localIterator2.hasNext())
              break label545;
            FabSharedPrefs.setFabCookies(localStringBuilder.toString());
          }
        }
        localInputStream = localHttpEntity.getContent();
        int i = localHttpResponse.getStatusLine().getStatusCode();
        if ((i < 200) || (i > 207))
          throw new FabException("Unable to contact server" + localHttpResponse.getStatusLine().getReasonPhrase());
      }
      catch (HttpResponseException localHttpResponseException)
      {
        while (true)
        {
          Iterator localIterator1;
          return localInputStream;
          String str2 = (String)localIterator3.next();
          BasicNameValuePair localBasicNameValuePair = new BasicNameValuePair(str2, (String)paramHashMap2.get(str2));
          localArrayList.add(localBasicNameValuePair);
          break;
          Cookie localCookie = (Cookie)localIterator1.next();
          if ((localCookie.getDomain().contains(FabEnvironment.baseDomain())) && (!localCookie.getName().contains("fab_session")))
            fabCookieHash.put(localCookie.getName(), localCookie.getName() + "=" + localCookie.getValue() + "; domain=" + localCookie.getDomain() + "<@@>" + localCookie.getName() + "<##>" + localCookie.getValue() + "<##>" + localCookie.isSecure() + "<##>" + localCookie.getVersion());
        }
      }
      catch (ClientProtocolException localClientProtocolException)
      {
        while (true)
        {
          Iterator localIterator2;
          throw new FabException(localClientProtocolException);
          str1 = (String)localIterator2.next();
          if ((!paramString1.contains("stay-signed-in/?enable=0")) || (!"tla".equalsIgnoreCase(str1)))
            break;
          fabCookieHash.remove(str1);
        }
      }
      catch (IOException localIOException)
      {
        while (true)
        {
          StringBuilder localStringBuilder;
          String str1;
          throw new FabException(localIOException);
          localStringBuilder.append((String)fabCookieHash.get(str1));
          localStringBuilder.append("<==>");
        }
      }
      catch (Exception localException)
      {
        label361: localException.toString();
        label545: throw new FabException(localException);
      }
    }
  }

  public HttpClient getClient()
  {
    return this.client;
  }

  public HttpClient getThreadSafeClient()
  {
    ClientConnectionManager localClientConnectionManager = this.client.getConnectionManager();
    HttpParams localHttpParams = this.client.getParams();
    this.client = new DefaultHttpClient(new ThreadSafeClientConnManager(localHttpParams, localClientConnectionManager.getSchemeRegistry()), localHttpParams);
    return this.client;
  }
}

/* Location:           C:\Documents and Settings\Administrator\桌面\private\反编译\apk2java\dex2java\dex-translator-0.0.9.3\dex-translator-0.0.9.3\classes_dex2jar.jar
 * Qualified Name:     com.fab.connection.FabHttpConnection
 * JD-Core Version:    0.6.2
 */