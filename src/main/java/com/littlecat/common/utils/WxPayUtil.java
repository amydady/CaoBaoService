package com.littlecat.common.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.SignatureException;

import org.apache.commons.codec.digest.DigestUtils;

import com.littlecat.cbb.utils.StringUtil;

public class WxPayUtil
{
	/**
	 * 签名字符串
	 * 
	 * @param text需要签名的字符串
	 * @param key
	 *            密钥
	 * @param input_charset编码格式
	 * @return 签名结果
	 */
	public static String sign(String text, String input_charset)
	{
		return DigestUtils.md5Hex(getContentBytes(text, input_charset));
	}

	/**
	 * @param content
	 * @param charset
	 * @return
	 * @throws SignatureException
	 * @throws UnsupportedEncodingException
	 */
	private static byte[] getContentBytes(String content, String charset)
	{
		if (StringUtil.isEmpty(charset))
		{
			return content.getBytes();
		}

		try
		{
			return content.getBytes(charset);
		}
		catch (UnsupportedEncodingException e)
		{
			throw new RuntimeException("MD5签名过程中出现错误,指定的编码集有误,目前指定的编码集是:" + charset);
		}
	}

	/**
	 * 
	 * @param requestUrl请求地址
	 * @param requestMethod请求方法
	 * @param outputStr参数
	 */
	public static String httpRequest(String requestUrl, String requestMethod, String outputStr)
	{
		// 创建SSLContext
		StringBuffer buffer = null;
		try
		{
			URL url = new URL(requestUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod(requestMethod);
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.connect();
			// 往服务器端写内容
			if (null != outputStr)
			{
				OutputStream os = conn.getOutputStream();
				os.write(outputStr.getBytes("utf-8"));
				os.close();
			}
			// 读取服务器端返回的内容
			InputStream is = conn.getInputStream();
			InputStreamReader isr = new InputStreamReader(is, "utf-8");
			BufferedReader br = new BufferedReader(isr);
			buffer = new StringBuffer();
			String line = null;
			while ((line = br.readLine()) != null)
			{
				buffer.append(line);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return buffer.toString();
	}
}
