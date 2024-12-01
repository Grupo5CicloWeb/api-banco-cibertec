package pe.edu.cibertec.apibancocibertec.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.cibertec.apibancocibertec.model.Transaccion;

@Repository
public interface TransaccionRepository extends
        JpaRepository<Transaccion, Integer> {
}
