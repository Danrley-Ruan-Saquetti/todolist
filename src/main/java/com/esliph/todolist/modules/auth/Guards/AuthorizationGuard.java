package com.esliph.todolist.modules.auth.Guards;

import java.util.Base64;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.esliph.todolist.modules.auth.PathsWithAuthorization;
import com.esliph.todolist.modules.user.IUserRepository;
import com.esliph.todolist.services.CryptoPassword;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.FilterChain;

@Component
public class AuthorizationGuard extends OncePerRequestFilter {

    @Autowired
    private IUserRepository userRepository;

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {

        var path = request.getServletPath();

        if (!PathsWithAuthorization.hasPath(request.getMethod(), path)) {
            filterChain.doFilter(request, response);
            return;
        }

        var authorization = request.getHeader("Authorization");

        if (authorization == null || "Basic ".length() > authorization.length()) {
            response.sendError(401, "Basic auth invalid");
            return;
        }

        var authEncoded = (authorization + "").substring("Basic".length()).trim();

        byte[] authDecode = Base64.getDecoder().decode(authEncoded);

        var authString = new String(authDecode);

        String[] credentials = authString.split(":");

        String username = credentials[0];
        String password = credentials[1];

        var user = this.userRepository.findByUsername(username);

        if (user == null) {
            response.sendError(401, "User not found");
            return;
        }

        var isPasswordCheck = CryptoPassword.verifyPassword(password, user.getPassword());

        if (!isPasswordCheck) {
            response.sendError(401, "User not authorized");
            return;
        }

        request.setAttribute("userId", user.getId());

        filterChain.doFilter(request, response);
    }
}