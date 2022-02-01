package com.math.weakness.filter;

import com.math.weakness.domain.Role;
import com.math.weakness.oauth.service.JwtService;
import io.jsonwebtoken.Claims;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class AdminRequestFilter implements Filter {

    private final JwtService jwtService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletResponse res = (HttpServletResponse) response;
        Claims claims = jwtService.parseJwt(request.getParameter("accessToken"));
        String role = claims.get("role").toString();
        try {
            role.equals(Role.ADMIN.toString());
        } catch (Exception e) {
            res.sendError(401, "You are not admin");
        }
        chain.doFilter(request, response);
    }
}
