package com.idv.yen.config;

import jakarta.servlet.http.Cookie;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.MapSessionRepository;
import org.springframework.session.config.annotation.web.http.EnableSpringHttpSession;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;

import java.util.concurrent.ConcurrentHashMap;

@Configuration
@EnableSpringHttpSession
public class SessionConfig {

    /**
     * Set properties of the cookie serializer
     * */
    @Bean
    public CookieSerializer cookieSerializer() {
        DefaultCookieSerializer cookieSerializer = new DefaultCookieSerializer();
        cookieSerializer.setCookieName("JSESSIONID");

        cookieSerializer.setDomainNamePattern("^.+?\\.(\\w+\\.[a-z]+)$");

        cookieSerializer.setCookiePath("/");

        cookieSerializer.setUseHttpOnlyCookie(false);

        cookieSerializer.setCookieMaxAge(60*60*24);

        return cookieSerializer;
    }

    /**
     * register serializer
     * */
    @Bean
    public MapSessionRepository mapSessionRepository() {
        return new MapSessionRepository(new ConcurrentHashMap<>());
    }
}
