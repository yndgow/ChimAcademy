package kr.co.ChimAcademy.util;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

@Component
public class IpAddress {

	// 실제 아이피 주소 가져오기
	public String getIpAddr(HttpServletRequest req) {
		String ipAddress = req.getHeader("X-Forwarded-For");
	    if (ipAddress == null || ipAddress.isEmpty()) {
	        ipAddress = req.getRemoteAddr();
	    } else {
	        ipAddress = ipAddress.split(",")[0].trim();
	    }
	    return ipAddress;
	}
	

}
