package com.vinayM.inventoryservice.Service;

import com.vinayM.inventoryservice.DTO.InventoryRequest;
import com.vinayM.inventoryservice.Exception.InventoryNotFoundException;
import com.vinayM.inventoryservice.Model.Inventory;
import com.vinayM.inventoryservice.Repository.InventoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryService {
    @Autowired
    InventoryRepo repo;
    public ResponseEntity<String> updateProdQuantity(Long id, InventoryRequest request) throws InventoryNotFoundException {
        Inventory inventory = repo.findById(id).orElseThrow(()->new InventoryNotFoundException("Inventory for this id is not found in record."));
        inventory.setQuantity(request.getQuantity());
        repo.save(inventory);
        return ResponseEntity.ok("Product quantity has been added successfully!!!");
    }

    public ResponseEntity<List<Inventory>> getProductBySkuCodes(List<String> skuCode) {
        try {
            List<Inventory> inventoryList = repo.findByskuCodeIn(skuCode);

            if (!inventoryList.isEmpty()) {
                return ResponseEntity.ok(inventoryList);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (Exception e) {
            // Log the exception
            //log.error("An error occurred while retrieving inventory by sku codes", e);

            // Return a 500 status code for other exceptions
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    public ResponseEntity<Inventory> getProductBySkuCode(String skuCode) {
        try {
            return ResponseEntity.ok(repo.findByskuCode(skuCode));
        }catch (Exception e){
            e.printStackTrace();
            return  ResponseEntity.badRequest().build();
        }
    }

    public ResponseEntity<List<Inventory>> getAllInventory() {
        try {
            return ResponseEntity.ok().body(repo.findAll());
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }
}
