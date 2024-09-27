package com.umg.edu.gt.parcial2.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TipoCambioFechaInicial {
    private String fecha;
    private double compra;
    private double venta;
    private int moneda;
}
