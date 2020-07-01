package ar.com.ada.api.empleados.controllersTest;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

@WebMvcTest(CategoriaController.class)
public class CategoriaControllerTest {

    @MockBean
    CategoriaService categoriaService;

    
    
}