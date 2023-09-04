package xyz.meurisse.computerstock.controller.model

import xyz.meurisse.computerstock.core.model.MonthlySpendingReport
import java.math.BigDecimal
import java.time.LocalDate

data class MonthlyReportDto(
        val startDate: LocalDate,
        val spendings: BigDecimal,
)

fun MonthlySpendingReport.toDto(): MonthlyReportDto {
    return MonthlyReportDto(startDate, spendings)
}