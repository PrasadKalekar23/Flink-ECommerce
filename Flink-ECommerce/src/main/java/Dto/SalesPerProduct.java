package Dto;

import lombok.Data;
import lombok.AllArgsConstructor;
@Data
@AllArgsConstructor
public class SalesPerProduct {
    private long transactionId;
    private String productId;
    private String productName;
}
