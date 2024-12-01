package pe.edu.cibertec.apibancocibertec.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CuentaDto {
    private int id;
    private String cuenta;
}
