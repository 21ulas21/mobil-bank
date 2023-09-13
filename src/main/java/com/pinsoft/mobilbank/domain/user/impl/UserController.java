package com.pinsoft.mobilbank.domain.user.impl;

import com.pinsoft.mobilbank.domain.user.api.UserDto;
import com.pinsoft.mobilbank.domain.user.api.UserFriendsDto;
import com.pinsoft.mobilbank.domain.user.api.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private final UserService service;

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable(value = "id") String id){
        var result = service.getUserById(id);
        return ResponseEntity.ok(UserResponse.toResponse(result));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable(value = "id") String id,
                                                   @RequestBody UserRequest request){
        var result = service.updateUser(id,request.toDto());
        return ResponseEntity.ok(UserResponse.toResponse(result));
    }
    @GetMapping
    public ResponseEntity<UserResponse> getAuthenticateUser(){
        var result = UserResponse.toResponse(service.getAuthenticateUser());
        return ResponseEntity.ok(result);
    }
    @PostMapping("/add-friends/{id}")
    public ResponseEntity<UserResponse> addFriend(@PathVariable(value = "id") String id){
        var result = service.addFriend(id);
        return ResponseEntity.ok(UserResponse.toResponse(result));
    }
    @PostMapping("/remove-friends/{id}")
    public ResponseEntity<UserResponse> removeFriend(@PathVariable(value = "id") String id){
        service.removeFriend(id);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/my-friends")
    public ResponseEntity<List<UserFriendsDto>> getMyFriends(){
        var result = service.getMyFriends();
        return ResponseEntity.ok(result);
    }
    @PutMapping("/add-money/{id}")
    public ResponseEntity<UserResponse> addMoney(@PathVariable(value = "id") String id,
                                                 @RequestParam Double money){
        var result = service.addMoney(id,money);
        return ResponseEntity.ok(UserResponse.toResponse(result));
    }



}
