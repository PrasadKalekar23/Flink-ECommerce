package Dto;

import lombok.Data;
import lombok.AllArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class SalesPerDay {
    long transactionId;
    LocalDate transactionDate;
    Double totalSalesAmount;
}
