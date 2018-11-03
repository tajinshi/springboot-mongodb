package com.springboot.springtest.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;

public class IPUtil {

	private static HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

	/**
	 * 通过HttpServletRequest返回IP地址
	 * @return ip String
	 * @throws Exception
	 */
	public static String getIpAddr() throws Exception {
	    String ip = request.getHeader("x-forwarded-for");
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	        ip = request.getHeader("Proxy-Client-IP");
	    }
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	        ip = request.getHeader("WL-Proxy-Client-IP");
	    }
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	        ip = request.getHeader("HTTP_CLIENT_IP");
	    }
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	        ip = request.getHeader("HTTP_X_FORWARDED_FOR");
	    }
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	        ip = request.getRemoteAddr();
	    }
	    return ip;
	}

	 /**
	 * 通过IP地址获取MAC地址
	 * @param ip String,127.0.0.1格式
	 * @return mac String
	 * @throws Exception
	 */
	public static String getMACAddress(String ip) throws Exception {
	    String line = "";
	    String macAddress = "";
	    final String MAC_ADDRESS_PREFIX = "MAC Address = ";
	    final String LOOPBACK_ADDRESS = "127.0.0.1";
	    //如果为127.0.0.1,则获取本地MAC地址。
	    if (LOOPBACK_ADDRESS.equals(ip)) {
	        InetAddress inetAddress = InetAddress.getLocalHost();
	        //貌似此方法需要JDK1.6。
	        byte[] mac = NetworkInterface.getByInetAddress(inetAddress).getHardwareAddress();
	        //下面代码是把mac地址拼装成String
	        StringBuilder sb = new StringBuilder();
	        for (int i = 0; i < mac.length; i++) {
	            if (i != 0) {
	                sb.append("-");
	            }
	            //mac[i] & 0xFF 是为了把byte转化为正整数
	            String s = Integer.toHexString(mac[i] & 0xFF);
	            sb.append(s.length() == 1 ? 0 + s : s);
	        }
	        //把字符串所有小写字母改为大写成为正规的mac地址并返回
	        macAddress = sb.toString().trim().toUpperCase();
	        return macAddress;
	    }
	    //获取非本地IP的MAC地址
	    try {
	        Process p = Runtime.getRuntime().exec("nbtstat -A " + ip);
	        InputStreamReader isr = new InputStreamReader(p.getInputStream());
	        BufferedReader br = new BufferedReader(isr);
	        while ((line = br.readLine()) != null) {
	            if (line != null) {
	                int index = line.indexOf(MAC_ADDRESS_PREFIX);
	                if (index != -1) {
	                    macAddress = line.substring(index + MAC_ADDRESS_PREFIX.length()).trim().toUpperCase();
	                }
	            }
	        }
	        br.close();
	    } catch (IOException e) {
	        e.printStackTrace(System.out);
	    }
	    return macAddress;
	}

	/**
	 * 通过IP获取地址(需要联网，调用淘宝的IP库)
	 *
	 * @param ip
	 * @return
	 */
	public static String getIpInfo(String ip) {
		if (ip.equals("本地")) {
			ip = "127.0.0.1";
		}
		String info = "";
		try {
			URL url = new URL("http://ip.taobao.com/service/getIpInfo.php?ip=" + ip);
			HttpURLConnection htpcon = (HttpURLConnection) url.openConnection();
			htpcon.setRequestMethod("GET");
			htpcon.setDoOutput(true);
			htpcon.setDoInput(true);
			htpcon.setUseCaches(false);

			InputStream in = htpcon.getInputStream();
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
			StringBuffer temp = new StringBuffer();
			String line = bufferedReader.readLine();
			while (line != null) {
				temp.append(line).append("\r\n");
				line = bufferedReader.readLine();
			}
			bufferedReader.close();
			JSONObject obj = (JSONObject) JSON.parse(temp.toString());
			if (obj.getIntValue("code") == 0) {
				JSONObject data = obj.getJSONObject("data");
				info += data.getString("country") + " ";
				info += data.getString("region") + " ";
				info += data.getString("city") + " ";
				info += data.getString("isp");
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (ProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return info;
	}

}
