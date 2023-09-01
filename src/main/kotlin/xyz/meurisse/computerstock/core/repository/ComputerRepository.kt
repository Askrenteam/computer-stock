package xyz.meurisse.computerstock.core.repository

import xyz.meurisse.computerstock.core.model.Computer

interface ComputerRepository {
    fun listComputers(): List<Computer>
    fun createComputer(computer: Computer)
}