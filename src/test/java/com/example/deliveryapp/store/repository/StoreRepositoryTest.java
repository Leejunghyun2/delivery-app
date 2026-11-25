package com.example.deliveryapp.store.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.hibernate.Hibernate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;

import com.example.deliveryapp.store.domain.Store;
import com.example.deliveryapp.store.domain.StoreStatus;
import com.example.deliveryapp.user.domain.Role;
import com.example.deliveryapp.user.domain.User;
import com.example.deliveryapp.user.repository.UserRepository;

import jakarta.persistence.EntityManager;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class StoreRepositoryTest {

	@Autowired
	private StoreRepository storeRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private EntityManager em;
	
	@Test
	@DisplayName("가게를 저장하면 id가 생기고 상태는 OPEN이다")
	void save() {
		// given
		User owner = userRepository.save(User.builder()
				.email("test-owner@example.com")
				.password("password123")
				.name("테스트사장")
				.phone("010-0000-0000")
				.role(Role.OWNER)
				.build());

		Store store = Store.builder()
				.owner(owner)
				.name("테스트 가게")
				.phone("053-000-0000")
				.address("Daegu")
				.minOrderPrice(10000)
				.deliveryFee(2000)
				.build();
		// when
		Store saved = storeRepository.save(store);
		// then
		assertThat(saved.getId()).isNotNull();
		assertThat(saved.getStatus()).isEqualTo(StoreStatus.OPEN);
	}
	
	@Test
	@DisplayName("findAll은 사장님을 프록시로 두고 가져오지 않는다")
	void findAll_ownerIsProxy() {
		// given
		saveStoresWithDifferentOwners();

		// when
		List<Store> stores = storeRepository.findAll();

		// then
		assertThat(stores).isNotEmpty();
		assertThat(stores).allMatch(store -> !Hibernate.isInitialized(store.getOwner()));
	}

	@Test
	@DisplayName("findAllWithOwner는 사장님까지 한 번에 가져온다")
	void findAllWithOwner_ownerIsLoaded() {
		// given
		saveStoresWithDifferentOwners();

		// when
		List<Store> stores = storeRepository.findAllWithOwner();

		// then
		assertThat(stores).isNotEmpty();
		assertThat(stores).allMatch(store -> Hibernate.isInitialized(store.getOwner()));
	}

	private void saveStoresWithDifferentOwners() {
		User ownerA = userRepository.save(createOwner("owner-a@example.com"));
		User ownerB = userRepository.save(createOwner("owner-b@example.com"));
		storeRepository.save(createStore(ownerA, "가게A"));
		storeRepository.save(createStore(ownerB, "가게B"));

		em.flush();
		em.clear();
	}

	private User createOwner(String email) {
		return User.builder()
				.email(email)
				.password("password123")
				.name("사장")
				.phone("010-0000-0000")
				.role(Role.OWNER)
				.build();
	}

	private Store createStore(User owner, String name) {
		return Store.builder()
				.owner(owner)
				.name(name)
				.phone("053-000-0000")
				.address("Daegu")
				.minOrderPrice(10000)
				.deliveryFee(2000)
				.build();
	}
}
