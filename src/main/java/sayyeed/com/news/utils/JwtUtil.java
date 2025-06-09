package sayyeed.com.news.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import sayyeed.com.news.dtos.JwtDTO;
import sayyeed.com.news.enums.profile.ProfileRoleEnum;
import sayyeed.com.news.exceptions.AppBadException;


import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JwtUtil {
    private static final int tokenLiveTime = 1000 * 3600 * 24; // 1-day
    private static final String secretKey = "u8bX2k9pQw4z7sV1yT3r6wA8eF5hJ2lN0mQpRsTuVzxYzAbCdEfGhIjKlMnOpQrStU";


    public static String encode(String username, List<ProfileRoleEnum> roles) {
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("username", username);
        extraClaims.put("role", roles);

        return Jwts
                .builder()
                .claims(extraClaims)
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + tokenLiveTime))
                .signWith(getSignInKey())
                .compact();
    }

    public static JwtDTO decode(String token) {
        Claims claims = Jwts
                .parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        String username = claims.getSubject();
        List<ProfileRoleEnum> roles = (List<ProfileRoleEnum>) claims.get("role");
        JwtDTO jwtDTO = new JwtDTO();
        jwtDTO.setUsername(username);
        jwtDTO.setRoles(roles);
        return jwtDTO;
    }

    /** Register JWT **/
    public static String encodeForRegister(String username, String code) {
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("username", username);
        extraClaims.put("code", code);

        return Jwts
                .builder()
                .claims(extraClaims)
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + tokenLiveTime))
                .signWith(getSignInKey())
                .compact();
    }

    public static JwtDTO decodeForRegister(String token) {
        try {
            Claims claims = Jwts
                    .parser()
                    .verifyWith(getSignInKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
            String username = (String) claims.get("username");
            String code = (String) claims.get("code");
            return new JwtDTO(username, code);
        } catch (Exception e) {
            throw new AppBadException("Something went wrong");
        }
    }

    private static SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}