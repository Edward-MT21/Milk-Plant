package com.module.Milk_Collection.repositories;

import com.module.Milk_Collection.model.entities.MilkCollection;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface IMilkCollectionRepository extends JpaRepository<MilkCollection, Long> {

    Optional<MilkCollection> findByMilkSupplierIdAndCollectionDate(Long milkSupplierId, LocalDate collectionDate);

    List<MilkCollection> findAllByCollectionDate(LocalDate collectionDate);

    List<MilkCollection> findAllByCollectionDateBetween(LocalDate startDate, LocalDate endDate);

    void deleteAllByMilkSupplierId(Long milkSupplierId);

    List<MilkCollection> findByMilkSupplierId(Long milkSupplierId);

    List<MilkCollection> findAllByMilkSupplierId(Long milkSupplierId);
}
