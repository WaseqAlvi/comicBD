package com.comicBD.productList.controller;

import com.comicBD.productList.model.products;
import com.comicBD.productList.repository.productRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
@CrossOrigin("http://localhost:3000")
public class productController {
    @Autowired
    private productRepository productRepository;

//    @PostMapping("/products")
//     public products newProduct(@RequestBody products newProduct){
//        return productRepository.save(newProduct);
//    }
    @PostMapping("/add")
    public ResponseEntity<products> newProduct(@RequestBody products newProduct){
        return new ResponseEntity<products>(productRepository.save(newProduct), HttpStatus.CREATED);
    }
    @GetMapping("/getAll")
     public List<products> getAllproducts(){
        return  productRepository.findAll();
    }
    @GetMapping("/get/{id}")
    public Optional<products> getProductById(@PathVariable long id){

            return productRepository.findById(id);
    }
    @PutMapping ("/update/{id}")
    public products updateProduct(@PathVariable long id,@RequestBody products prod){
        prod.setId(id);
        return productRepository.save(prod);
    }
    @DeleteMapping("/delete/{id}")
    public void deleteProduct(@PathVariable long id){
         productRepository.deleteById(id);
    }

}
