package pe.edu.cibertec.apibancocibertec.security;

import io.jsonwebtoken.*;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FiltroJWTAuthorization extends OncePerRequestFilter {
    private final String CLAVE ="@Cibertec2024";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
            if(utilizaToken(request)){
                Claims claims = validateToken(request);
                if(claims.get("authorities") != null){
                    cargarAutorizaciones(claims);
                }else{
                    SecurityContextHolder.clearContext();
                }
            }else{
                SecurityContextHolder.clearContext();
            }
            filterChain.doFilter(request, response);
        }catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException ex){
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            ((HttpServletResponse) response).sendError(HttpServletResponse.SC_FORBIDDEN, ex.getMessage());
        }
    }

    private void cargarAutorizaciones(Claims claims){
        List<String> autorizaciones = (List<String>) claims.get("autorizaciones");
        UsernamePasswordAuthenticationToken authenticationToken
                = new UsernamePasswordAuthenticationToken(
                        claims.getSubject(),
                null,
                autorizaciones.stream().map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList())
        );
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }

    private Claims validateToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization")
                .replace("Bearer ", "");
        return Jwts.parser().setSigningKey(CLAVE.getBytes()).parseClaimsJws(token).getBody();
    }

    private boolean utilizaToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token == null && !token.startsWith("Bearer ")) {
            return false;
        }
        return true;
    }
}
