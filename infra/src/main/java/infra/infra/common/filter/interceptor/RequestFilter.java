//package infra.infra.filter.interceptor;
//
//import infra.infra.filter.config.JwtConfig;
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.io.Decoders;
//import io.jsonwebtoken.security.Keys;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.extern.slf4j.Slf4j;
//import org.slf4j.MDC;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//
//@Slf4j
//@Component
//public class RequestFilter extends OncePerRequestFilter {
//
//    private final JwtConfig jwtConfig;
//
//    public RequestFilter(JwtConfig jwtConfig) {
//        this.jwtConfig = jwtConfig;
//    }
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        String token = request.getHeader("Authorization");
//
//        if (token == null || !token.startsWith(jwtConfig.getTokenPrefix())) {
//            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
//            return;
//        }
//
//        token = token.replace(jwtConfig.getTokenPrefix(), "");
//        log.info("Token: {}", token);
//
//        String userId = extractUserId(token);
//        if (userId == null) {
//            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
//            return;
//        }
//        MDC.put("userId", userId);
//        filterChain.doFilter(request, response);
//    }
//
//    private String extractUserId(String token) {
//        Claims claims = null;
//        String userId = null;
//        try {
//            claims = Jwts
//                    .parserBuilder()
//                    .setSigningKey(Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtConfig.getSecretKey())))
//                    .build()
//                    .parseClaimsJws(token)
//                    .getBody();
//            userId = claims.getSubject();
//        } catch (Exception e) {
//            log.error("Error while decoding token", e);
//        }
//        return userId;
//    }
//}
