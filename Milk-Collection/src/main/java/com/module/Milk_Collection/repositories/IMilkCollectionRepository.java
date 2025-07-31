package com.module.Milk_Collection.repositories;

import com.module.Milk_Collection.model.entities.MilkCollection;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface IMilkCollectionRepository extends JpaRepository<MilkCollection, Long> {
    List<MilkCollection> findAllByCreatedAtBetween(LocalDateTime start, LocalDateTime end);

    Optional<MilkCollection> findByMilkSupplierIdAndCreatedAtBetween(Long milkSupplierId, LocalDateTime start, LocalDateTime end);
}
