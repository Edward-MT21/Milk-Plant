package com.module.Milk_Collection.repositories;

import com.module.Milk_Collection.model.entities.MilkSupplier;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.Optional;

public interface IMilkSupplierRepository extends JpaRepository<MilkSupplier, Long> {

    Optional<MilkSupplier> findByPersonId(Long personId);

    @Transactional
    @Modifying
    void deleteByPersonId(Long personId);
}
