package com.vinayM.inventoryservice.Service;

import com.vinayM.inventoryservice.DTO.InventoryRequest;
import com.vinayM.inventoryservice.Exception.InventoryNotFoundException;
import com.vinayM.inventoryservice.Model.Inventory;
import com.vinayM.inventoryservice.Repository.InventoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;



@Service
public class InventoryService {
    @Autowired
    InventoryRepo repo;
    public ResponseEntity<String> updateProdQuantity(String id, InventoryRequest request) throws InventoryNotFoundException {
        Inventory inventory = repo.findById(id).orElseThrow(()->new InventoryNotFoundException("Inventory for this id is not found in record."));
        inventory.setQuantity(request.getQuantity());
        repo.save(inventory);
        return ResponseEntity.ok("Product quantity has been added successfully!!!");
    }

    public ResponseEntity<List<Inventory>> getProductBySkuCodes(List<String> skuCode) {
        try {
            return ResponseEntity.ok(repo.findByskuCodeIn(skuCode));
        }catch (Exception e){
            e.printStackTrace();
            return  ResponseEntity.badRequest().build();
        }
    }

    public ResponseEntity<List<Inventory>> getAllInventory() {
        return ResponseEntity.ok().body(repo.findAll());
    }

    public ResponseEntity<Inventory> getProductBySkuCode(String skuCode) {
        try {
            return ResponseEntity.ok(repo.findByskuCode(skuCode));
        }catch (Exception e){
            e.printStackTrace();
            return  ResponseEntity.badRequest().build();
        }
    }
}
