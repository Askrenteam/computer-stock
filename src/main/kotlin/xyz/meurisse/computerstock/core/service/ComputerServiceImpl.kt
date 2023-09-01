package xyz.meurisse.computerstock.core.service

import org.springframework.stereotype.Service
import xyz.meurisse.computerstock.core.api.ComputerCreateDto
import xyz.meurisse.computerstock.core.api.ComputerService
import xyz.meurisse.computerstock.core.model.Computer
import xyz.meurisse.computerstock.core.repository.ComputerRepository
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
}