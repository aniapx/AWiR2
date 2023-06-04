package edu.zut.awir2.Controllers;

import edu.zut.awir2.Exceptions.StorageFileNotFoundException;
import edu.zut.awir2.Models.User;
import edu.zut.awir2.Repository.UserRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("/list")
    public String userList(Model model) {
        List<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "user-list";
    }

    @GetMapping("/add")
    public String userForm(Model model) {
        model.addAttribute("user", new User());

        return "add-user";
    }

    @GetMapping("/edit/{id}")
    public String userEditForm(@PathVariable long id, Model model) {
        User user = userRepository.findUserById(id);
        model.addAttribute("user", user);

        return "edit-user";
    }

    @GetMapping("/{id}")
    public String userInfo(@PathVariable long id, Model model) {
        User user = userRepository.findUserById(id);
        model.addAttribute("user", user);

        return "user-info";
    }

    @Transactional
    @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") long id, Model model) {
        User user = userRepository.findUserById(id);
        if (user != null) {
            long deletedCount = userRepository.deleteUserById(id);
            if (deletedCount > 0) {
                model.addAttribute("message", "User deleted successfully");
            } else {
                model.addAttribute("error", "Failed to delete user");
            }
        } else {
            model.addAttribute("error", "User not found");
        }

        List<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "user-list";
    }

    @PostMapping("/edit/{id}")
    public String userEditSubmit(@PathVariable long id, @ModelAttribute @Valid User updatedUser, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("user", updatedUser);
            return "edit-user";
        }

        User existingUser = userRepository.findById(id).orElse(null);
        existingUser.setUsername(updatedUser.getUsername());
        existingUser.setEmail(updatedUser.getEmail());

        userRepository.save(existingUser);

        model.addAttribute("user", existingUser);
        return "user-info";
    }

    @PostMapping("/add")
    public String userSubmit(@ModelAttribute @Valid User user, BindingResult bindingResult,
                             @RequestParam("file") MultipartFile file, Model model) {
        if (bindingResult.hasErrors()) {
            return "add-user";
        }

        if (!file.isEmpty()) {
            try {
                String filePath = "C:\\Users\\annap\\Desktop\\test\\" + file.getOriginalFilename();
                Path path = Paths.get(filePath);
                Files.write(path, file.getBytes());
                System.out.println("File saved to: " + path);
            } catch (IOException e) {
                e.printStackTrace();
                model.addAttribute("error", "Failed to upload the file.");
                return "add-user";
            } catch (Exception e) {
                e.printStackTrace();
                model.addAttribute("error", "Unexpected error while uploading the file.");
                return "add-user";
            }
        } else {
            model.addAttribute("error", "The file is empty.");
            return "add-user";
        }

        //store(file); // TODO zapis pliku do bazy – to dodamy w następnym ćwiczeniu

        model.addAttribute("user", user);
        userRepository.save(user);

        return "user-info";
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/")
    public RedirectView homePageRedirect() {
        return new RedirectView("/users/list");
    }
}
