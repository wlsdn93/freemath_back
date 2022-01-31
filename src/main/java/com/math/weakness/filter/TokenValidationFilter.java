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

@RequiredArgsConstructor
public class TokenValidationFilter implements Filter {

    private final JwtService jwtService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        try {
            Claims claims = jwtService.parseJwt(request.getParameter("accessToken"));
            String name = claims.get("name").toString();
        } catch (ExpiredJwtException e) {
            ((HttpServletResponse) response).sendError(401, "This token has been expired");
        } catch (SignatureException e) {
            ((HttpServletResponse) response).sendError(401, "This token has been forged");
        } catch (JwtException e) {
            ((HttpServletResponse) response).sendError(401, "An error occurred for some reason");
        }
    }
}
