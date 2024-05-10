// package com.example.task.restproductapi.producttest;

// import static org.junit.jupiter.api.Assertions.*;
// import static org.mockito.Mockito.*;

// import java.util.HashSet;
// import java.util.Optional;
// import java.util.Set;

// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.mockito.MockitoAnnotations;

// import com.example.task.restproductapi.entities.NotFoundException;
// import com.example.task.restproductapi.entities.Product;
// import com.example.task.restproductapi.entities.ProductType;
// import com.example.task.restproductapi.repository.ProductTypeRepository;
// import com.example.task.restproductapi.service.ProductTypeServiceImpl;

//  class ProductTypeServiceImplTest {

//     @Mock
//     private ProductTypeRepository productTypeRepository;

//     @InjectMocks
//     private ProductTypeServiceImpl productTypeService;

//     @BeforeEach
//     public void setUp() {
//         MockitoAnnotations.initMocks(this);
//     }

//     @Test
//     public void testCreateProductType() {
//         ProductType productType = new ProductType();
//         productType.setId(1L);
//         productType.setName("Electronics");

//         when(productTypeRepository.save(productType)).thenReturn(productType);

//         ProductType createdProductType = productTypeService.createProductType(productType);

//         assertEquals(productType, createdProductType);
//         verify(productTypeRepository, times(1)).save(productType);
//     }

//     @Test
//     public void testGetAllProductTypes() {
//         Set<ProductType> productTypes = new HashSet<>();
//         productTypes.add(new ProductType(1L, "Electronics", null));
//         productTypes.add(new ProductType(2L, "Clothing", null));

//         when(productTypeRepository.findAll()).thenReturn(productTypes);

//         Set<ProductType> retrievedProductTypes = productTypeService.getAllProductTypes();

//         assertEquals(productTypes.size(), retrievedProductTypes.size());
//         assertEquals(productTypes, retrievedProductTypes);
//         verify(productTypeRepository, times(1)).findAll();
//     }
    

//     @Test
//     public void testGetProductTypeById() {
//         ProductType productType = new ProductType(1L, "Electronics", null);

//         when(productTypeRepository.findById(1L)).thenReturn(Optional.of(productType));

//         ProductType retrievedProductType = productTypeService.getProductTypeById(1L);

//         assertEquals(productType, retrievedProductType);
//         verify(productTypeRepository, times(1)).findById(1L);
//     }

//     @Test
//     public void testGetProductTypeById_NotFound() {
//         when(productTypeRepository.findById(1L)).thenReturn(Optional.empty());

//         assertThrows(NotFoundException.class, () -> productTypeService.getProductTypeById(1L));
//         verify(productTypeRepository, times(1)).findById(1L);
//     }

//     @Test
//     public void testUpdateProductType() {
//         ProductType existingProductType = new ProductType(1L, "Electronics");
//         ProductType updatedProductType = new ProductType(1L, "Electronics Updated");

//         when(productTypeRepository.findById(1L)).thenReturn(Optional.of(existingProductType));
//         when(productTypeRepository.save(existingProductType)).thenReturn(updatedProductType);

//         ProductType result = productTypeService.updateProductType(1L, updatedProductType);

//         assertEquals(updatedProductType.getName(), result.getName());
//         verify(productTypeRepository, times(1)).findById(1L);
//         verify(productTypeRepository, times(1)).save(existingProductType);
//     }

//     @Test
//     public void testDeleteProductType() {
//         ProductType productType = new ProductType(1L, "Electronics", null);

//         when(productTypeRepository.findById(1L)).thenReturn(Optional.of(productType));

//         productTypeService.deleteProductType(1L);

//         verify(productTypeRepository, times(1)).delete(productType);
//     }

//     // Additional test case for handling NotFoundException in deleteProductType()
//     @Test
//     public void testDeleteProductType_NotFound() {
//         when(productTypeRepository.findById(1L)).thenReturn(Optional.empty());

//         assertThrows(NotFoundException.class, () -> productTypeService.deleteProductType(1L));
//         verify(productTypeRepository, times(0)).delete(any());
//     }
// }
