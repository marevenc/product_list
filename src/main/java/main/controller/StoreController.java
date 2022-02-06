package main.controller;

import main.model.Lists;
import main.repository.ListRepo;
import main.model.Product;
import main.repository.ProductsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/store")
public class StoreController {
    @Autowired
    private ProductsRepo productsRepo;

    @Autowired
    private ListRepo listRepo;

    @PostMapping("/save_product")
    public String saveProduct(@RequestBody Product product) {
        productsRepo.save(product);
        return "Product saved";
    }

    @PostMapping("/save_list")
    public String saveList(@RequestBody Lists list) {
        for(Product product : list.getProducts()) {
            if(productsRepo.findById(product.getId()).isEmpty()){
                productsRepo.save(product);
            }
        }
        listRepo.save(list);
        return "List saved";
    }

    @PostMapping("/save_product_to_list")
    public String addProductToList(@RequestBody Lists list) {
        Optional<Lists> optListToUpdate = listRepo.findById(list.getId());

        if(optListToUpdate.isEmpty()) {
            return "List " + list.getName() + " doesn't exist";
        }

        Lists listToUpdate = optListToUpdate.get();
        for(Product product : list.getProducts()) {
            if(productsRepo.findById(product.getId()).isEmpty()) {
                productsRepo.save(product);
            }

            if(!listToUpdate.getProducts().contains(product)) {
                listToUpdate.addProduct(product);
            }
        }

        listRepo.save(listToUpdate);
        return "Product list " + list.getName() + " is updated";
    }

    @GetMapping("/getProductById/{id}")
    public Optional<Product> getProductById(@PathVariable long id) {
        return productsRepo.findById(id);
    }

    @GetMapping("/getAllProducts")
    public List<Product> getAllProducts() {
        return productsRepo.findAll();
    }

    @GetMapping("/getListById/{id}")
    public Optional<Lists> getListById(@PathVariable long id) {
        return listRepo.findById(id);
    }
}
