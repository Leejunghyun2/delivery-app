package com.example.deliveryapp.menu.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.deliveryapp.menu.dto.MenuCreateRequest;
import com.example.deliveryapp.menu.dto.MenuResponse;
import com.example.deliveryapp.menu.dto.MenuUpdateRequest;
import com.example.deliveryapp.menu.service.MenuService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class Menucontroller {

	private final MenuService menuService;

	@PostMapping("/stores/{storeId}/menus")
	public ResponseEntity<MenuResponse> create(@PathVariable Long storeId, 
			@Valid @RequestBody MenuCreateRequest request) {
		MenuResponse response = menuService.create(storeId, request);
		return ResponseEntity.created(URI.create("/api/menus/" + response.id())).body(response);
	}

	@GetMapping("/stores/{storeId}/menus")
	public ResponseEntity<List<MenuResponse>> getMenusByStore(@PathVariable Long storeId) {
		return ResponseEntity.ok(menuService.findByStore(storeId));
	}

	@GetMapping("/menus/{menuId}")
	public ResponseEntity<MenuResponse> getMenu(@PathVariable Long menuId) {
		return ResponseEntity.ok(menuService.findById(menuId));
	}

	@PutMapping("/menus/{menuId}")
	public ResponseEntity<MenuResponse> update(@PathVariable Long menuId,
			@Valid @RequestBody MenuUpdateRequest request) {
		return ResponseEntity.ok(menuService.update(menuId, request));
	}

	@PatchMapping("/menus/{menuId}/sold-out")
	public ResponseEntity<Void> soldOut(@PathVariable Long menuId) {
		menuService.soldOut(menuId);
		return ResponseEntity.noContent().build();
	}

	@PatchMapping("/menus/{menuId}/on-sale")
	public ResponseEntity<Void> resumeSale(@PathVariable Long menuId) {
		menuService.resumeSale(menuId);
		return ResponseEntity.noContent().build();
	}

}
