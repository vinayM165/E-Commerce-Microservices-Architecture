package com.vinayM.inventoryservice.Repository;

import com.vinayM.inventoryservice.Model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InventoryRepo extends JpaRepository<Inventory,Long> {
    List<Inventory> findByskuCodeIn(List<String> skucode);
}
