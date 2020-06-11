package com.tenfar.yiyi.filter;

import com.tenfar.yiyi.service.JwtUserDetailsService;
import com.tenfar.yiyi.util.JwtTokenUtils;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.tenfar.yiyi.common.Constant.*;

/**
 * @author tenfar
 */
@Component
public class AuthorizationFilter extends OncePerRequestFilter {
    @Resource
    private JwtUserDetailsService jwtUserDetailsService;
    @Resource
    private JwtTokenUtils jwtTokenUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        final String requestTokenHeader = request.getHeader(ATHORIZATION);
        String username = null;
        String jwtToken = null;
        //去掉Bearer开头
        if (requestTokenHeader != null && requestTokenHeader.startsWith(TOKEN_HEADER)) {
            jwtToken = requestTokenHeader.substring(7);
            try {
                username = jwtTokenUtils.getUsernameFromToken(jwtToken);
            } catch (IllegalArgumentException e) {
                System.out.println("无法获取 JWT Token");
            } catch (ExpiredJwtException e) {
                System.out.println("JWT Token 已经过期");
            }
        } else {
            logger.warn("JWT Token 不是Bearer开头");
        }
        //校验token
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = jwtUserDetailsService.getByUsername(username);
            //如果token校验成功，配置SpringSecurty设置校验
            if (jwtTokenUtils.validateToken(jwtToken, userDetails)) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                //配置用户通过校验
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        chain.doFilter(request, response);
    }
}
