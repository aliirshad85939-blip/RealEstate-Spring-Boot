package com.example.realestate;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

    List<Favorite> findByUser_Id(Long userId);

    Optional<Favorite> findByUser_IdAndProperty_Id(
            Long userId, Long propertyId);

    boolean existsByUser_IdAndProperty_Id(
            Long userId, Long propertyId);

            @Transactional
void deleteByUser_IdAndProperty_Id(
        Long userId, Long propertyId);

    
}