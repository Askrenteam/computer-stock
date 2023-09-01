package xyz.meurisse.computerstock

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import xyz.meurisse.computerstock.controller.model.ComputerDto
import xyz.meurisse.computerstock.core.api.ComputerCreateDto
import xyz.meurisse.computerstock.repository.ComputerInMemoryRepository
import java.math.BigDecimal
import java.time.LocalDate

@SpringBootTest
@AutoConfigureMockMvc
class ComputerStockApplicationTest(
		@Autowired val mockMvc: MockMvc,
		@Autowired val objectMapper: ObjectMapper,
		@Autowired val computerInMemoryRepository: ComputerInMemoryRepository
) {

	@BeforeEach
	fun setup() {
		computerInMemoryRepository.clearRepository()
	}

	@Test
	fun creatingAComputerShouldReturn201() {
		val computerCreateDto = ComputerCreateDto(
				name = "Shiny computer",
				purchaseDate = LocalDate.now().minusYears(5),
				purchasePrice = BigDecimal("799.99"),
				yearlyKWhConsumption = 500
		)

		createComputer(computerCreateDto)
				.andExpect(status().isCreated)
	}

	@Test
	fun computersCreatedShouldBeReturned() {
		val computer1 = ComputerCreateDto(
				name = "First computer",
				purchaseDate = LocalDate.now().minusYears(5),
				purchasePrice = BigDecimal("799.99"),
				yearlyKWhConsumption = 500
		)
		val computer2 = ComputerCreateDto(
				name = "Second computer",
				purchaseDate = LocalDate.now().minusYears(3),
				purchasePrice = BigDecimal("499.99"),
				yearlyKWhConsumption = 300
		)

		createComputer(computer1)
		createComputer(computer2)

		mockMvc.perform(get("/computers"))
				.andExpect {
					val result = objectMapper.readValue(it.response.contentAsString, object: TypeReference<List<ComputerDto>>(){})
					assertThat(result).extracting("name").containsExactly("First computer", "Second computer")
				}
	}

	private fun createComputer(computerCreateDto: ComputerCreateDto): ResultActions {
		return mockMvc.perform(post("/computers")
				.content(objectMapper.writeValueAsString(computerCreateDto)).contentType(MediaType.APPLICATION_JSON))
	}

}
