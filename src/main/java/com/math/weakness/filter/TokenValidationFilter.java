package com.math.weakness.filter;


import com.math.weakness.oauth.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.security.SignatureException;
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
public class TokenValidationFilter implements Filter {

    private final JwtService jwtService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletResponse res = (HttpServletResponse) response;
        try {
            String accessToken = request.getParameter("accessToken");
            if (accessToken.equals("guest")) {
                log.info("guest request");
            } else {
                Claims claims = jwtService.parseJwt(accessToken);
                log.info("{} request", claims.get("name"));
            }
        } catch (ExpiredJwtException e) {
            log.info("ExpiredJwtException is caught by TokenValidationFilter");
            res.sendError(401, "This token has been expired");
        } catch (SignatureException e) {
            res.sendError(401, "This token has been forged");
        } catch (JwtException e) {
            res.sendError(401, "An error occurred for some reason");
        }
        chain.doFilter(request, response);
    }
}
