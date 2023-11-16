package com.comicBD.productList.repository;

import com.comicBD.productList.model.products;
import org.springframework.data.jpa.repository.JpaRepository;

public interface productRepository extends JpaRepository<products,Long> {
}
