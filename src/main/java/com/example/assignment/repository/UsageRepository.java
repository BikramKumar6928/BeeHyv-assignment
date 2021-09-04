package com.example.assignment.repository;

import com.example.assignment.models.Usage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UsageRepository extends JpaRepository<Usage, UUID> {
    List<Usage> findAllByUserId(String userId);
}
