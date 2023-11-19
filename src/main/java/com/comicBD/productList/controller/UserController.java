package com.comicBD.productList.controller;

import com.comicBD.productList.exception.UserNotFoundException;
import com.comicBD.productList.model.User;
import com.comicBD.productList.model.products;
import com.comicBD.productList.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@RestController
@CrossOrigin("http://localhost:3000")
public class UserController {

    @Autowired
    private UserRepository userRepository;





    @PostMapping("/user")
    User newUser(@RequestBody User newUser){
        return userRepository.save(newUser);
    } //newuser = {username: samir,username, email, password:123}

    @GetMapping("/users")
    List<User> getAllUsers(){
        return userRepository.findAll();

    }



    @PutMapping("/changePass")
    public ResponseEntity<User> updateUser(@RequestBody User newUser) {
        Optional<User> userOptional = userRepository.findByUsernameAndPassword(newUser.getUsername(), newUser.getPassword());
        System.out.println("Received request with username: " + newUser.getUsername());
        System.out.println("Received request with oldPassword: " + newUser.getPassword());
        System.out.println("Received request with newPassword: " + newUser.getNewPassword());
        System.out.println("Received request with userOptional: " + userOptional);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setPassword(newUser.getNewPassword());
            userRepository.save(user);
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PutMapping("/forget")
    public ResponseEntity<User> changePass(@RequestBody User newUser) {
        Optional<User> userOptional = userRepository.findByUsernameAndMobileNo(newUser.getUsername(), newUser.getMobileNo());
        System.out.println("Received request with username: " + newUser.getUsername());

        System.out.println("Received request with newPassword: " + newUser.getNewPassword());
        System.out.println("Received request with userOptional: " + userOptional);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setPassword(newUser.getNewPassword());
            userRepository.save(user);
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }


    @PostMapping("/login")
    public ResponseEntity<User> loginUser(@RequestBody User loginUser) {
        Optional<User> userOptional = userRepository.findByUsernameAndPassword(loginUser.getUsername(), loginUser.getPassword());

        if (userOptional.isPresent()) {

            User authenticatedUser = userOptional.get();

            authenticatedUser.setLogin(true); // Set login property to true
            userRepository.save(authenticatedUser); // Save the updated user object back to the database
            return ResponseEntity.ok(authenticatedUser); // Return the user object
        } else {
            // Invalid credentials
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }






    @GetMapping("/user/{id}")
    public User getUserById(@PathVariable Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        return userOptional.orElse(null); // Returns null if user not found
    }

    @PutMapping("/users/{id}")
    User updateUser(@RequestBody User newUser, @PathVariable Long id) {
        newUser.setId(id);
        return userRepository.save(newUser);
    }
//    @PutMapping ("/update/{id}")
//    public products updateProduct(@PathVariable long id, @RequestBody products prod){
//        prod.setId(id);
//        return productRepository.save(prod);
//    }

    @DeleteMapping("/user/{id}")
    String deleteuser(@PathVariable Long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException(id);
        }
        userRepository.deleteById(id);
        return "User with " + id + " has been deleted successfully.";

    }

}