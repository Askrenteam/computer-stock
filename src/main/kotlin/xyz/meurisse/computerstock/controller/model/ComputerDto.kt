package xyz.meurisse.computerstock.controller.model

import xyz.meurisse.computerstock.core.model.Computer
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

data class ComputerDto(
        val id: UUID,
        val creationTime: LocalDateTime,
        val name: String,
        val purchaseDate: LocalDate,
        val purchasePrice: BigDecimal,
        val yearlyKWhConsumption: Int
)

fun Computer.toDto(): ComputerDto {
    return ComputerDto(id, creationTime, name, purchaseDate, purchasePrice, yearlyKWhConsumption)
}
