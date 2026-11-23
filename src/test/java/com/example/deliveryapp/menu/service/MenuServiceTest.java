package com.example.deliveryapp.menu.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.deliveryapp.global.exception.BusinessException;
import com.example.deliveryapp.global.exception.ErrorCode;
import com.example.deliveryapp.menu.domain.MenuItem;
import com.example.deliveryapp.menu.domain.MenuStatus;
import com.example.deliveryapp.menu.dto.MenuCreateRequest;
import com.example.deliveryapp.menu.repository.MenuItemRepository;
import com.example.deliveryapp.store.repository.StoreRepository;

@ExtendWith(MockitoExtension.class)
public class MenuServiceTest {

	@Mock
	private MenuItemRepository menuItemRepository;
	
	@Mock
	private StoreRepository storeRepository;
	
	@InjectMocks
	private MenuService menuService;
	
	@Test
	@DisplayName("없는 메뉴를 조회하면 MENU_NOT_FOUND 예외발생한다")
	void findById_notFound() {
		
		given(menuItemRepository.findById(999L)).willReturn(Optional.empty());
		
		assertThatThrownBy(()-> menuService.findById(999L))
					.isInstanceOf(BusinessException.class)
					.extracting("errorCode")
					.isEqualTo(ErrorCode.MENU_NOT_FOUND);;
	}
	
	@Test
	@DisplayName("메뉴를 품절 처리하면 상태가 SOLD_OUT으로 바뀐다")
	void soldOut() {
		MenuItem menu = MenuItem.builder()
				.name("양념치킨")
				.description("달짝지근")
				.price(20000)
				.build();
		
		given(menuItemRepository.findById(1L)).willReturn(Optional.of(menu));
		
		menuService.soldOut(1L);
		
		assertThat(menu.getStatus()).isEqualTo(MenuStatus.SOLD_OUT);
		
	}
	
	@Test
	@DisplayName("없는 메뉴를 조회하면 MENU_NOT_FOUND 예외발생한다")
	void create_storeNotFound() {
		MenuCreateRequest request = new MenuCreateRequest("양념치킨", "달짝지근", 20000);
		given(storeRepository.findById(999L)).willReturn(Optional.empty());
		
		assertThatThrownBy(() -> menuService.create(999L, request))
				.isInstanceOf(BusinessException.class)
				.extracting("errorCode")
				.isEqualTo(ErrorCode.STORE_NOT_FOUND);
		
		verify(menuItemRepository, never()).save(any());
	}
}
