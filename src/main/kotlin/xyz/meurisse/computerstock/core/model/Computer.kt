package xyz.meurisse.computerstock.core.model

import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.UUID

data class Computer(
        val id: UUID,
        val creationTime: LocalDateTime,
        val name: String,
        val purchaseDate: LocalDate,
        val purchasePrice: BigDecimal,
        val yearlyKWhConsumption: Int
)
