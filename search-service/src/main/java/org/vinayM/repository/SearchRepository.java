package org.vinayM.repository;

import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.vinayM.model.Product;

import java.util.List;

public interface SearchRepository extends ElasticsearchRepository<Product,String> {
    List<Product> findByName(String name);
    @Query("{\"fuzzy\" : {\"name\" : \"?0\"}}")
    List<Product> findByNameFuzzy(String term);

}
