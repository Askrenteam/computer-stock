package xyz.meurisse.computerstock

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import xyz.meurisse.computerstock.core.api.ComputerCreateDto
import java.math.BigDecimal
import java.time.LocalDate

@SpringBootTest
@AutoConfigureMockMvc
class ComputerStockApplicationTest(
		@Autowired val mockMvc: MockMvc,
		@Autowired val objectMapper: ObjectMapper
) {

	@Test
	fun computersCreatedShouldBeReturned() {
		val computerCreateDto = ComputerCreateDto(
				name = "Shiny computer",
				purchaseDate = LocalDate.now().minusYears(5),
				purchasePrice = BigDecimal("799.99"),
				yearlyKWhConsumption = 500
		)

		mockMvc.perform(post("/computers")
				.content(objectMapper.writeValueAsString(computerCreateDto)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated)
	}

}
