package xyz.meurisse.computerstock.controller

import org.springframework.http.*
import org.springframework.web.bind.annotation.CrossOrigin
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
@CrossOrigin //Wouldn't use this in production obviously
@RequestMapping("/computers")
class ComputerController(
        val computerService: ComputerService
) {

    //TODO: a real app would offer a paginated endpoint
    @GetMapping
    fun listComputers(): ResponseEntity<List<ComputerDto>> {
        return ResponseEntity.ok(computerService.listComputers().map { it.toDto() })
    }

    @PostMapping
    fun createComputer(@RequestBody computerCreateDto: ComputerCreateDto): ResponseEntity<Void> {
        computerService.createComputer(computerCreateDto)
        return ResponseEntity.status(HttpStatus.CREATED).build()
    }
}