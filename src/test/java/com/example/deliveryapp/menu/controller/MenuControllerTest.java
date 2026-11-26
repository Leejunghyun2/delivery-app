package com.example.deliveryapp.menu.controller;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.deliveryapp.global.exception.BusinessException;
import com.example.deliveryapp.global.exception.ErrorCode;
import com.example.deliveryapp.menu.domain.MenuStatus;
import com.example.deliveryapp.menu.dto.MenuResponse;
import com.example.deliveryapp.menu.service.MenuService;

@WebMvcTest(MenuController.class)
public class MenuControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockitoBean
	private MenuService menuService;
	
	@Test
	@DisplayName("메뉴 단건 조회에 성공하면 200과 메뉴 정보를 응답한다")
	void getMenu() throws Exception {
		MenuResponse response = new MenuResponse(
				1L, 1L, "chicken house", "후라이드", "바삭바삭", 18000,
				MenuStatus.ON_SALE, LocalDateTime.now());
		given(menuService.findById(1L)).willReturn(response);
		
		mockMvc.perform(get("/api/menus/1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name").value("후라이드"))
				.andExpect(jsonPath("$.price").value(18000))
				.andExpect(jsonPath("$.status").value("ON_SALE"));
	}
	
	@Test
	@DisplayName("없는 메뉴를 조회하면 404와 MENU_NOT_FOUND를 응답한다")
	void getMenu_notFound() throws Exception {
		// given
		given(menuService.findById(999L))
				.willThrow(new BusinessException(ErrorCode.MENU_NOT_FOUND));

		// when & then
		mockMvc.perform(get("/api/menus/999"))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.status").value(404))
				.andExpect(jsonPath("$.code").value("MENU_NOT_FOUND"));
	}
	
	@Test
	@DisplayName("가격이 0 이하이면 400을 응답하고 서비스는 호출하지 않는다")
	void createMenu_invalidPrice() throws Exception {
		// given
		String requestJson = """
				{
				  "name": "후라이드",
				  "description": "바삭바삭",
				  "price": -1
				}
				""";

		// when & then
		mockMvc.perform(post("/api/stores/1/menus")
						.contentType(MediaType.APPLICATION_JSON)
						.content(requestJson))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.code").value("INVALID_INPUT_VALUE"))
				.andExpect(jsonPath("$.errors[0].field").value("price"));

		verify(menuService, never()).create(any(), any());
	}
	
}
