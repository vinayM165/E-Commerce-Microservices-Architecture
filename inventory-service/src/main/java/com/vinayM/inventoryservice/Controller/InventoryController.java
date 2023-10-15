package com.vinayM.inventoryservice.Controller;

import com.vinayM.inventoryservice.Exception.InventoryNotFoundException;
import com.vinayM.inventoryservice.Service.InventoryService;
import com.vinayM.inventoryservice.DTO.InventoryRequest;
import com.vinayM.inventoryservice.Model.Inventory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    @Autowired
    InventoryService service;

    @PostMapping("/getList")
    public ResponseEntity<?> getQuantity(@RequestBody  List<String> skuCode){
        return service.getProductBySkuCodes(skuCode);
    }
    @GetMapping("/{sku-code}")
    public ResponseEntity<?> getQuantityList(@PathVariable("sku-code") String skuCode){
        return service.getProductBySkuCode(skuCode);
    }
    @PutMapping("/{id}")
    public ResponseEntity<String> updateProdQuantity(@PathVariable String id, @RequestBody InventoryRequest request) throws InventoryNotFoundException {
        return service.updateProdQuantity(id,request);
    }

    @GetMapping
    public ResponseEntity<List<Inventory>> getAllInventory(){
        return service.getAllInventory();
    }
}
