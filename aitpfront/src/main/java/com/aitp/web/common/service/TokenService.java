package com.aitp.web.common.service;

import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;

@Component
public class TokenService {

    private final Logger log = LoggerFactory.getLogger(TokenService.class);

    private static final String AUTHORITIES_SUBJEECT = "aitp";
    private static final String AUTHORITIES_KEY = "auth";

    private final Base64.Encoder encoder = Base64.getEncoder();

    @Value("${jwt.secret.key}")
    private String secretKey;
    @Value("${jwt.secret.token.validity.milliseconds}")
    private Long tokenValidityInMilliseconds;
    @Value("${jwt.secret.token.validity.milliseconds.remember}")
    private Long tokenValidityInMillisecondsForRememberMe;



    @PostConstruct
    public void init() {
        this.secretKey = encoder.encodeToString(secretKey.getBytes(StandardCharsets.UTF_8));


    }

    public String createToken(Object authorities, boolean rememberMe) {

        long now = (new Date()).getTime();
        Date validity;
        if (rememberMe) {
            validity = new Date(now + this.tokenValidityInMillisecondsForRememberMe*1000);
        } else {
            validity = new Date(now + this.tokenValidityInMilliseconds*1000);
        }
         log.info("expiration time:{}",validity);
        return Jwts.builder()
            .setSubject(AUTHORITIES_SUBJEECT)
            .claim(AUTHORITIES_KEY, authorities)
            .signWith(SignatureAlgorithm.HS512, secretKey)
            .setExpiration(validity)
            .compact();
    }


    public Object getAuthentication(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();

        return claims.get(AUTHORITIES_KEY).toString();
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            log.info("Invalid JWT signature.");
            log.trace("Invalid JWT signature trace: {}", e);
        } catch (MalformedJwtException e) {
            log.info("Invalid JWT token.");
            log.trace("Invalid JWT token trace: {}", e);
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT token.");
            log.trace("Expired JWT token trace: {}", e);
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT token.");
            log.trace("Unsupported JWT token trace: {}", e);
        } catch (IllegalArgumentException e) {
            log.info("JWT token compact of handler are invalid.");
            log.trace("JWT token compact of handler are invalid trace: {}", e);
        }
        return false;
    }

}
