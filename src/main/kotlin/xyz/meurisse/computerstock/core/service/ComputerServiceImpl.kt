package xyz.meurisse.computerstock.core.service

import org.springframework.stereotype.Service
import xyz.meurisse.computerstock.core.api.ComputerCreateDto
import xyz.meurisse.computerstock.core.api.ComputerService
import xyz.meurisse.computerstock.core.model.Computer
import xyz.meurisse.computerstock.core.model.MonthlyConsumptionReport
import xyz.meurisse.computerstock.core.model.MonthlySpendingReport
import xyz.meurisse.computerstock.core.repository.ComputerRepository
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

@Service
class ComputerServiceImpl(
        val computerRepository: ComputerRepository
) : ComputerService {
    override fun listComputers(): List<Computer> {
        return computerRepository.listComputers()
    }

    override fun createComputer(computerCreateDto: ComputerCreateDto) {
        computerRepository.createComputer(Computer(
                id = UUID.randomUUID(),
                creationTime = LocalDateTime.now(),
                name = computerCreateDto.name,
                purchaseDate = computerCreateDto.purchaseDate,
                purchasePrice = computerCreateDto.purchasePrice,
                yearlyKWhConsumption = computerCreateDto.yearlyKWhConsumption))
    }

    override fun getSpendingReport(): List<MonthlySpendingReport> {
        return computerRepository.listComputers()
                .filter { computer -> computer.wasPurchasedLastYear() }
                .groupBy { computer -> computer.purchaseDate.withDayOfMonth(1) }
                .entries
                .sortedBy { (date, _) -> date }
                .map { (date, computers) -> MonthlySpendingReport(startDate = date, spendings = computers.sumOf { it.purchasePrice }) }
    }

    private fun Computer.wasPurchasedLastYear(): Boolean {
        return purchaseDate.withDayOfMonth(1) > LocalDate.now().withDayOfMonth(1).minusMonths(12)
    }

    override fun getConsumptionReport(): List<MonthlyConsumptionReport> {
        val computers = computerRepository.listComputers()

        // TODO: the monthly consumption isn't very precise, we might want to use floats / bigdecimals
        return (12L downTo 1L).map { LocalDate.now().minusMonths(it).withDayOfMonth(1) }
                .map { date -> MonthlyConsumptionReport(
                        startDate = date,
                        kWhConsumption = computers.filter { it.purchaseDate < date }.sumOf { it.yearlyKWhConsumption / 12 }) }
    }
}