package pe.edu.cibertec.apibancocibertec.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter @Setter @Entity @Table(name = "usuario")
public class Usuario {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idusuario;
    private String nomusuario;
    private String nombres;
    private String apellidos;
    private String email;
    private String password;
    private Boolean activo;
    @ManyToMany(cascade = CascadeType.MERGE
    , fetch = FetchType.EAGER)
    @JoinTable(name = "usuario_rol",
    joinColumns = @JoinColumn(name = "idusuario"),
    inverseJoinColumns = @JoinColumn(name = "idrol"))
    private Set<Rol> roles;
}
