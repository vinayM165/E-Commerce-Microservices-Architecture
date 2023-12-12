package org.vinayM.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.vinayM.model.Product;
import org.vinayM.repository.SearchRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class SearchService {

    @Autowired
    SearchRepository repository;
    public List<Product> getAll(){
        List<Product> li = StreamSupport.stream(repository.findAll().spliterator(), false)
                .collect(Collectors.toList());
        return li;
    }
}
