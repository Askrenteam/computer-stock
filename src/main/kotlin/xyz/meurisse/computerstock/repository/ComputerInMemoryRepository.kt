package xyz.meurisse.computerstock.repository

import org.springframework.stereotype.Repository
import xyz.meurisse.computerstock.core.model.Computer
import xyz.meurisse.computerstock.core.repository.ComputerRepository

@Repository
class ComputerInMemoryRepository: ComputerRepository {
    private val computers = mutableListOf<Computer>()

    override fun listComputers(): List<Computer> {
        return computers
    }

    override fun createComputer(computer: Computer) {
        computers.add(computer)
    }
}