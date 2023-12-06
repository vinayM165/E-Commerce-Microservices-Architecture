package com.vinayM.inventoryservice.Repository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import com.vinayM.inventoryservice.Model.Inventory;
import com.vinayM.inventoryservice.Repository.InventoryRepo;
import com.vinayM.inventoryservice.Service.InventoryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class RepositoryTest {

    @Mock
    private InventoryRepo inventoryRepo;

    @InjectMocks
    private InventoryService inventoryService; // Assuming you have an InventoryService that uses InventoryRepo

    @Test
    public void testFindByskuCodeIn() {
        // Arrange
        List<String> skuCodes = Arrays.asList("SKU1", "SKU2");
        List<Inventory> expectedInventoryList = Arrays.asList(new Inventory(), new Inventory());

        // Mock the behavior of the repository
        when(inventoryRepo.findByskuCodeIn(skuCodes)).thenReturn(expectedInventoryList);

        // Act
        List<Inventory> actualInventoryList = inventoryRepo.findByskuCodeIn(skuCodes);

        // Assert
        assertEquals(expectedInventoryList, actualInventoryList);
    }

    @Test
    public void testFindByskuCode() {
        // Arrange
        String skuCode = "SKU1";
        Inventory expectedInventory = new Inventory();

        // Mock the behavior of the repository
        when(inventoryRepo.findByskuCode(skuCode)).thenReturn(expectedInventory);

        // Act
        Inventory actualInventory = inventoryRepo.findByskuCode(skuCode);

        // Assert
        assertEquals(expectedInventory, actualInventory);
    }

    @Test
    public void testExistsBySkuCode() {
        // Arrange
        String skuCode = "SKU1";

        // Mock the behavior of the repository
        when(inventoryRepo.existsBySkuCode(skuCode)).thenReturn(true);

        // Act
        boolean exists = inventoryRepo.existsBySkuCode(skuCode);

        // Assert
        assertTrue(exists);
    }
}
