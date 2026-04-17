package com.app.quantitymeasurement.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Handler for successful OAuth2 login. Generates a JWT and redirects back to the frontend.
 * Uses an explicit Location header to prevent Spring Cloud Gateway from rewriting the redirect URL.
 */
@Component
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Value("${app.oauth2.redirectUri}")
    private String authorizedRedirectUri;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {

        if (response.isCommitted()) {
            logger.debug("Response has already been committed. Unable to redirect.");
            return;
        }

        // Correct signature: parent only accepts HttpServletRequest
        super.clearAuthenticationAttributes(request);

        String token = tokenProvider.generateToken(authentication);

        // Build the absolute frontend redirect URL
        String targetUrl = UriComponentsBuilder.fromUriString(authorizedRedirectUri)
                .queryParam("token", token)
                .build().toUriString();

        // Use explicit Location header instead of sendRedirect() to prevent
        // Spring Cloud Gateway from rewriting the redirect to its own host/port
        response.setStatus(HttpServletResponse.SC_FOUND); // 302
        response.setHeader("Location", targetUrl);
    }
}