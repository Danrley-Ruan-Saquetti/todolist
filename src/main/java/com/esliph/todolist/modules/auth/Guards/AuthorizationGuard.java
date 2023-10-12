package com.esliph.todolist.modules.auth.Guards;

import java.util.Base64;
import java.io.IOException;

import at.favre.lib.crypto.bcrypt.BCrypt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.esliph.todolist.modules.user.IUserRepository;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.FilterChain;

@Component
public class AuthorizationGuard {

    @Autowired
    private IUserRepository userRepository;

    // @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        var authorization = request.getHeader("Authorization");

        var authEncoded = authorization.substring("Basic".length()).trim();

        byte[] authDecode = Base64.getDecoder().decode(authEncoded);

        var authString = new String(authDecode);

        String[] credentials = authString.split(":");

        String username = credentials[0];
        String password = credentials[1];

        var user = this.userRepository.findByUsername(username);

        if (user == null) {
            response.sendError(0, password);
            return;
        }

        var isPasswordCheck = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword().toCharArray());

        if (!isPasswordCheck.verified) {
            response.sendError(0, password);
            return;
        }

        filterChain.doFilter(request, response);
    }
}