

package com.TaskManage.Security;

import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

import com.TaskManage.Entity.UserAuth;
import com.TaskManage.Enum.Permission;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import java.security.Key;

public class JWTUtil {

    private final Key key;
    private final long expireToken = 1000 * 60 * 60; // 1 hour

    public JWTUtil() {
        String secret = System.getenv("JWT_SECRET");

        if (secret == null || secret.isEmpty()) {
            secret = "ReplaceThisWithVerySecretKey1234567890";
        }

        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String generateToken(UserAuth user) {

        Set<Permission> permissions =
                RolePermissionConfig.getRoleBasedPermissions().get(user.getRole());

        return Jwts.builder()
                .setSubject(user.getUserOfficialEmail())
                .claim("role", user.getRole().name())
                .claim("permissions",
                        permissions.stream().map(Enum::name).collect(Collectors.toList()))
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expireToken))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String getUserEmail(String token) {
        return getClaims(token).getSubject();
    }
}
