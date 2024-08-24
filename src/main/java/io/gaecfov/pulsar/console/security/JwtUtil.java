package io.gaecfov.pulsar.console.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.crypto.SecretKey;
import lombok.experimental.UtilityClass;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author zhangqin
 */
@UtilityClass
public class JwtUtil {

  static final SecretKey secretKey = Jwts.SIG.HS256.key().build();

  public static String generateToken(UserDetails userDetails) {
    String roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority)
        .collect(Collectors.joining(","));
    return Jwts.builder()
        .subject(userDetails.getUsername())
        .claim("roles", roles)
        .issuedAt(new Date())
        .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
        .signWith(secretKey)
        .compact();
  }

  public static JwtUserInfo parseToken(String jwt) {
    Jws<Claims> jws = Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(jwt);
    Claims payload = jws.getPayload();
    String roles = payload.get("roles", String.class);
    List<SimpleGrantedAuthority> authorities = Arrays.stream(roles.split(","))
        .map(SimpleGrantedAuthority::new).toList();

    JwtUserInfo userInfo = new JwtUserInfo();
    userInfo.setUsername(payload.getSubject());
    userInfo.setAuthorities(authorities);
    return userInfo;
  }
}