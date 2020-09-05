package ar.com.ada.api.empleados.entities;

import ar.com.ada.api.empleados.services.EmpleadoService.EmpleadoValidationType;

public class AltaEmpleado {

    private AltaEmpleadoResultEnum resultado;
    private Empleado empleado;
    private EmpleadoValidationType motivo;

    public enum AltaEmpleadoResultEnum {
        REALIZADA, RECHAZADA
    }

    public AltaEmpleadoResultEnum getResultado() {
        return resultado;
    }

    public void setResultado(AltaEmpleadoResultEnum resultado) {
        this.resultado = resultado;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public EmpleadoValidationType getMotivo() {
        return motivo;
    }

    public void setMotivo(EmpleadoValidationType motivo) {
        this.motivo = motivo;
    }

}
