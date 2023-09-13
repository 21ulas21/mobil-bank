package com.pinsoft.mobilbank.domain.user.impl;

import com.pinsoft.mobilbank.domain.user.api.UserDto;
import com.pinsoft.mobilbank.domain.user.api.UserFriendsDto;
import com.pinsoft.mobilbank.domain.user.api.UserService;
import com.pinsoft.mobilbank.library.exception.UsernameAlreadyExists;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {


    private final UserRepository repository;

    @Override
    public UserDto createUser(UserDto userDto){
        var user = toEntity(new User(),userDto);
        if (repository.findByEmail(user.getEmail()).isEmpty()){
           return toDto(repository.save(user));
        }else {
            throw new UsernameAlreadyExists("Username Already Exists");
        }

    }
    @Override
    public UserDto getUserById(String id) {
        User user = repository.findById(id).orElseThrow(EntityNotFoundException::new);
        return toDto(user);
    }
    public UserDto getUserByEmail(String email) {
        User user = repository.findByEmail(email).orElseThrow(EntityNotFoundException::new);
        return toDto(user);
    }


    @Override
    public UserDto updateUser(String id, UserDto userDto) {
        return repository.findById(id)
                .map(user -> toEntity(user,userDto))
                .map(repository::save)
                .map(this::toDto)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public UserDto getAuthenticateUser() {
        User user = getCurrentUser();
        return toDto(user);
    }


    @Override
    public UserDto addFriend(String id) {
        User user = repository.findById(id).orElseThrow(()->new UsernameNotFoundException("User Not Found"));
        User currentUser = getCurrentUser();
        if (!currentUser.getFriends().contains(user)) {
            currentUser.getFriends().add(user);
            repository.save(currentUser);

            if (!user.getFriends().contains(currentUser)) {
                user.getFriends().add(currentUser);
                repository.save(user);
            }
        }
        return toDto(currentUser);
    }

    @Override
    public void removeFriend(String id) {

        User user = repository.findById(id).orElseThrow(()->new UsernameNotFoundException("User Not Found"));
        User currentUser = getCurrentUser();
        currentUser.getFriends().remove(user);
    }
    public User getUserEntityById(String id){
        return repository.findById(id).orElseThrow(()-> new UsernameNotFoundException("User Not Found"));
    }
    public Double decAmount(String userId, Double amount){
        User user = repository.findById(userId).orElseThrow(()-> new UsernameNotFoundException("User Not Found"));
        user.setAmount(user.getAmount()-amount);
        repository.save(user);
        return user.getAmount();
    }

    public Double incAmount(String userId, Double amount){
        User user = repository.findById(userId).orElseThrow(()-> new UsernameNotFoundException("User Not Found"));
        user.setAmount(user.getAmount()+amount);
        repository.save(user);
        return user.getAmount();
    }

    public List<UserFriendsDto> getMyFriends(){
        User user = getCurrentUser();
       var userFriends =  user.getFriends().stream().map(this::toUserFriendsDto).toList();
       return userFriends;
    }

    @Override
    public UserDto addMoney(String id, Double money) {
        User user = repository.findById(id).orElseThrow(()-> new UsernameNotFoundException("User Not Found"));
        user.setAmount(user.getAmount() + money);
        return toDto(repository.save(user));
    }


    public User toEntity(User user, UserDto dto){
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword() == null ? user.getPassword() : dto.getPassword());
        user.setStatus(dto.getStatus());
        user.setAmount(dto.getAmount());
        user.setRole(dto.getRole());
        return user;
    }

    public UserDto toDto(User user){

        return UserDto.builder()
                .id(user.getId())
                .createdDate(user.getCreatedDate())
                .modified(user.getModified())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .password(user.getPassword())
                .status(user.getStatus())
                .amount(user.getAmount())
                .role(user.getRole())
                .build();
    }

    public User getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return repository.findByEmail(email).orElseThrow(EntityNotFoundException::new);
    }
    public UserFriendsDto toUserFriendsDto(User user){
        return UserFriendsDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .build();
    }
}
