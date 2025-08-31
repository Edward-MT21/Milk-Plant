package com.module.Financial_Management.repositories;

import com.module.Financial_Management.model.entities.MilkSupplierPayment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IMilkSupplierPaymentRepository extends JpaRepository<MilkSupplierPayment, Long> {

}
