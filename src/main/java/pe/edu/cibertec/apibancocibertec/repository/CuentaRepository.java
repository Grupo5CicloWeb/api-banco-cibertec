package pe.edu.cibertec.apibancocibertec.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.cibertec.apibancocibertec.model.Cuenta;

@Repository
public interface CuentaRepository
        extends JpaRepository<Cuenta, Integer> {
}
