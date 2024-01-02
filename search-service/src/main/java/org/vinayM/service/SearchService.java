package org.vinayM.service;


import co.elastic.clients.elasticsearch.core.*;
import co.elastic.clients.elasticsearch.core.search.Hit;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.vinayM.model.Product;
import org.vinayM.repository.SearchRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static java.util.Collections.singletonMap;
import static org.springframework.expression.spel.SpelCompilerMode.IMMEDIATE;

@Service
public class SearchService {
    @Autowired
    RestHighLevelClient highLevelClient;

    private final String indexName = "products";
    @Autowired
    SearchRepository repository;
    public List<Product> getAll(){
        List<Product> li = StreamSupport.stream(repository.findAll().spliterator(), false)
                .collect(Collectors.toList());
        return li;
    }

    public List<Product> findByName(String name) {
        return repository.findByName(name);
    }

    public List<Product> fuzzSearch(String term) {
        return repository.findByNameFuzzy(term);
    }
}
