package nl.blue4it.basic.controller.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// 1. convert to kotlin
// 2. change to data class
// 3. show null value
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Payment {

    private String name;
    private String fromIban;
    private String toIban;
    private Float amount;
    private Float balance;

    // could be null, otherwise it could be weekly, monthly or yearly
    private String duration;

}
