package com.module.Milk_Collection.model.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MilkCollection extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "milk_collection_id")
    private Long milkCollectionId;

    @Column(name = "milk_supplier_id")
    private Long milkSupplierId;

    @Column(name = "liters_milk")
    private Integer litersMilk;

    @Column(name = "collection_date")
    private LocalDate collectionDate;

}
    
