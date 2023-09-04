package xyz.meurisse.computerstock.core.model

import java.math.BigDecimal
import java.time.LocalDate

data class MonthlySpendingReport(
        val startDate: LocalDate,
        val spendings: BigDecimal
)
