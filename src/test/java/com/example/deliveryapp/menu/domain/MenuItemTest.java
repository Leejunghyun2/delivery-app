package com.example.deliveryapp.menu.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class MenuItemTest {

	@Test
	@DisplayName("메뉴를 품절 처리하면 상태가 SOLD_OUT으로 바뀐다")
	void soldOut() {
		
		MenuItem menu = MenuItem.builder()
				.name("후라이드")
				.description("바삭바삭")
				.price(18000)
				.build();
		
		menu.soldOut();
		
		assertThat(menu.getStatus()).isEqualTo(MenuStatus.SOLD_OUT);
	}
	
	@Test
	@DisplayName("새로 만든 메뉴는 판매 중이다")
	void createdMenuIsOnSale() {
		MenuItem menu = MenuItem.builder()
				.name("양념치킨")
				.description("달짝지근")
				.price(20000)
				.build();
		
		assertThat(menu.getStatus()).isEqualTo(MenuStatus.ON_SALE);
	}
	
	@Test
	@DisplayName("품절된 메뉴를 판매 재개하면 판매 중 상태가 된다")
	void resumeSale() {
		
		//given
		MenuItem menu = MenuItem.builder()
				.name("양념치킨")
				.description("달짝지근")
				.price(20000)
				.build();
		menu.soldOut();
		
		//when
		menu.resumeSale();
		
		//then
		assertThat(menu.getStatus()).isEqualTo(MenuStatus.ON_SALE);
	}
	
	
}


