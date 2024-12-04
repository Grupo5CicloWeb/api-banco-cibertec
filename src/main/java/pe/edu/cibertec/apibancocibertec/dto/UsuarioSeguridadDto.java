package pe.edu.cibertec.apibancocibertec.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UsuarioSeguridadDto {
    private Integer idUsuario;
    private String nomusuario;
    private String token;
    private String mensajeError;
}
