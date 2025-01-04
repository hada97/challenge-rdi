package com.rdi.MenuApp;

import com.rdi.MenuApp.domain.Product;
import com.rdi.MenuApp.exception.ProductNotFoundException;
import com.rdi.MenuApp.repository.ProductComponentsRepository;
import com.rdi.MenuApp.repository.ProductRepository;
import com.rdi.MenuApp.repository.ProductStatusRepository;
import com.rdi.MenuApp.service.MenuService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MenuAppApplicationTests {

	@Mock
	private ProductRepository productRepository;

	@Mock
	private ProductComponentsRepository productComponentsRepository;

	@Mock
	private ProductStatusRepository productStatusRepository;

	@InjectMocks
	private MenuService menuService;

	@Test
	public void testChangeStatus_InvalidStatus() {
		assertThrows(IllegalArgumentException.class, () -> menuService.changeStatus(1L, 2));
	}

	@Test
	public void testChangeStatus_ProductNotFound() {
		when(productRepository.findById(1L)).thenReturn(Optional.empty());
		assertThrows(ProductNotFoundException.class, () -> menuService.changeStatus(1L, 1));
	}

	@Test
	public void testChangeStatus_ValidStatusForChoiceProduct() {
		Product product = new Product(1L, 2); // Tipo CHOICE
		when(productRepository.findById(1L)).thenReturn(Optional.of(product));
		when(productComponentsRepository.findByParentId(1L)).thenReturn(List.of(100L));
		when(productStatusRepository.findStatusByProductId(100L)).thenReturn(1);

		menuService.changeStatus(1L, 1);
		verify(productStatusRepository).updateStatus(1L, 1);
	}

	@Test
	public void testHasActiveComponent_ActiveComponent() {
		when(productComponentsRepository.findByParentId(1L)).thenReturn(List.of(100L));
		when(productStatusRepository.findStatusByProductId(100L)).thenReturn(1);

		assertTrue(menuService.hasActiveComponent(1L, 1));
	}

	@Test
	public void testHasActiveComponent_NoActiveComponent() {
		when(productComponentsRepository.findByParentId(1L)).thenReturn(List.of(100L));
		when(productStatusRepository.findStatusByProductId(100L)).thenReturn(0);

		assertFalse(menuService.hasActiveComponent(1L, 1));
	}

	@Test
	public void testHasAllComponentsActive_AllActive() {
		when(productComponentsRepository.findByParentId(1L)).thenReturn(List.of(100L, 101L));
		when(productStatusRepository.findStatusByProductId(100L)).thenReturn(1);
		when(productStatusRepository.findStatusByProductId(101L)).thenReturn(1);

		assertTrue(menuService.hasAllComponentsActive(1L, 1));
	}

	@Test
	public void testHasAllComponentsActive_OneInactive() {
		when(productComponentsRepository.findByParentId(1L)).thenReturn(List.of(100L, 101L));
		when(productStatusRepository.findStatusByProductId(100L)).thenReturn(1);
		when(productStatusRepository.findStatusByProductId(101L)).thenReturn(0);

		assertFalse(menuService.hasAllComponentsActive(1L, 1));
	}

	@Test
	public void testUpdateProductStatus() {
		menuService.updateProductStatus(1L, 1);
		verify(productStatusRepository).updateStatus(1L, 1);
	}
}
