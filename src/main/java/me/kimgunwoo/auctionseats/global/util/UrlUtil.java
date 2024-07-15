package me.kimgunwoo.auctionseats.global.util;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

@Component
public class UrlUtil {

    public String getCurrentServerUrl(HttpServletRequest request) {
        String serverName = request.getServerName();
        int serverPort = request.getServerPort();

        String protocol = request.getScheme();
        StringBuilder sb = new StringBuilder();
        sb.append(protocol);
        sb.append("://");
        sb.append(serverName);
        sb.append(serverPort);
        return sb.toString();
    }
}