package xyz.meurisse.computerstock.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import xyz.meurisse.computerstock.controller.model.MonthlyConsumptionReportDto
import xyz.meurisse.computerstock.controller.model.MonthlySpendingReportDto
import xyz.meurisse.computerstock.controller.model.toDto
import xyz.meurisse.computerstock.core.api.ComputerService

@RestController
@CrossOrigin //Wouldn't use this in production obviously
@RequestMapping("/reports")
class ReportsController(
        val computerService: ComputerService
) {
    @GetMapping("monthly-spendings")
    fun monthlySpending(): ResponseEntity<List<MonthlySpendingReportDto>> {
        return ResponseEntity.ok(computerService.getSpendingReport().map { it.toDto() })
    }

    @GetMapping("monthly-consumption")
    fun monthlyConsumption(): ResponseEntity<List<MonthlyConsumptionReportDto>> {
        return ResponseEntity.ok(computerService.getConsumptionReport().map { it.toDto() })
    }

}