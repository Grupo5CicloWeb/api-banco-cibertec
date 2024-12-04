package pe.edu.cibertec.apibancocibertec.controller;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pe.edu.cibertec.apibancocibertec.dto.UsuarioSeguridadDto;
import pe.edu.cibertec.apibancocibertec.model.Usuario;
import pe.edu.cibertec.apibancocibertec.service.DetalleUsuarioService;
import pe.edu.cibertec.apibancocibertec.service.IUsuarioService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/auth")
public class AuthController {

    private final IUsuarioService usuarioService;
    private final DetalleUsuarioService detalleUsuarioService;
    private final AuthenticationManager authenticationManager;

    @GetMapping("/login")
    @Transactional(readOnly = true)
    public ResponseEntity<UsuarioSeguridadDto> autenticarUsuario(
            @RequestParam String usuario,
            @RequestParam String password
    ) throws Exception {
        try{
            Authentication authentication = authenticationManager
                    .authenticate(
                            new UsernamePasswordAuthenticationToken(usuario, password)
                    );
            if(authentication.isAuthenticated()){
                Usuario objUsuario = usuarioService.obtenerUsuarioxNomusuario(usuario);
                String token = generarToken(objUsuario);
                return new ResponseEntity<>(UsuarioSeguridadDto.builder()
                        .idUsuario(objUsuario.getIdusuario())
                        .nomusuario(objUsuario.getNomusuario())
                        .token(token).build(), HttpStatus.OK);
            }else{
                return new ResponseEntity<>(
                        UsuarioSeguridadDto.builder().mensajeError("Error de autenticación").build(),
                        HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    private String generarToken(Usuario usuario) {
        String clave = "@Cibertec2024";
        List<GrantedAuthority> grantedAuthorities =
                detalleUsuarioService.getAuthorities(usuario.getRoles());
        return Jwts.builder()
                .setId(usuario.getIdusuario().toString())
                .setSubject(usuario.getNomusuario())
                .claim("Authorities",
                        grantedAuthorities.stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 300000))
                .signWith(SignatureAlgorithm.HS512, clave.getBytes())
                .compact();
    }

}
