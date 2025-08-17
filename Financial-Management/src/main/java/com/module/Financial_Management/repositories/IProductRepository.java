package com.module.Financial_Management.repositories;

import com.module.Financial_Management.model.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IProductRepository extends JpaRepository<Product, Long> {
}
