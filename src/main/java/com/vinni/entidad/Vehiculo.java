package com.vinni.entidad;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;

@Entity
public class Vehiculo implements Serializable {
    @Id
    private String placa;
    private int modelo;
    private long precio;
    private int UltimoAnoPagoSOAT;
    private String TipoVehiculo;
    
    @Transient
    private Long ValorSeguro;

    
    

    public String getTipoVehiculo() {
        return TipoVehiculo;
    }
    public void setTipoVehiculo(String tipoVehiculo) {
        TipoVehiculo = tipoVehiculo;
    }
    public String getPlaca() {
        return placa;
    }
    public void setPlaca(String placa) {
        this.placa = placa;
    }
    public int getModelo() {
        return modelo;
    }
    public void setModelo(int modelo) {
        this.modelo = modelo;
    }
    public long getPrecio() {
        return precio;
    }
    public void setPrecio(long precio) {
        this.precio = precio;
    }
    public Long getValorSeguro() {
        return ValorSeguro;
    }
    public int getUltimoAnoPagoSOAT() {
        return UltimoAnoPagoSOAT;
    }
    public void setUltimoAnoPagoSOAT(int ultimoAnoPagoSOAT) {
        UltimoAnoPagoSOAT = ultimoAnoPagoSOAT;
    }
    

    public Vehiculo(String placa, int modelo, long precio, String tipoVehiculo) {
        this.placa = placa;
        this.modelo = modelo;
        this.precio = precio;
        this.TipoVehiculo = tipoVehiculo;
    }


    public Vehiculo(String placa, int modelo, long precio, int ultimoAnoPagoSOAT, Long valorSeguro, String tipoVehiculo) {
        this.placa = placa;
        this.modelo = modelo;
        this.precio = precio;
        UltimoAnoPagoSOAT = ultimoAnoPagoSOAT;
        ValorSeguro = valorSeguro;
        this.TipoVehiculo = tipoVehiculo;
    }
    public Long consultarSeguro() {
        int antiguedad = 2023 - this.modelo;
        double porcentajeAntiguedad = antiguedad * 0.002; // 2% por cada 10 años
        this.ValorSeguro = (long) (this.precio * (0.1 + porcentajeAntiguedad));
        return this.ValorSeguro;
    }
    public Vehiculo() {
    }
    public Vehiculo(String placa, int modelo, long precio, int ultimoAnoPagoSOAT, String tipoVehiculo) {
        this.placa = placa;
        this.modelo = modelo;
        this.precio = precio;
        UltimoAnoPagoSOAT = ultimoAnoPagoSOAT;
        this.TipoVehiculo = tipoVehiculo;
    }
}
