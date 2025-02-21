package com.bucket.store.service.user;

import com.bucket.store.dto.user.UserLoginDto;
import com.bucket.store.dto.user.UserSignUpDto;
import com.bucket.store.exception.CustomException;
import com.bucket.store.exception.error.ErrorCode;
import com.bucket.store.model.user.User;
import com.bucket.store.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final EncryptionService encryptionService;

    // 회원가입
    public void userSignUp(UserSignUpDto.Request request) {
        String username = request.getUsername();
        String password = encryptionService.hashPassword(request.getPassword());
        String name = encryptionService.encryptData(request.getName());

        User user = User.builder().username(username).password(password).name(name).build();
        try {
            User savedUser = userRepository.save(user);
            savedUser.setUserId("user_" + savedUser.getId());
            userRepository.save(savedUser);
        } catch (Exception e) {
            throw new CustomException(ErrorCode.DUPLICATE_USERNAME);
        }

    }

    public UserLoginDto.Response userLogin(UserLoginDto.Request request) {
        User user = userRepository.findByUsername(request.getUsername()).get();
        if (user == null) throw new CustomException(ErrorCode.UserNotFoundException);

        String rawPassword = request.getPassword();
        String hashedPassword = user.getPassword();
        if (!encryptionService.matchesPassword(rawPassword, hashedPassword)) {
            throw new CustomException(ErrorCode.INVALID_PASSWORD);
        }
        String realName = encryptionService.decryptData(user.getName());
        return UserLoginDto.Response.builder().name(realName).build();
    }
}
