package com.bookingtrips.booking_trips_backend.service;

import com.bookingtrips.booking_trips_backend.dao.UserRepository;
import com.bookingtrips.booking_trips_backend.dto.UserDto;
import com.bookingtrips.booking_trips_backend.dto.request.UserRequest;
import com.bookingtrips.booking_trips_backend.entity.User;
import com.bookingtrips.booking_trips_backend.exception.ResourceNotFoundException;
import com.bookingtrips.booking_trips_backend.exception.UserAlreadyExists;
import com.bookingtrips.booking_trips_backend.mapper.UserMapper;
import com.bookingtrips.booking_trips_backend.util.ReflectionUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserDto create(UserRequest request){
      if(userRepository.existsByUsername(request.getUsername())){
          throw new UserAlreadyExists(String.format(
                  "User with this %s username already exist",request.getUsername()
          ));
      }
      User user = userMapper.toEntity(request);
      setUserPasswordAndRole(request,user);
      User userInDb = userRepository.save(user);
      return userMapper.toDto(userInDb);

    }

    public UserDto getById(Long id){
        User user = userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(String.format("User with id %s not found", id))
        );
        return userMapper.toDto(user);
    }

    public List<UserDto> getAll(){
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    public UserDto update(Long id, Map<String, Object> fields){
        User userInDb = userRepository.findById(id)
                .orElseThrow( () -> new ResourceNotFoundException(String.format("User with id %s not found", id)));
        fields.forEach((key, value) ->{
            ReflectionUtil.setFieldValue(userInDb, key, value);
        });
        return userMapper.toDto(userRepository.save(userInDb));
    }
    private void setUserPasswordAndRole(UserRequest request, User user){
      user.setPassword(passwordEncoder.encode(request.getPassword()));
      user.setRole(request.getRole());
    }

    public UserDto getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User loggedUser = (User) authentication.getPrincipal();

        return userMapper.toDto(loggedUser);
    }

    public void delete(Long id){
        userRepository.deleteById(id);
    }
}
