package nl.blue4it.basic.controller.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

// 1. convert to kotlin
// 2. change to data class
// 3. show null value
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Payment {

    @NotNull
    private String name;
    @NotNull
    private String fromIban;
    @NotNull
    private String toIban;
    @NotNull
    private Float amount;
    @NotNull
    private Float balance;

    // could be null, otherwise it could be weekly, monthly or yearly
    private String duration;

}
