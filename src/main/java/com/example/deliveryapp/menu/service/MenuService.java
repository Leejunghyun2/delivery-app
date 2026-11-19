package com.example.deliveryapp.menu.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.deliveryapp.global.exception.BusinessException;
import com.example.deliveryapp.global.exception.ErrorCode;
import com.example.deliveryapp.menu.domain.MenuItem;
import com.example.deliveryapp.menu.dto.MenuCreateRequest;
import com.example.deliveryapp.menu.dto.MenuResponse;
import com.example.deliveryapp.menu.dto.MenuUpdateRequest;
import com.example.deliveryapp.menu.repository.MenuItemRepository;
import com.example.deliveryapp.store.domain.Store;
import com.example.deliveryapp.store.repository.StoreRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MenuService {

	private final MenuItemRepository menuItemRepository;
	private final StoreRepository storeRepository;

	@Transactional
	public MenuResponse create(Long storeId, MenuCreateRequest request) {
		Store store = findStore(storeId);
		MenuItem menuItem = menuItemRepository.save(request.toEntity(store));
		return MenuResponse.from(menuItem);

	}

	public List<MenuResponse> findByStore(Long storeId) {
		findStore(storeId);
		return menuItemRepository.findByStoreId(storeId).stream().map(MenuResponse::from).toList();
	}

	public MenuResponse findById(Long menuId) {
		return MenuResponse.from(findMenu(menuId));
	}

	@Transactional
	public MenuResponse update(Long menuId, MenuUpdateRequest request) {
		MenuItem menuItem = findMenu(menuId);
		menuItem.update(request.name(), request.description(), request.price());
		return MenuResponse.from(menuItem);
	}

	@Transactional
	public void soldOut(Long menuId) {
		findMenu(menuId).soldOut();
	}

	@Transactional
	public void resumeSale(Long menuId) {
		findMenu(menuId).resumeSale();
	}

	private Store findStore(Long storeId) {
		return storeRepository.findById(storeId).orElseThrow(() -> new BusinessException(ErrorCode.STORE_NOT_FOUND));
	}

	private MenuItem findMenu(Long menuId) {
		return menuItemRepository.findById(menuId).orElseThrow(() -> new BusinessException(ErrorCode.MENU_NOT_FOUND));
	}

}
