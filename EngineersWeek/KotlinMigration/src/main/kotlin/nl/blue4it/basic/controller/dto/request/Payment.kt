package nl.blue4it.basic.controller.dto.request

open class Payment(val name: String, val fromIban: String, val toIban: String,
    val amount: Float, val balance: Float, val duration: String?)
