package com.kiranaos.kiranaos_store_service.service;

import com.kiranaos.kiranaos_store_service.domain.Product;
import com.kiranaos.kiranaos_store_service.domain.Store;
import com.kiranaos.kiranaos_store_service.domain.enums.UnitType;
import com.kiranaos.kiranaos_store_service.dto.ProductCsvRow;
import com.kiranaos.kiranaos_store_service.dto.request.CreateProductRequest;
import com.kiranaos.kiranaos_store_service.dto.request.UpdateProductRequest;
import com.kiranaos.kiranaos_store_service.dto.response.MessageResponse;
import com.kiranaos.kiranaos_store_service.dto.response.ProductImportResponse;
import com.kiranaos.kiranaos_store_service.dto.response.ProductResponse;
import com.kiranaos.kiranaos_store_service.exception.CsvProcessingException;
import com.kiranaos.kiranaos_store_service.exception.ProductNotFoundException;
import com.kiranaos.kiranaos_store_service.repository.ProductRepository;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final StoreService storeService;

    public ProductResponse createProduct(UUID ownerId, CreateProductRequest request) {
        Store store = storeService.findStoreByOwnerId(ownerId);

        Product product = new Product();
        product.setName(request.getName());
        product.setStore(store);
        product.setCategory(request.getCategory());
        product.setUnit(request.getUnit());
        product.setPrice(request.getPrice());
        product.setGstRate(request.getGstRate());
        product.setStockQuantity(request.getStockQuantity());
        product.setReorderThreshold(request.getReorderThreshold());
        return toProductResponse(productRepository.save(product));
    }

    public ProductResponse getProduct(UUID productId, UUID ownerId) {
        Store store = storeService.findStoreByOwnerId(ownerId);
        Product product = productRepository.findByIdAndStoreId(productId, store.getId()).orElseThrow(() -> new ProductNotFoundException("Product not found"));
        return toProductResponse(product);
    }

    public List<ProductResponse> getProducts(UUID ownerId) {
        Store store = storeService.findStoreByOwnerId(ownerId);
        List<Product> products = productRepository.findAllByStoreIdAndIsDeletedFalse(store.getId());
        return products.stream().map(this::toProductResponse).toList();
    }

    public ProductResponse updateProduct(UUID productId, UUID ownerId, UpdateProductRequest request) {
        Store store = storeService.findStoreByOwnerId(ownerId);
        Product product = productRepository.findByIdAndStoreId(productId, store.getId()).orElseThrow(() -> new ProductNotFoundException("Product not found"));
        if (request.getName() != null) {
            product.setName(request.getName());
        }
        if (request.getCategory() != null) {
            product.setCategory(request.getCategory());
        }
        if (request.getUnit() != null) {
            product.setUnit(request.getUnit());
        }
        if (request.getPrice() != null) {
            product.setPrice(request.getPrice());
        }
        if (request.getGstRate() != null) {
            product.setGstRate(request.getGstRate());
        }
        if (request.getStockQuantity() != null) {
            product.setStockQuantity(request.getStockQuantity());
        }
        if (request.getReorderThreshold() != null) {
            product.setReorderThreshold(request.getReorderThreshold());
        }
        return toProductResponse(productRepository.save(product));
    }

    public MessageResponse deleteProduct(UUID productId, UUID ownerId) {
        Store store = storeService.findStoreByOwnerId(ownerId);
        Product product = productRepository.findByIdAndStoreId(productId, store.getId()).orElseThrow(() -> new ProductNotFoundException("Product not found"));
        product.setIsDeleted(true);
        productRepository.save(product);
        return new MessageResponse("Product deleted!");
    }

    public List<ProductResponse> getProductsByCategory(UUID ownerId, String category) {
        Store store = storeService.findStoreByOwnerId(ownerId);
        List<Product> products = productRepository.findAllByStoreIdAndCategoryAndIsDeletedFalse(store.getId(), category);
        return products.stream().map(this::toProductResponse).toList();
    }

    public List<ProductResponse> findLowStockProducts(UUID ownerId) {
        Store store = storeService.findStoreByOwnerId(ownerId);
        List<Product> products = productRepository.findLowStockProducts(store.getId());
        return products.stream().map(this::toProductResponse).toList();
    }

    public Product findByProductId(UUID productId, UUID storeId) {
        return productRepository.findByIdAndStoreId(productId, storeId).orElseThrow(() -> new ProductNotFoundException("Product not found"));
    }

    public ProductImportResponse importProducts(UUID ownerId, MultipartFile file) {
        Store store = storeService.findStoreByOwnerId(ownerId);
        AtomicInteger successCount = new AtomicInteger();
        AtomicInteger failedCount = new AtomicInteger();
        List<String> failedReasons = new ArrayList<>();
        try {
            Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
            CsvToBean<ProductCsvRow> csvToBean = new CsvToBeanBuilder<ProductCsvRow>(reader).withType(ProductCsvRow.class).withIgnoreLeadingWhiteSpace(true).build();
            List<ProductCsvRow> products = csvToBean.parse();

            products.forEach(product -> {
                Optional<String> result = validateCsv(product);
                if (result.isEmpty()) {
                    Product product1 = new Product();
                    product1.setName(product.getName());
                    product1.setCategory(product.getCategory());
                    try {
                        product1.setUnit(UnitType.valueOf(product.getUnit()));
                    } catch (IllegalArgumentException e) {
                        failedCount.getAndIncrement();
                        failedReasons.add("Invalid unit: " + product.getUnit());
                        return;
                    }
                    product1.setPrice(product.getPrice());
                    product1.setGstRate(product.getGstRate());
                    product1.setStockQuantity(product.getStockQuantity());
                    product1.setReorderThreshold(product.getReorderThreshold());
                    product1.setStore(store);
                    productRepository.save(product1);
                    successCount.getAndIncrement();
                } else {
                    failedCount.getAndIncrement();
                    failedReasons.add(result.get());
                }
            });
            return ProductImportResponse.builder().successCount(successCount.get()).failedCount(failedCount.get()).failedReasons(failedReasons).build();
        } catch (IOException e) {
            throw new CsvProcessingException("Error reading CSV file");
        }
    }

    public void saveProduct(Product product) {
        productRepository.save(product);
    }

    private ProductResponse toProductResponse(Product product) {
        return ProductResponse.builder().id(product.getId()).storeId(product.getStore().getId()).name(product.getName()).category(product.getCategory()).unit(product.getUnit()).price(product.getPrice()).gstRate(product.getGstRate()).stockQuantity(product.getStockQuantity()).reorderThreshold(product.getReorderThreshold()).build();
    }

    private Optional<String> validateCsv(ProductCsvRow product) {
        if (product.getName() == null) {
            return Optional.of("Product name is required");
        }
        if (product.getCategory() == null) {
            return Optional.of("Product category is required");
        }
        if (product.getUnit() == null) {
            return Optional.of("Unit is required");
        }
        if (product.getPrice() == null) {
            return Optional.of("Price is required");
        }
        if (product.getStockQuantity() == null) {
            return Optional.of("Stock quantity is required");
        }
        return Optional.empty();
    }
}
