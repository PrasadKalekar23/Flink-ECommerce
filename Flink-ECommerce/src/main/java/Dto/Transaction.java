package Dto;

import lombok.Data;
import org.apache.flink.elasticsearch7.shaded.org.elasticsearch.search.DocValueFormat;

import java.sql.Timestamp;

@Data
public class Transaction {
    private long transactionId;
    private String productId;
    private String productName;
    private double productPrice;
    private int productQuantity;
    private String productCategory;
    private String customerId;
    private String transactionDate;
    private Double totalPayAmount;
}