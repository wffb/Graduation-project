package org.example.bsproject.common.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.example.bsproject.dto.Detail.LoginUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtUtil.class);
    private static final String CLAIM_KEY_USERNAME = "sub";
    private static final String CLAIM_KEY_CREATED = "created";

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    /**
     * 生成JWT的token
     */
    private String generateToken(Map<String,Object> claims){
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(generateExpirationDate())
                .setNotBefore(new Date(System.currentTimeMillis()))
                .signWith(SignatureAlgorithm.HS512,secret)
                .compact();
    }
    //为不同身份生成不同长度的token
    private String generateToken(Map<String,Object> claims,boolean isAdmin){
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(generateExpirationDate(isAdmin))
                .setNotBefore(new Date(System.currentTimeMillis()))
                .signWith(SignatureAlgorithm.HS512,secret)
                .compact();
    }

    /**
     * 从token中获取JWT中的负载
     */
    private Claims getClaimsFromToken(String token){
        Claims claims = null;
        try {
            claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            LOGGER.info("JWT格式验证失败:{}",token);
        }
        return claims;
    }

    /**
     * 生成token的过期时间
     */
    private Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + expiration * 1000);
    }

    private Date generateExpirationDate(boolean isAdmin) {
        return new Date(System.currentTimeMillis() + expiration * 2 * 1000);
    }

    /**
     * 从token中获取登录用户信息(此时为id)
     */
    public String getUsernameFromToken(String token) {
        String username;
        try {
            Claims claims = getClaimsFromToken(token);
            username =  claims.getSubject();
//             username= (String) claims.get("sub");

        } catch (Exception e) {
            username = null;
        }
        return username;
    }

    public Date getIatFromToken(String token){
        Date iat;
        try {
            Claims claims = getClaimsFromToken(token);
            iat=claims.getIssuedAt();
        }catch (Exception e) {
            iat = null;
        }
        return iat;
    }

    /**
     * 从token中获取管理员标识
     */
    public Boolean getIsAdminFromToken(String token) {
        Boolean isAdmin;
        try {
            Claims claims = getClaimsFromToken(token);
            isAdmin =  (Boolean)claims.get("isAdmin");
        } catch (Exception e) {
            isAdmin = null;
        }
        return isAdmin;
    }

    /**
     * 验证token是否还有效
     *
     * @param token       客户端传入的token
     * @param userDetails 从数据库中查询出来的用户信息
     */
//    public boolean validateToken(String token, UserDetails userDetails) {
//        String username = getUserNameFromToken(token);
//        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
//    }


    /**
     * 验证token是否还有效
     *
     * @param token       客户端传入的token
     * @param loginUser  从数据库中查询出来的用户信息
     */
    public boolean validateToken(String token, LoginUser loginUser) {
        String username =getUsernameFromToken(token);
        return username.equals(loginUser.getUser().getUsername()) && !isTokenExpired(token);
    }

    /**
     * 判断token是否已经失效
     */
    private boolean isTokenExpired(String token) {
        Date expiredDate = getExpiredDateFromToken(token);
        return expiredDate.before(new Date());
    }

    /**
     * 从token中获取过期时间
     */
    private Date getExpiredDateFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return claims.getExpiration();
    }

    /**
     * 根据用户信息生成token-不能太短
     */
    public String generateToken(String userId) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_USERNAME, userId);
        claims.put("isAdmin",true);
        return generateToken(claims);
    }

    public String generateToken(UserDetails userDetails,boolean isAdmin) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_USERNAME, userDetails.getUsername());
        claims.put(CLAIM_KEY_CREATED, new Date());
        claims.put("isAdmin",isAdmin);
        return generateToken(claims);
    }



    public String generateTokenDefault(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_USERNAME, userDetails.getUsername());
        claims.put(CLAIM_KEY_CREATED, new Date());
        claims.put("isAdmin",false);
        return generateToken(claims);
    }

    /**
     * 判断token是否可以被刷新
     */
    public boolean canRefresh(String token) {
        return !isTokenExpired(token);
    }

    /**
     * 刷新token
     */
    public String refreshToken(String token) {
        Claims claims = getClaimsFromToken(token);
        claims.put(CLAIM_KEY_CREATED, new Date());
        return generateToken(claims);
    }
}
