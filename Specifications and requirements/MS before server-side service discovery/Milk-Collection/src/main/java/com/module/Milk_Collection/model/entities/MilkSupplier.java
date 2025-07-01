package com.module.Milk_Collection.model.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor

@Builder
public class MilkSupplier extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "milk_supplier_id")
    private Long milkSupplierId;

    @Column(name = "person_id")
    private Long personId;

    @Column(name = "communication_sw")
    private Boolean communicationSw;



}
