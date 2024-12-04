package pe.edu.cibertec.apibancocibertec.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.edu.cibertec.apibancocibertec.model.Usuario;
import pe.edu.cibertec.apibancocibertec.repository.UsuarioRepository;

@RequiredArgsConstructor
@Service
public class UsuarioService implements IUsuarioService  {

    private final UsuarioRepository usuarioRepository;

    @Override
    public Usuario obtenerUsuarioxNomusuario(String nomusuario) {
        return usuarioRepository.findByNomusuario(nomusuario);
    }
}
