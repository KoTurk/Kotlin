package nl.blue4it.basic.controller.dto.request

data class Payment(
    val name: String,
    val fromIban: String,
    val toIban: String,
    val amount: Float,
    val balance: Float,

    // could be null, otherwise it could be weekly, monthly or yearly
    val duration: String?
)