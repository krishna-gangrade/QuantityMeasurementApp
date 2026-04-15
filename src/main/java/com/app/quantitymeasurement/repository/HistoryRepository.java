package com.app.quantitymeasurement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.app.quantitymeasurement.entity.History;

public interface HistoryRepository extends JpaRepository<History, Long> {
}