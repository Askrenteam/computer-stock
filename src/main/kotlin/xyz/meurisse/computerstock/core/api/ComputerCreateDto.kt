package xyz.meurisse.computerstock.core.api

import java.math.BigDecimal
import java.time.LocalDate

/**
 * TODO: maybe add some validation?
 *  - limit name length
 *  - purchaseDate should be in the past
 */
data class ComputerCreateDto(
        val name: String,
        val purchaseDate: LocalDate,
        val purchasePrice: BigDecimal,
        val yearlyKWhConsumption: Int
)
