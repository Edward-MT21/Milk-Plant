package com.module.Milk_Collection.repositories;

import com.module.Milk_Collection.model.entities.MilkCollection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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

    @Modifying
    @Query("UPDATE MilkCollection m SET m.status = :status WHERE m.milkSupplierId = :milkSupplierId")
    void updateStatusByMilkSupplierId(@Param("milkSupplierId") Long milkSupplierId, @Param("status") String status);
}
