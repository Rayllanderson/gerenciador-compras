package com.rayllanderson.gerenciadordecompras.core.services;

import com.rayllanderson.gerenciadordecompras.core.dtos.product.ProductPostRequestBody;
import com.rayllanderson.gerenciadordecompras.core.dtos.product.ProductPostResponseBody;
import com.rayllanderson.gerenciadordecompras.core.dtos.product.ProductPutRequestBody;
import com.rayllanderson.gerenciadordecompras.core.exceptions.NotFoundException;
import com.rayllanderson.gerenciadordecompras.core.mapper.ProductMapper;
import com.rayllanderson.gerenciadordecompras.core.model.Category;
import com.rayllanderson.gerenciadordecompras.core.model.Product;
import com.rayllanderson.gerenciadordecompras.core.repositories.ProductRepository;
import com.rayllanderson.gerenciadordecompras.core.requests.SelectItemsRequestBody;
import com.rayllanderson.gerenciadordecompras.core.requests.products.TransferProductRequestBody;
import com.rayllanderson.gerenciadordecompras.core.services.utils.UpdateData;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional(readOnly = true)
    public Page<Product> findAll(Category category, Pageable pageable){
        return productRepository.findAllByCategoryId(category.getId(), pageable);
    }

    @Transactional(readOnly = true)
    public Page<Product> findPurchased(Category category, Pageable pageable){
        List<Product> purchasedProducts =
                productRepository.findAllByCategoryId(category.getId()).stream().filter(Product::getPurchased).collect(Collectors.toList());
        return new PageImpl<>(purchasedProducts, pageable, purchasedProducts.size());
    }

    @Transactional(readOnly = true)
    public Page<Product> findNotPurchased(Category category, Pageable pageable){
        List<Product> productsNotPurchased =
                productRepository.findAllByCategoryId(category.getId())
                        .stream()
                        .filter(product -> !product.getPurchased())
                        .collect(Collectors.toList());
        return new PageImpl<>(productsNotPurchased, pageable, productsNotPurchased.size());
    }

    @Transactional(readOnly = true)
    public List<Product> findAllNonPageable(Category category){
        return productRepository.findAllByCategoryId(category.getId());
    }

    @Transactional
    public ProductPostResponseBody save(ProductPostRequestBody productPostRequestBody, Category category){
        Product product = ProductMapper.toProduct(productPostRequestBody);
        product.setCategory(category);
        return ProductMapper.toProductPostResponseBody(productRepository.save(product));
    }

    @Transactional(readOnly = true)
    public Product findById(Long productId, Long categoryId){
        return productRepository.findByIdAndCategoryId(productId, categoryId)
                .orElseThrow(() -> new NotFoundException("Produto não encontrado"));
    }

    @Transactional
    public void update(ProductPutRequestBody productPutRequestBody, Category category){
        Product product = findById(productPutRequestBody.getId(), category.getId());
        UpdateData.updateProductData(productPutRequestBody, product);
        productRepository.save(product);
    }

    @Transactional
    public void deleteById(Long id, Category category){
        findById(id, category.getId());
        productRepository.deleteById(id);
    }

    public void deleteVariousById(List<SelectItemsRequestBody> productsIds, Category category){
        productsIds.forEach(req -> this.deleteById(req.getId(), category));
    }

    @Transactional(readOnly = true)
    public Page<Product> findByName(String search, Category category, Pageable pageable){
        return productRepository.findByNameIgnoreCaseContainingAndCategoryId(search, category.getId(), pageable);
    }

    @Transactional
    public void copyProductsToAnotherCategory(TransferProductRequestBody data){
        List<Product> products = transformSelectItemsToProductList(data.getSelectItems(), data.getCurrentCategoryId());
        products.forEach(product -> {
            Product productToBeCopied = ProductMapper.createANewProduct(product);
            productToBeCopied.setId(null);
            productToBeCopied.setCategory(new Category(data.getNewCategoryId()));
            productRepository.save(productToBeCopied);
        });
    }

    @Transactional
    public void moveProductsToAnotherCategory(TransferProductRequestBody data){
        List<Product> products = transformSelectItemsToProductList(data.getSelectItems(), data.getCurrentCategoryId());
        products.forEach(product -> {
            Product productToBeMoved = ProductMapper.createANewProduct(product);
            productToBeMoved.setCategory(new Category(data.getNewCategoryId()));
            productRepository.save(productToBeMoved);
        });
    }

    private List<Product> transformSelectItemsToProductList(List<SelectItemsRequestBody> items, Long currentCategory){
        return items.stream()
                .map(req -> findById(req.getId(), currentCategory))
                .collect(Collectors.toList());
    }
}