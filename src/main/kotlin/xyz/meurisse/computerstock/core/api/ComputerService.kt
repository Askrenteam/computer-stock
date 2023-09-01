package xyz.meurisse.computerstock.core.api

import xyz.meurisse.computerstock.core.model.Computer

interface ComputerService {
    fun listComputers(): List<Computer>
    fun createComputer(computerCreateDto: ComputerCreateDto)
}