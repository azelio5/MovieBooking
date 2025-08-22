package com.sbbc.mb.moviebookingapp.jwt;

import com.sbbc.mb.moviebookingapp.exception.NotFoundException;
import com.sbbc.mb.moviebookingapp.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    public JWTAuthenticationFilter(JwtService jwtService, UserRepository userRepository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authorizationHeader = request.getHeader("Authorization");
        final String jwtToken;
        final String username;
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        //EXTRACT JWT FROM HEADER
        jwtToken = authorizationHeader.substring(7);
        username = jwtService.extractUsername(jwtToken);

        //check if we have a username and no authentication exist yet
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            var userDetails = userRepository.findUserByUsername(username).orElseThrow(() ->
                    new NotFoundException("User not found with username: " + username));

            //validate the token
            if (jwtService.isTokenValid(jwtToken, userDetails)) {

                //create authentication with user roles
                List<SimpleGrantedAuthority> authorities = userDetails.getRoles().
                        stream().
                        map(SimpleGrantedAuthority::new).
                        toList();

                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, authorities);

                //set authentication details
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                //update the security context with authentication
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
