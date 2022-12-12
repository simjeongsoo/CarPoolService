package com.Easy.webcarpool.jwt.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;


@Component
public class TokenProvider {
    //--토큰의 생성, 토큰의 유효성 검증을 담당할 Token Provider--//

    private static final Logger logger = LoggerFactory.getLogger(TokenProvider.class);

//    private static final String AUTHORITIES_KEY = "auth";

    @Value("${jwt.secret}")
    private String jwtSecret;
    @Value("${jwt.token-validity-in-seconds}")
    private long jwtExpirationInMs;

//    private Key key;
/*
    public TokenProvider(
            @Value("${jwt.secret}") String secret,  // application.yml 설정값 호출
            @Value("${jwt.token-validity-in-seconds}") long tokenValidityInSeconds) {
        this.jwtSecret = secret;
        this.tokenValidityInMilliseconds = tokenValidityInSeconds * 1000;
    }*/

    /*@Override
    public void afterPropertiesSet() throws Exception {
        //-- InitializingBean의 afterPropertiesSet()를 오버라이드 --//

        // secret 값을 Base64 Decode 해서 key변수에 할당
        byte[] KeyBytes = Decoders.BASE64.decode(jwtSecret);

        // HMAC-SHA algorithms based on the specified key byte array
        this.key = Keys.hmacShaKeyFor(KeyBytes);
    }*/

//=========================================================================================

    private Key getSignKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

//=========================================================================================

    /**
     * Authentication 객체의 권한 정보를 이용해서 토큰을 생성하는 generateToken 메소드
     */
    public String generateToken(Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        Date now = new Date();
        Date expireDate = new Date(now.getTime() + jwtExpirationInMs);

        return Jwts.builder()
                .setSubject(Long.toString(userPrincipal.getId()))   // 권한 이름 설정
                .setIssuedAt(new Date())                            // 토큰 발급 시간
                .setExpiration(expireDate)                          // 유효기간 설정
                .signWith(getSignKey())                             // 서명 알고리즘 설정
                .compact();

    }

    public Long getUserIdFromJWT(String token) {

        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

		/*
		Claims claims = Jwts.parserBuilder()
			.setSigningKey(jwtSecret)
			.parseClaimsJws(token)
			.getBody();*/

        return Long.parseLong(claims.getSubject());
    }

    /*public String createToken(Authentication authentication) {

        // 권한정보 가져옴
        String authorities = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority) // 계정이 가지고 있는 권한 정보 리턴
                .collect(Collectors.joining(","));

        long now = (new Date()).getTime();

        // application.yml 에서 설정했던 만료시간을 설정
        Date validity = new Date(now + this.jwtExpirationInMs);

        // jwt token 생성후 리턴
        return Jwts.builder()
                .setSubject(authentication.getName())       // 권한 이름 설정
                .claim(AUTHORITIES_KEY, authorities)        // key-value 형식으로 이루어진 한 쌍의 정보 : Claim
                .signWith(key, SignatureAlgorithm.HS512)    // 서명 알고리즘 설정
                .setExpiration(validity)                    // 유효기간 설정
                .compact();
    }*/

//=========================================================================================
    /**
     * Token 에 담겨있는 정보를 이용해 Authentication 객체를 리턴하는 메소드
     * */
    /*public Authentication getAuthentication(String token) {

        Claims claims = Jwts
                .parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)      // token을 이용해 claims 생성
                .getBody();

        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))  // claims 에서 권한정보를 꺼냄
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        // org.springframework.security.core.userdetails.User;
        User principal = new User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }*/

//=========================================================================================
    /**
     * 토큰의 유효성 검증을 수행하는 validateToken 메소드
     * */
    public boolean validateToken(String token) {

        try {
            // 토큰을 파싱해보고 발생하는 익셉션들틀 캐치, 문제가 있으면 false, 정상이면 true
            Jwts
                    .parserBuilder()
                    .setSigningKey(getSignKey())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            logger.info("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            logger.info("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            logger.info("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            logger.info("JWT 토큰이 잘못 되었습니다.");
        }
        return false;
    }
//=========================================================================================

} // end class