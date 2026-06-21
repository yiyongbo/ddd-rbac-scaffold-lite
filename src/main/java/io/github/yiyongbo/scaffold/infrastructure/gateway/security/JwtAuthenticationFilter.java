package io.github.yiyongbo.scaffold.infrastructure.gateway.security;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.yiyongbo.scaffold.common.response.CommonResponseCode;
import io.github.yiyongbo.scaffold.common.response.Result;
import io.github.yiyongbo.scaffold.common.security.LoginUser;
import io.github.yiyongbo.scaffold.domain.auth.model.valueobject.TokenPayloadValueObject;
import io.github.yiyongbo.scaffold.domain.common.gateway.TokenGateway;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * JWT 认证过滤器
 *
 * @author kidd
 * @since 2026/6/21 15:22
 */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String AUTHORIZATION_HEADER = "Authorization";

    private static final String BEARER_PREFIX = "Bearer ";

    private final TokenGateway tokenGateway;

    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = resolveToken(request);

        if (StrUtil.isBlank(token)) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            TokenPayloadValueObject payload = tokenGateway.parseAccessToken(token);

            LoginUser loginUser = LoginUser.builder()
                    .userId(payload.getUserId())
                    .username(payload.getUsername())
                    .build();

            List<SimpleGrantedAuthority> authorities = loginUser.getPermissions().stream().map(SimpleGrantedAuthority::new).toList();
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(loginUser, null, authorities);

            SecurityContextHolder.getContext().setAuthentication(authentication);


            filterChain.doFilter(request, response);
        } catch (Exception e) {
            SecurityContextHolder.clearContext();
            writeUnauthorizedResponse(response);
        } finally {
            SecurityContextHolder.clearContext();
        }

    }

    private String resolveToken(HttpServletRequest request) {
        String authorization = request.getHeader(AUTHORIZATION_HEADER);

        if (StrUtil.isBlank(authorization)) {
            return null;
        }

        if (!authorization.startsWith(BEARER_PREFIX)) {
            return null;
        }

        return authorization.substring(BEARER_PREFIX.length());
    }

    private void writeUnauthorizedResponse(HttpServletResponse response) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        Result<Void> result = Result.fail(CommonResponseCode.UNAUTHORIZED);
        response.getWriter().write(objectMapper.writeValueAsString(result));
    }
}
