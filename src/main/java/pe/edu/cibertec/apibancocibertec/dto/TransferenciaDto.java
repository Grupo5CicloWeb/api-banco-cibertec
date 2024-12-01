package pe.edu.cibertec.apibancocibertec.dto;

import lombok.Data;

@Data
public class TransferenciaDto {
    private Integer cuentaorigenid;
    private Integer cuentadestinoid;
    private Double monto;

}
