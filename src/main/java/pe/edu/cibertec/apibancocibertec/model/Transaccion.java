package pe.edu.cibertec.apibancocibertec.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Getter @Setter
@Entity
@Table(name = "transaccion")
public class Transaccion {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Integer id;
    private Integer cuentaorigenid;
    private Integer cuentadestinoid;
    private Double monto;
    private LocalDate fecha;
}
