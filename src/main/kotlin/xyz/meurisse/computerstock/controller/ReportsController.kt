package xyz.meurisse.computerstock.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import xyz.meurisse.computerstock.controller.model.MonthlyReportDto
import xyz.meurisse.computerstock.controller.model.toDto
import xyz.meurisse.computerstock.core.api.ComputerService

@RestController
@RequestMapping("/reports")
class ReportsController(
        val computerService: ComputerService
) {
    @GetMapping("monthly-spendings")
    fun monthlySpending(): ResponseEntity<List<MonthlyReportDto>> {
        return ResponseEntity.ok(computerService.getSpendingReport().map { it.toDto() })
    }

}