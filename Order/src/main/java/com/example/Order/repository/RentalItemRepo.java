package com.example.Order.repository;

import com.example.Order.entity.RentalItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface RentalItemRepo extends JpaRepository<RentalItem, Long> {
    /*@Query("SELECT ri FROM RentalItem ri " +
            "WHERE ri.id <> :rentalItemId " +
            "AND ri.productId = :productId " +
            "AND ((ri.pickupDateTime BETWEEN :pickupDateTime AND :returnDateTime) " +
            "OR (ri.returnDateTime BETWEEN :pickupDateTime AND :returnDateTime))")
    List<RentalItem> findOverlappingRentals(@Param("rentalItemId") Long rentalItemId,
                                            @Param("productId") Long productId,
                                            @Param("pickupDateTime") LocalDateTime pickupDateTime,
                                            @Param("returnDateTime") LocalDateTime returnDateTime);

     */

    @Query("SELECT ri FROM RentalItem ri " +
            "WHERE (ri.rentalItemId <> :rentalItemId OR :rentalItemId IS NULL) " +
            "AND ri.productId = :productId " +
            "AND ((ri.pickupDateTime BETWEEN :pickupDateTime AND :returnDateTime) " +
            "OR (ri.returnDateTime BETWEEN :pickupDateTime AND :returnDateTime))")
    List<RentalItem> findOverlappingRentals(@Param("rentalItemId") Long rentalItemId,
                                            @Param("productId") Long productId,
                                            @Param("pickupDateTime") LocalDateTime pickupDateTime,
                                            @Param("returnDateTime") LocalDateTime returnDateTime);

}
