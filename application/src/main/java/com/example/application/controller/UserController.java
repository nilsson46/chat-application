package com.example.application.controller;

import com.example.application.exception.UserNotFoundException;
import com.example.application.model.User;
import com.example.application.service.AuthenticationService;
import com.example.application.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationService authenticationService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PutMapping("/update")
    public ResponseEntity<?> updateUser(@RequestBody Map<String, Object> updates){
        try{
            String username = authenticationService.getLoggedInUsername();
            User user = userService.updateUser(username, updates);
            if(updates.containsKey("address")) {
                user.setAddress(updates.get("address").toString());
            }
            if(updates.containsKey("city")) {
                user.setCity(updates.get("city").toString());
            }
            userService.updateUser(username, updates);
            return ResponseEntity.ok("User updated successfully");
        } catch (UserNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("User update failed");
        }
    }
}
