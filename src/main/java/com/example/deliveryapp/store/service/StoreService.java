package com.example.deliveryapp.store.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.deliveryapp.global.exception.BusinessException;
import com.example.deliveryapp.global.exception.ErrorCode;
import com.example.deliveryapp.store.domain.Store;
import com.example.deliveryapp.store.dto.StoreCreateRequest;
import com.example.deliveryapp.store.dto.StoreResponse;
import com.example.deliveryapp.store.repository.StoreRepository;
import com.example.deliveryapp.user.domain.Role;
import com.example.deliveryapp.user.domain.User;
import com.example.deliveryapp.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StoreService {
	
	private final StoreRepository storeRepository;
	private final UserRepository userRepository;
	
	@Transactional
    public StoreResponse create(StoreCreateRequest request) {
        User owner = userRepository.findById(request.ownerId())
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        if (owner.getRole() != Role.OWNER) {
            throw new BusinessException(ErrorCode.NOT_STORE_OWNER);
        }

        Store store = storeRepository.save(request.toEntity(owner));
        return StoreResponse.from(store);
    }

    public StoreResponse findById(Long id) {
        Store store = storeRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.STORE_NOT_FOUND));
        return StoreResponse.from(store);
    }

    public List<StoreResponse> findAll() {
        return storeRepository.findAllWithOwner().stream()
                .map(StoreResponse::from)
                .toList();
    }
	
}
