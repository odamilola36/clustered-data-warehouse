package com.lomari.clustereddatawarehouse.repository;

import com.lomari.clustereddatawarehouse.models.Deal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DealsRepository extends JpaRepository<Deal, Long> {
    Optional<Deal> findByDealUniqueId(String uniqueDealId);
}
