package Dto;

import lombok.Data;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
public class SalesPerCategory {
    long transactionId;
    String productCategory;
}
