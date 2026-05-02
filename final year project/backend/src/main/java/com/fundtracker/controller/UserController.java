package com.fundtracker.controller;

import com.fundtracker.dto.ApiResponse;
import com.fundtracker.model.User;
import com.fundtracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired private UserService userService;

    @GetMapping
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<ApiResponse<List<User>>> getAll() {
        return ResponseEntity.ok(ApiResponse.success(userService.getAllUsers()));
    }

    @GetMapping("/{uid}")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<ApiResponse<User>> getByUid(@PathVariable String uid) {
        return ResponseEntity.ok(ApiResponse.success(userService.getUserByUid(uid)));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<ApiResponse<User>> addUser(@RequestBody User user) {
        return ResponseEntity.ok(ApiResponse.success("User Added Successfully.", userService.addUser(user)));
    }

    @PutMapping("/{uid}")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<ApiResponse<User>> updateUser(@PathVariable String uid, @RequestBody User user) {
        return ResponseEntity.ok(ApiResponse.success("User Updated Successfully.", userService.updateUser(uid, user)));
    }

    @DeleteMapping("/{uid}")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable String uid) {
        userService.deleteUser(uid);
        // Use successVoid() — avoids "cannot infer type arguments" on ApiResponse<Void>
        return ResponseEntity.ok(ApiResponse.successVoid("User Deleted Successfully."));
    }
}
