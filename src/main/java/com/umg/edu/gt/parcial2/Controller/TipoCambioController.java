package com.umg.edu.gt.parcial2.Controller;

import com.umg.edu.gt.parcial2.Dto.TipoCambio;
import com.umg.edu.gt.parcial2.Dto.TipoCambioFechaInicial;
import com.umg.edu.gt.parcial2.service.TipoCambioSoapService;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

@RestController
public class TipoCambioController {

    private final Logger logger = LogManager.getLogger( this.getClass() );

    @Autowired
    private TipoCambioSoapService tipoCambioSoapService;
    /*private Logger logger;*/

    @GetMapping("/tipoCambioDia")
    public ResponseEntity<Object> obtenerTipoCambioDia() {
        String tipoCambio = tipoCambioSoapService.obtenerTipoCambioDia();

        // Verificar si la respuesta contiene errores
        if (tipoCambio.contains("Error")) {
            return ResponseEntity.status(500).body("Error: " + tipoCambio);
        }

        try {
            // Convertir el XML a JSON
            JSONObject xmlJSONObj = XML.toJSONObject(tipoCambio);

            // datos específicos del JSON
            JSONObject varDolar = xmlJSONObj.getJSONObject("soap:Envelope")
                    .getJSONObject("soap:Body")
                    .getJSONObject("TipoCambioDiaResponse")
                    .getJSONObject("TipoCambioDiaResult")
                    .getJSONObject("CambioDolar")
                    .getJSONObject("VarDolar");

            // traer los valores que te interesan
            String fecha = varDolar.getString("fecha");
            double referencia = varDolar.getDouble("referencia");

            // se creo la instancia del TipoCambio
            TipoCambio tipoCambioDTO = new TipoCambio(fecha, referencia);


            // Devolver la respuesta con el TipoCambio
            return ResponseEntity.ok(tipoCambioDTO);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error al procesar el XML.");
        }
    }


    /*
    *
    *
    *  CARNET: 9490-22-2364
    *  NOMBRE: Byron Geovanni Chicoj Perez
    *  Servicio a consumir: TipoCambioFechaInicial (Terminacion 4)
    *
    * @Param String fechaUrl
    *
    * */
    @GetMapping("/tipoCambioFechaInicial/{fechaUrl}")
    public ResponseEntity<Object> obtenerTipoCambioFechaInicial(@PathVariable String fechaUrl) {
        // Consumo de servicio SOAP
        String tipoCambio = tipoCambioSoapService.obtenerTipoCambioFechaInicial(fechaUrl.replace("-", "/"));

        // Verificar si la respuesta contiene errores
        if (tipoCambio.contains("Error")) {
            return ResponseEntity.status(500).body("Error: " + tipoCambio);
        }

        try {
            // Convertir el XML a JSON
            JSONObject xmlJSONObj = XML.toJSONObject(tipoCambio);

            // datos específicos del JSON
            JSONArray varDolarArray = xmlJSONObj.getJSONObject("soap:Envelope")
                    .getJSONObject("soap:Body")
                    .getJSONObject("TipoCambioFechaInicialResponse")
                    .getJSONObject("TipoCambioFechaInicialResult")
                    .getJSONObject("Vars")
                    .getJSONArray("Var");

            List<TipoCambioFechaInicial> tipoCambioList = new ArrayList<>();

            for (int i = 0; i < varDolarArray.length(); i++) {
                JSONObject varDolar = varDolarArray.getJSONObject(i);
                String fecha = varDolar.getString("fecha");
                double compra = varDolar.getDouble("compra");
                double venta = varDolar.getDouble("venta");
                int moneda = varDolar.getInt("moneda");

                // se creo la instancia del TipoCambio
                TipoCambioFechaInicial tipoCambioDTO = new TipoCambioFechaInicial(fecha, compra, venta, moneda);

                // Agregar el TipoCambio a la lista
                tipoCambioList.add(tipoCambioDTO);
            }

            logger.info("Se ha consumido el servicio de TipoCambioFechaInicial");
            // se envia respuesta con la lista de TipoCambio
            return ResponseEntity.ok(tipoCambioList);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error al procesar el XML.");
        }
    }


}