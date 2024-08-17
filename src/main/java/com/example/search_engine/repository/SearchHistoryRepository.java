package com.example.search_engine.repository;

import com.example.search_engine.history.Search_history;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SearchHistoryRepository extends JpaRepository<Search_history, Long> {
    List<Search_history> findAllByUserId(Long userId);
}
