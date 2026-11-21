package com.example.deliveryapp.store.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.deliveryapp.store.dto.StoreCreateRequest;
import com.example.deliveryapp.store.dto.StoreResponse;
import com.example.deliveryapp.store.service.StoreService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/stores")
@RequiredArgsConstructor
public class StoreController {

	private final StoreService storeService;
	
	@PostMapping
    public ResponseEntity<StoreResponse> create(@Valid @RequestBody StoreCreateRequest request) {
        StoreResponse response = storeService.create(request);
        return ResponseEntity
                .created(URI.create("/api/stores/" + response.id()))
                .body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StoreResponse> getStore(@PathVariable Long id) {
        return ResponseEntity.ok(storeService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<StoreResponse>> getStores() {
        return ResponseEntity.ok(storeService.findAll());
    }
	
}
