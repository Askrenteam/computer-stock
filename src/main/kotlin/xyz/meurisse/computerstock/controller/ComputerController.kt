package xyz.meurisse.computerstock.controller

import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import xyz.meurisse.computerstock.controller.model.ComputerDto
import xyz.meurisse.computerstock.controller.model.toDto
import xyz.meurisse.computerstock.core.api.ComputerCreateDto
import xyz.meurisse.computerstock.core.api.ComputerService

@RestController
@RequestMapping("/computers")
class ComputerController(
        val computerService: ComputerService
) {

    @GetMapping
    fun listComputers(): List<ComputerDto> {
        return computerService.listComputers().map { it.toDto() }
    }

    @PostMapping
    fun createComputer(@RequestBody computerCreateDto: ComputerCreateDto) {
        computerService.createComputer(computerCreateDto)
    }
}