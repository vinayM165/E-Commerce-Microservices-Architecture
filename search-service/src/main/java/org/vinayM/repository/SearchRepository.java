package org.vinayM.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.vinayM.model.Product;

public interface SearchRepository extends ElasticsearchRepository<Product,String> {
}
