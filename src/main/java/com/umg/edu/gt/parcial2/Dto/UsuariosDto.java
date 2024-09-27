package com.umg.edu.gt.parcial2.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UsuariosDto {

    private Long id;
    private String nombre;
    private String apellido;

}