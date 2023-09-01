package xyz.meurisse.computerstock.core.api

import java.math.BigDecimal
import java.time.LocalDate

data class ComputerCreateDto(
        val name: String,
        val purchaseDate: LocalDate,
        val purchasePrice: BigDecimal,
        val yearlyKWhConsumption: Int
)
