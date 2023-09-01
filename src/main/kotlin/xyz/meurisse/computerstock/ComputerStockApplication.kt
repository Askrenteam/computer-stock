package xyz.meurisse.computerstock

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ComputerStockApplication

fun main(args: Array<String>) {
	runApplication<ComputerStockApplication>(*args)
}
