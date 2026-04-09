package com.app.quantitymeasurement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import com.app.quantitymeasurement.entity.History;
import com.app.quantitymeasurement.service.HistoryService;

@RestController
@RequestMapping("/api/history")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class HistoryController {

    @Autowired
    private HistoryService historyService;

    // ✅ GET history
    @GetMapping
    public List<History> getHistory() {
        return historyService.getAllHistory();
    }

    // ✅ SAVE history
    @PostMapping
    public History saveHistory(@RequestBody History history) {
        return historyService.saveHistory(history);
    }
}