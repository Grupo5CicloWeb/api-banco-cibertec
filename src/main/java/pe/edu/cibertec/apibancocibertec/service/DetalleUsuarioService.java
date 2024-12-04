package pe.edu.cibertec.apibancocibertec.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pe.edu.cibertec.apibancocibertec.model.Rol;
import pe.edu.cibertec.apibancocibertec.model.Usuario;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class DetalleUsuarioService implements UserDetailsService {
    private final UsuarioService usuarioService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioService.obtenerUsuarioxNomusuario(username);
        return getUserDetails(usuario,
                getAuthorities(usuario.getRoles()));
    }
    public List<GrantedAuthority> getAuthorities(Set<Rol> roles) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (Rol role : roles) {
            authorities.add(new SimpleGrantedAuthority("ROLE_"+ role.getNomrol()));
        }
        return authorities;
    }

    private UserDetails getUserDetails(Usuario usuario,
                                       List<GrantedAuthority> authorities) {
        return new User(
                usuario.getNomusuario(),
                usuario.getPassword(),
                usuario.getActivo(),
                true, true, true,
                authorities);
    }
}
