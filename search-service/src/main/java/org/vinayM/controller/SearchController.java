package org.vinayM.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.vinayM.model.Product;
import org.vinayM.service.SearchService;

import java.util.List;

@RestController
@RequestMapping("/api/search")
public class SearchController {
    @Autowired
    SearchService service;
    @GetMapping
    public ResponseEntity getAll(){
        List<Product> li = service.getAll();
        return ResponseEntity.ok(li);
    }
    @GetMapping("{name}")
    public ResponseEntity getByName(@RequestParam String name){
        List<Product> li  = service.findByName(name);
        return ResponseEntity.ok(li);
    }
}
