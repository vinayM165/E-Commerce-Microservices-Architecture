package com.vinayM.inventoryservice.Controller;

import com.vinayM.inventoryservice.Model.Inventory;
import com.vinayM.inventoryservice.Repository.InventoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {
    @Autowired
    InventoryRepo repo;
    @GetMapping("/{sku-code}")
    public ResponseEntity getQuantity(@PathVariable("sku-code")List<String> skuCode){
        try {
            return ResponseEntity.ok(repo.findByskuCodeIn(skuCode));
        }catch (Exception e){
            e.printStackTrace();
            return  ResponseEntity.badRequest().build();
        }
    }
}
