package xyz.meurisse.computerstock.core.api

import xyz.meurisse.computerstock.core.model.Computer
import xyz.meurisse.computerstock.core.model.MonthlyConsumptionReport
import xyz.meurisse.computerstock.core.model.MonthlySpendingReport

interface ComputerService {
    fun listComputers(): List<Computer>
    fun createComputer(computerCreateDto: ComputerCreateDto)
    fun getSpendingReport(): List<MonthlySpendingReport>
    fun getConsumptionReport(): List<MonthlyConsumptionReport>
}