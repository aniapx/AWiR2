package edu.zut.awir2.Controllers;

import edu.zut.awir2.Models.Result;
import edu.zut.awir2.Models.User;
import edu.zut.awir2.Repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class UserRestController implements UserApi {

    @Autowired
    UserRepository userRepository;

    @GetMapping("/all")
    public ResponseEntity getAllUsers() {
        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(userRepository.findAll());
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(new Error("Forbidden", e.getCause()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity getUser(@PathVariable long id) {
        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(userRepository.findUserById(id));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(new Error("Forbidden", e.getCause()));
        }
    }

    @PostMapping("/")
    public ResponseEntity addUser(@RequestBody User user) {
        try {
            userRepository.save(user);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new Result(200));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(new Error("Forbidden", e.getCause()));
        }
    }

    @PutMapping("/")
    public ResponseEntity editUser(@RequestBody User updatedUser) {
        try {
            User existingUser = userRepository.findById(updatedUser.getId()).orElse(null);
            existingUser.setUsername(updatedUser.getUsername());
            existingUser.setEmail(updatedUser.getEmail());

            userRepository.save(existingUser);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new Result(200));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(new Error("Forbidden", e.getCause()));
        }
    }

    @Transactional
    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteUser(@PathVariable long id) {
        try {
            User user = userRepository.findUserById(id);
            if (user != null) {
                userRepository.deleteUserById(id);
            }
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new Result(200));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(new Error("Forbidden", e.getCause()));
        }
    }
}
