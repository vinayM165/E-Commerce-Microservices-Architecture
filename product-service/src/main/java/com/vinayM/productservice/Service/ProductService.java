package com.vinayM.productservice.Service;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.vinayM.productservice.DTO.ProductRequest;
import com.vinayM.productservice.DTO.ProductResponse;
import com.vinayM.productservice.Event.ProductAddEvent;
import com.vinayM.productservice.JsonConverter;
import com.vinayM.productservice.Model.Product;
import com.vinayM.productservice.OutBoundChannel;
import com.vinayM.productservice.Repository.ProductRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

import java.util.*;


@Service
public class ProductService {

    private Random random = new Random();
    private final String bucketName ="product_images_bucket_0";
    private final String objectName = LocalDateTime.now().toString() + String.valueOf(random.nextInt(10000)) + ".jpeg";

    @Autowired
    Storage storage;
    @Autowired
    private OutBoundChannel channel;
    @Autowired
    private KafkaTemplate<String, ProductAddEvent> template;
    private static final Logger log = LogManager.getLogger("ProductService Logs");
    @Autowired
    private ProductRepository repository;
    public ResponseEntity createProduct(@RequestBody ProductRequest productRequest){
        log.info("Product Create Request received!");
        String ImageUrl = null;
        try {
            try {
                MultipartFile image = productRequest.getImage();
                BlobId id = (BlobId.of(bucketName, objectName));
                BlobInfo info = BlobInfo.newBuilder(id).setContentType("image/jpeg").build();
                Blob blob = storage.create(info, productRequest.getImage().getBytes());
                ImageUrl = blob.getMediaLink();
            } catch (Exception e) {
                e.printStackTrace();
            }
            Product product = Product.builder()
                    .name(productRequest.getName())
                    .description(productRequest.getDescription())
                    .price(productRequest.getPrice())
                    .quantity(productRequest.getQuantity())
                    .brand(productRequest.getBrand())
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .imageUrl(ImageUrl)
                    .build();
            log.info("Product is getting saved....!!!");
            if (!checkIfExists(product.getName())) {
                Product p = repository.save(product);
                log.info("product is saved!!!");
                log.info("starting Kafka/pubsub event productAdd!!!");
                log.info("product received after saving to DB" + " : " + p.toString());
                ProductAddEvent event = new ProductAddEvent(p.get_id(), p.getName(), p.getQuantity());
                log.info("product event created" + " : " + event.toString());
                String json = JsonConverter.convertObjectToJson(event);
                log.info("product event json string" + " : " + json);
                channel.sendMsgToPubSub(json);
                //template.send("ProductAddTopic",new ProductAddEvent(p.getName(),p.getId().toString(),p.getQuantity()));
                log.info("pubsub/Kafka event message has been sent to topic successfully!");
                return new ResponseEntity<>(HttpStatus.CREATED);
            } else {
                throw new RuntimeException("This product Already exists!");
            }
        }catch (DuplicateFormatFlagsException exception){
            return ResponseEntity.badRequest().build();
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    public ResponseEntity<List<ProductResponse>> getAllProducts(){
        try{
            log.info("request received to get all the products!");
            List<Product> list = repository.findAll();
            log.info("fetching all the products");
            List<ProductResponse> rList = new ArrayList<>();
            for(Product p : list){
                ProductResponse response = ProductResponse.builder()
                        .name(p.getName())
                        .description(p.getDescription())
                        .price(p.getPrice())
                        .quantity(p.getQuantity())
                        .createdAt(p.getCreatedAt())
                        .updatedAt(p.getUpdatedAt())
                        .brand(p.getBrand())
                        .imageUrl(p.getImageUrl())
                        .build();
                rList.add(response);
            }
            log.info("Fetched all product list");
            return new ResponseEntity<>(rList, HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    public ResponseEntity<ProductResponse> getProduct(String id){
        try{
            log.info("received a request to get product with id " + id);
            Optional<Product> res = repository.findById(id);
           return res.map(product -> new ResponseEntity<>(mapToProductResponse(product),HttpStatus.OK))
                   .orElse(new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE));
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    private ProductResponse mapToProductResponse(Product product){
        return ProductResponse.builder()
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .brand(product.getBrand())
                .updatedAt(product.getUpdatedAt())
                .createdAt(product.getCreatedAt())
                .build();
    }
    private boolean checkIfExists(String s){
        return repository.findByName(s).isPresent();
    }
}
