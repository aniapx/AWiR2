package edu.zut.awir2.Controllers;

import edu.zut.awir2.Models.Result;
import edu.zut.awir2.Models.User;
import edu.zut.awir2.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class UserRestController {

    @Autowired
    UserRepository repository;
    @PostMapping("/add_user")
    public ResponseEntity addUser(@RequestBody User user){
        try {
            repository.save(user);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new Result());
        }
        catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(new Error());
        }

    }

//    @DeleteMapping("/delete_user/{name}")
//    public ResponseEntity deleteUser(@PathVariable String name) {
//        try {
//            repository.deleteByName(name);
//            return ResponseEntity.status(HttpStatus.OK).body(new Result()); // obiekt Result należy zainicjować
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new Error()); // obiekt Error mależy zainicjować
//        }
//    }

    @GetMapping("/get_user/{id}")
    public ResponseEntity getUser(@PathVariable long id) {
        try {
            return ResponseEntity
                .status(HttpStatus.OK)
                .body(repository.findUserById(id));
        }
        catch (Exception e) {
            return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(new Error());// obiekt Error mależy zainicjować
        }
    }
}
