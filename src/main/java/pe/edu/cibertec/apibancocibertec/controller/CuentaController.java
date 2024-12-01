package pe.edu.cibertec.apibancocibertec.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pe.edu.cibertec.apibancocibertec.dto.CuentaDto;
import pe.edu.cibertec.apibancocibertec.dto.TransferenciaDto;
import pe.edu.cibertec.apibancocibertec.service.ICuentaService;

import java.util.List;
/*@CrossOrigin(origins = {"http://frontbancociber.com"},
methods = {RequestMethod.GET, RequestMethod.POST})*/
@RequiredArgsConstructor
@Controller
@RequestMapping("/api/v1/cuenta")
public class CuentaController {
    private final ICuentaService cuentaService;
    //localhost:8080/api/v1/cuenta/transaccion
    @PostMapping("/transaccion")
    public ResponseEntity<String> transaccion(
            @RequestBody TransferenciaDto transferenciaDto) {
        try {
            cuentaService.transferir(transferenciaDto);
            return new ResponseEntity<>(
                    "Transferencia correcta",
                    HttpStatus.OK);
        }catch (Exception ex){
            return new ResponseEntity<>("Error: "
                    + ex.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    //localhost:8080/api/v1/cuenta
    @GetMapping()
    public ResponseEntity<List<CuentaDto>> getCuenta() {
        return new ResponseEntity<>(
                cuentaService.listarCuentas(),
                HttpStatus.OK);
    }


}
