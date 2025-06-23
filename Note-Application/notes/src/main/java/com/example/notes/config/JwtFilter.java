package com.example.notes.config;

import com.example.notes.model.UserEntity;
import com.example.notes.repository.UserRepository;
import com.example.notes.service.JwtUtilService;
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
import java.util.List;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtilService jwtUtilService;
    private final UserRepository userRepository;

    public JwtFilter(JwtUtilService jwtUtilService, UserRepository userRepository) {
        this.jwtUtilService = jwtUtilService;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader=request.getHeader("Authorization");
        String token=null;
        String username=null;
        String path=request.getRequestURI();
        if (path.equals("/api/register")){
            filterChain.doFilter(request, response);
            return;
        }

        if (authHeader!=null &&authHeader.startsWith("Bearer ")){
            token=authHeader.substring(7);
            System.out.println("The token is: "+token);
            username=jwtUtilService.extractUserName(token);
        }
        if (username!=null && SecurityContextHolder.getContext().getAuthentication()==null){

            UserEntity user=userRepository.findByEmail(username);
            if (jwtUtilService.validateToken(token,user)){
                String username1=jwtUtilService.extractUserName(token);
                System.out.println("The user email is: "+username1);
                System.out.println("The user Role is: "+jwtUtilService.extractUserRole(token));
                UsernamePasswordAuthenticationToken authToken
                        =new UsernamePasswordAuthenticationToken(user.getEmail(),null, List.of(new SimpleGrantedAuthority("ROLE_"+user.getRoles().getRole_name())));
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request,response);
    }
}
