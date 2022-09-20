package nl.blue4it.basic.controller.dto.request

import lombok.AllArgsConstructor
import lombok.Data
import lombok.NoArgsConstructor
import javax.validation.constraints.NotNull

// 1. convert to kotlin
// 2. change to data class
// 3. show null value
data class Payment(val name: String,
                   val fromIban: String,
                   val toIban: String,
                   val amount: Float,
                   val balance: Float,
                   val duration: String?)