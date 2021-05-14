package com.esgi.marketplace.controller;

import com.esgi.marketplace.dao.UserDao;
import com.esgi.marketplace.exceptions.IllegalNameException;
import com.esgi.marketplace.exceptions.IllegalNameExecption;
import com.esgi.marketplace.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static org.springframework.http.ResponseEntity.notFound;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserDao userDao;

    //List des utilisateurs
    @GetMapping
    public List<User> listUser() {
        return userDao.findAll();
    }

    //Affiche l'utilisateur selon son {id}
    @GetMapping("/{id}")
    public ResponseEntity<User> findById(@PathVariable int id) {
        return userDao.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> notFound().build());
    }

    //Ajouter un utilisateur
    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody User user) {
        if ("none".equalsIgnoreCase(user.getFirstname())) {
            throw new IllegalNameException("Illegal name for none");
        }
        User created = userDao.save(user);

//        if (created == null) return ResponseEntity.noContent().build();

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }
}
