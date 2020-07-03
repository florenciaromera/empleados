package ar.com.ada.api.empleados.reposTest;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import ar.com.ada.api.empleados.entities.Empleado;
import ar.com.ada.api.empleados.repos.EmpleadoRepo;

@ExtendWith(SpringExtension.class)
@DataJpaTest()
public class EmpleadoRepoTest {

    @Autowired 
    private TestEntityManager entityManager;

    @Autowired
    private EmpleadoRepo empleadoRepo;

    @Test
    public void findById_SUCCESS() {

        Empleado rolf = new Empleado();
        entityManager.persist(rolf);
        entityManager.flush();

        Empleado found = empleadoRepo.findById(rolf.getEmpleadoId());

        Assertions.assertThat(found.getEmpleadoId()).isEqualTo(rolf.getEmpleadoId());

    }
    
}