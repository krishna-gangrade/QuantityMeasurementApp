package com.app.quantitymeasurement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import com.app.quantitymeasurement.entity.History;
import com.app.quantitymeasurement.repository.HistoryRepository;

@Service
public class HistoryService {

    @Autowired
    private HistoryRepository historyRepository;

    public List<History> getAllHistory() {
        return historyRepository.findAll();
    }

    public History saveHistory(History history) {
        return historyRepository.save(history);
    }
}