package com.example.search_engine.history;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import java.time.LocalDateTime;

@Entity
public class Search_history {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "SEARCH_TERM", nullable = false)
    private String searchTerm;

    @Column(name = "SEARCH_TIME", nullable = false)
    private LocalDateTime searchTime;
    private String query;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSearchTerm(String query) {
        return searchTerm;
    }

    public void setSearchTerm(String searchTerm) {
        this.searchTerm = searchTerm;
    }

    public LocalDateTime getSearchTime() {
        return searchTime;
    }

    public void setSearchTime(LocalDateTime searchTime) {
        this.searchTime = searchTime;
    }

    public void setQuery(String query) {
        this.query=query;
    }

    public char[] getSearchTerm() {
        return searchTerm.toCharArray();
    }
}
