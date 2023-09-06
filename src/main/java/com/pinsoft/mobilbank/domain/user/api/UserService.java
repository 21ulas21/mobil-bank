package com.pinsoft.mobilbank.domain.user.api;

public interface UserService {
    UserDto getUserById(String id);
    UserDto updateUser(String id, UserDto dto);
}
