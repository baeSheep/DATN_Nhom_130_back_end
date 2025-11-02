package com.DATN.DTO;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class TransactionDTO {
    private Integer transactionId;
    private Integer orderId;
    private String transactionCode;
    private String paymentStatus;
    private String paymentGateway;
    private LocalDateTime paidAt;
}