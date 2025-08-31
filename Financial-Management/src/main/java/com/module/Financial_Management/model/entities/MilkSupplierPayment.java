package com.module.Financial_Management.model.entities;

import com.module.Common.entities.BaseEntity;
import com.module.Financial_Management.model.enums.PaymentStatusEnum;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MilkSupplierPayment extends BaseEntity {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long milkSupplierPaymentId;
        private Long milkSupplierId; // Referencia externa al proveedor
        private LocalDate startDate; // Inicio de la quincena
        private LocalDate endDate;   // Fin de la quincena
        private Integer totalLitersMilk;  // Litros recogidos en ese periodo
        private BigDecimal pricePerLiter;
        private BigDecimal totalAmount;// totalLitersMilk * pricePerLiter
        @Enumerated(EnumType.STRING)
        private PaymentStatusEnum paymentStatusEnum; // Enum: PENDING, PAID, CANCELLED
        private LocalDateTime paymentDate; // Fecha en que se realizó el pago (si aplica)

}
