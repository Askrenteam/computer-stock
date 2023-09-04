package xyz.meurisse.computerstock

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertEquals
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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import xyz.meurisse.computerstock.controller.model.ComputerDto
import xyz.meurisse.computerstock.controller.model.MonthlyConsumptionReportDto
import xyz.meurisse.computerstock.controller.model.MonthlySpendingReportDto
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

	@Test
	fun monthlySpendingReportsShouldAggregateAllComputerPrices() {
		val computer1 = ComputerCreateDto(
				name = "First computer",
				purchaseDate = LocalDate.now().minusMonths(5),
				purchasePrice = BigDecimal("799.99"),
				yearlyKWhConsumption = 500
		)
		val computer2 = computer1.copy(
				purchaseDate = LocalDate.now().minusMonths(5),
				purchasePrice = BigDecimal("499.99")
		)
		val computer3 = computer1.copy(
				purchaseDate = LocalDate.now().minusMonths(2),
				purchasePrice = BigDecimal("999.99")
		)
		val computer4 = computer1.copy(
				purchaseDate = LocalDate.now().minusMonths(13),
				purchasePrice = BigDecimal("299.99")
		)

		createComputer(computer1)
		createComputer(computer2)
		createComputer(computer3)
		createComputer(computer4)

		val expectedReport = listOf(
				MonthlySpendingReportDto(LocalDate.now().minusMonths(5).withDayOfMonth(1), BigDecimal("1299.98")),
				MonthlySpendingReportDto(LocalDate.now().minusMonths(2).withDayOfMonth(1), BigDecimal("999.99")),
		)

		mockMvc.perform(get("/reports/monthly-spendings"))
				.andExpect {
					val result = objectMapper.readValue(it.response.contentAsString, object: TypeReference<List<MonthlySpendingReportDto>>(){})
					assertEquals(expectedReport, result)
				}
	}

	@Test
	fun monthlyConsumptionReportsShouldAggregateAllComputerConsumption() {
		val computer1 = ComputerCreateDto(
				name = "First computer",
				purchaseDate = LocalDate.now().minusMonths(5),
				purchasePrice = BigDecimal("799.99"),
				yearlyKWhConsumption = 500
		)
		val computer2 = computer1.copy(
				purchaseDate = LocalDate.now().minusMonths(5),
				yearlyKWhConsumption = 200
		)
		val computer3 = computer1.copy(
				purchaseDate = LocalDate.now().minusMonths(2),
				yearlyKWhConsumption = 300
		)
		val computer4 = computer1.copy(
				purchaseDate = LocalDate.now().minusMonths(13),
				yearlyKWhConsumption = 320
		)

		createComputer(computer1)
		createComputer(computer2)
		createComputer(computer3)
		createComputer(computer4)

		val expectedReport = listOf(
				MonthlyConsumptionReportDto(LocalDate.now().minusMonths(12).withDayOfMonth(1), 26),
				MonthlyConsumptionReportDto(LocalDate.now().minusMonths(11).withDayOfMonth(1), 26),
				MonthlyConsumptionReportDto(LocalDate.now().minusMonths(10).withDayOfMonth(1), 26),
				MonthlyConsumptionReportDto(LocalDate.now().minusMonths(9).withDayOfMonth(1), 26),
				MonthlyConsumptionReportDto(LocalDate.now().minusMonths(8).withDayOfMonth(1), 26),
				MonthlyConsumptionReportDto(LocalDate.now().minusMonths(7).withDayOfMonth(1), 26),
				MonthlyConsumptionReportDto(LocalDate.now().minusMonths(6).withDayOfMonth(1), 26),
				MonthlyConsumptionReportDto(LocalDate.now().minusMonths(5).withDayOfMonth(1), 26),
				MonthlyConsumptionReportDto(LocalDate.now().minusMonths(4).withDayOfMonth(1), 83),
				MonthlyConsumptionReportDto(LocalDate.now().minusMonths(3).withDayOfMonth(1), 83),
				MonthlyConsumptionReportDto(LocalDate.now().minusMonths(2).withDayOfMonth(1), 83),
				MonthlyConsumptionReportDto(LocalDate.now().minusMonths(1).withDayOfMonth(1), 108),
		)

		mockMvc.perform(get("/reports/monthly-consumption"))
				.andExpect {
					val result = objectMapper.readValue(it.response.contentAsString, object: TypeReference<List<MonthlyConsumptionReportDto>>(){})
					assertEquals(expectedReport, result)
				}
	}

	private fun createComputer(computerCreateDto: ComputerCreateDto): ResultActions {
		return mockMvc.perform(post("/computers")
				.content(objectMapper.writeValueAsString(computerCreateDto)).contentType(MediaType.APPLICATION_JSON))
	}

}
