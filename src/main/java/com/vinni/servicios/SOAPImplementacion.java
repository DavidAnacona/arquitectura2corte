package com.vinni.servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vinni.entidad.Vehiculo;

import com.vinni.repositorios.VehiculoRepositorio;

import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;

@Service
@WebService(serviceName = "usuarios")
public class SOAPImplementacion {

    @Autowired
    private VehiculoRepositorio vehiculoRepositorio;

    @WebMethod(operationName = "obtenerVehiculos")
    public List<Vehiculo> consultarVehiculos() {
        return vehiculoRepositorio.findAll();
    }

    @WebMethod(operationName = "consultaValorSeguro")
    public Long consultaValorSeguro(@WebParam(name = "placaVehiculo") String placa) {
        Vehiculo vehiculo = vehiculoRepositorio.findById(placa).orElse(null);

        if (vehiculo == null) {

            return null;
        }

        int modelo = vehiculo.getModelo();
        int antiguedad = 2023 - modelo;
        double porcentajeAntiguedad = antiguedad * 0.2; // 2% por cada 10 años
        double porcentaje = (porcentajeAntiguedad / 100) + 0.1;

        Long Seguro = (long) (vehiculo.getPrecio() * porcentaje);
        return Seguro;
    }

    @WebMethod(operationName = "crearVehiculo")
    public boolean crearVehiculo(@WebParam(name = "placaVehiculo") String placa,
            @WebParam(name = "modeloVehiculo") int modelo, @WebParam(name = "precioVehiculo") long precio,
            @WebParam(name = "ultimoAnoSOAT") int ultimoAnoPagoSOAT,
            @WebParam(name = "tipoVehiculo") String tipoVehiculo) {
        try {
            // Verificar si ya existe un vehículo con la misma placa
            Optional<Vehiculo> existingVehiculo = vehiculoRepositorio.findById(placa);
            if (existingVehiculo.isPresent()) {
                // El vehículo ya existe, no se puede crear otro con la misma placa
                return false;
            } else {
                // Crear un nuevo vehículo
                Vehiculo vehiculo = new Vehiculo(placa, modelo, precio, ultimoAnoPagoSOAT, tipoVehiculo);
                vehiculoRepositorio.save(vehiculo);
                return true;
            }
        } catch (Exception e) {
            // Manejo de errores
            e.printStackTrace();
            return false;
        }
    }

    @WebMethod(operationName = "consultarSOAT")
    public String consultarSOAT(@WebParam(name = "placaVehiculo") String placa) {
        Vehiculo vehiculo = vehiculoRepositorio.findById(placa).orElse(null);
        if (vehiculo == null) {
            return "Vehículo no encontrado";
        }

        int modelo = vehiculo.getModelo();
        int antiguedad = 2023 - modelo;
        Long valorSOAT;
        String estado;

        if (antiguedad < 5) {
            valorSOAT = 100000L;
        } else {
            valorSOAT = 150000L;
        }

        // Verificar si el SOAT está pagado
        if (vehiculo.getUltimoAnoPagoSOAT() == 2023) {
            estado = "Activo"; // Pagado
        } else {
            estado = "Inactivo"; // No pagado
        }

        return "Valor SOAT: " + valorSOAT + ", Estado: " + estado;
    }

    @WebMethod(operationName = "eliminarVehiculo")
    public boolean eliminarVehiculo(@WebParam(name = "placaVehiculo") String placa) {
        try {
            Optional<Vehiculo> existingVehiculo = vehiculoRepositorio.findById(placa);
            if (existingVehiculo.isPresent()) {
                vehiculoRepositorio.deleteById(placa);
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @WebMethod(operationName = "picoYPlacaSolidario")
    public String picoYPlacaSolidario(@WebParam(name = "placaVehiculo") String placa) {
        Optional<Vehiculo> vehiculoOptional = vehiculoRepositorio.findById(placa);

        if(vehiculoOptional.isPresent()){
            if ("motocicleta".equalsIgnoreCase(vehiculoOptional.get().getTipoVehiculo())){
                return "Las motocicletas no tienen que pagar el pico y placa solidario";
            }else{
                return "El valor del pico y placa solidario es: Diario: $58.178, Mensual: $464.974 y valor $2.325.095";
            }
        }else{
            return "Vehiculo no encontrado";
        }
    }
}