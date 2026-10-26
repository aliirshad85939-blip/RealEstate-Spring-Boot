package com.example.realestate;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PropertyRepository extends JpaRepository<Property, Long> {

    List<Property> findByLocationContainingIgnoreCase(String location);

    @Query("""
        SELECT p FROM Property p
        WHERE (:location IS NULL OR
               LOWER(p.location) LIKE LOWER(CONCAT('%', :location, '%')))
         AND (
    :type IS NULL
    OR (
        UPPER(:type) = 'SALE'
        AND UPPER(p.type) IN ('HOUSE', 'PLOT')
    )
    OR (
        UPPER(:type) = 'RENT'
        AND UPPER(p.type) = 'FLAT'
    )
)
          AND (:minPrice IS NULL OR p.price >= :minPrice)
          AND (:maxPrice IS NULL OR p.price <= :maxPrice)
        """)
    List<Property> searchProperties(
            @Param("location") String location,
            @Param("type") String type,
            @Param("minPrice") Double minPrice,
            @Param("maxPrice") Double maxPrice
    );
}