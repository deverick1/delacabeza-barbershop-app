package com.analistas.barberiapp.service;

import com.analistas.barberiapp.dto.LoginRequest;
import com.analistas.barberiapp.dto.LoginResponse;
import com.analistas.barberiapp.model.Usuario;
import com.analistas.barberiapp.repository.UsuarioRepository;
import com.analistas.barberiapp.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public LoginResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        Usuario usuario = usuarioRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        String token = jwtUtil.generateToken(usuario.getEmail());

        return new LoginResponse(token, usuario.getNombre(), usuario.getEmail());
    }

    public void registrar(String nombre, String email, String password) {
        if (usuarioRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("Ya existe un usuario con ese email");
        }

        Usuario usuario = Usuario.builder()
                .nombre(nombre)
                .email(email)
                .password(passwordEncoder.encode(password))
                .build();

        usuarioRepository.save(usuario);
    }
}