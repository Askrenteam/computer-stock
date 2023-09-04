package xyz.meurisse.computerstock.controller.model

import xyz.meurisse.computerstock.core.model.MonthlyConsumptionReport
import java.time.LocalDate

data class MonthlyConsumptionReportDto(
        val startDate: LocalDate,
        val kwhConsumption: Int // Fun fact: I wanted to name that kWhConsumption but a bug in jackson prevents me: https://github.com/FasterXML/jackson-module-kotlin/issues/630
)

fun MonthlyConsumptionReport.toDto(): MonthlyConsumptionReportDto {
    return MonthlyConsumptionReportDto(startDate, kWhConsumption)
}
