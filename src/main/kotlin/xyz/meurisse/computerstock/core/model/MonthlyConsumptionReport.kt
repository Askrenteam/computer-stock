package xyz.meurisse.computerstock.core.model

import java.time.LocalDate

data class MonthlyConsumptionReport(
        val startDate: LocalDate,
        val kWhConsumption: Int
)