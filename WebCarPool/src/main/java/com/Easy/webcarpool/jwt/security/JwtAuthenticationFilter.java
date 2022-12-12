package com.Easy.webcarpool.jwt.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {
    //--JWT를 위한 커스텀 필터를 만들기 위한 JwtFilter 클래스--//

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
//    public static final String AUTHORIZATION_HEADER = "Authorization";

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwt = getJwtFromRequest(request);

            if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
                Long userId = tokenProvider.getUserIdFromJWT(jwt);

                UserDetails userDetails = customUserDetailsService.loadUserById(userId);

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception ex) {
//            logger.error("Could not set user authentication in security context", ex);
            logger.error("Security Context에서 사용자 인증을 설정할 수 없습니다.", ex);
        }

        filterChain.doFilter(request, response);
    }


    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
    }


    /*@Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //--doFilter는 jwt토큰의 인증 정보를 현재 실행중인 SecurityContext에 저장하는 역할 수행--//
        // 요청 정보 토큰 매칭 확인

        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;


        // resolveToken() 에서 토큰정보를 받아옴
        String jwt = resolveToken(httpServletRequest);

        // RequestURI 정보
        String requestURI = httpServletRequest.getRequestURI();

        // 받아온 jwt 토큰의 유효성 검사
        if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
            // 토큰이 정상이라면 authentication 정보를 받아와
            Authentication authentication = tokenProvider.getAuthentication(jwt);

            // SecurityContext에 저장
            SecurityContextHolder.getContext().setAuthentication(authentication);
            logger.debug("Security Context에 '{}' 인증정보를 저장했습니다, uri: {}", authentication.getName(), requestURI);
        } else {
            logger.debug("유효한 JWT 토큰이 없습니다, uri: {}", requestURI);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    private String resolveToken(HttpServletRequest request) {
        //--Request Header에서 토큰 정보를 꺼내오기 위한 resolveToken 메소드 추가--//

        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);

        // 토큰이 비어있지 않거나, 토큰이 "Bearer "로 시작한다면
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        } else {
            logger.debug("토큰이 비어있음");
        }

        return null;
    }*/
}
