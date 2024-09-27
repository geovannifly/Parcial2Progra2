package com.umg.edu.gt.parcial2;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
class Parcial2ApplicationTests {

	private static final Logger logger = LogManager.getLogger( Parcial2ApplicationTests.class );

	@Autowired
	private MockMvc mockMvc;

	@Test
	void obtenerTipoCambioDiaTest() throws Exception {
		/*mockMvc.perform(MockMvcRequestBuilders.get("/tipoCambioDia"))
				.andExpect(MockMvcResultMatchers.status().isOk());*/
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/tipoCambioDia"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();

		String response = result.getResponse().getContentAsString();
		logger.info(response);
	}

	@Test
	void obtenerTipoCambioFechaInicialTest() throws Exception {
		String fechaUrl = "24-09-2024";

		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/tipoCambioFechaInicial/{fechaUrl}", fechaUrl))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();

		String response = result.getResponse().getContentAsString();
		logger.info(response);
	}

}
