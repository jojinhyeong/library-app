package com.group.libraryapp.service.user;

import com.group.libraryapp.domain.user.User;
import com.group.libraryapp.dto.user.reponse.UserResponse;
import com.group.libraryapp.dto.user.request.UserCreateRequest;
import com.group.libraryapp.dto.user.request.UserUpdateRequest;
import com.group.libraryapp.domain.user.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceV2 {
    private final UserRepository userRepository;

    public UserServiceV2(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //아래 있는 함수가 시작될 때 start transaction을 실행
    //함수가 예외 없이 끝나면 commit
    //문제가 있다면 rollback
    @Transactional
    public void saveUser(UserCreateRequest request) {
        userRepository.save(new User(request.getName(), request.getAge()));
    }

    @Transactional(readOnly = true)
    public List<UserResponse> getUsers() {
        return userRepository.findAll().stream()
                //.map(user -> new UserResponse(user.getId(), user.getName(),user.getAge()))
                .map(UserResponse::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void updateUser(UserUpdateRequest request) {
        //select * from user where id =?
        //Optional<User>
        User user = userRepository.findById(request.getId())
                .orElseThrow(IllegalArgumentException::new);

        user.updateName(request.getName());
        //userRepository.save(user);
    }

    @Transactional
    public void deleteUser(String name) {
        //User user = userRepository.findByName(name);

        // Optional<User> 버전
        User user = userRepository.findByName(name).orElseThrow(IllegalArgumentException::new);

        //if(user ==null){
        //    throw new IllegalArgumentException();
        // }

        userRepository.delete(user);
    }

}
