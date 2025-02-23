package com.bucket.store.service.user;

import com.bucket.store.dto.user.UserLogDto;
import com.bucket.store.dto.user.UserLoginDto;
import com.bucket.store.dto.user.UserSignUpDto;
import com.bucket.store.exception.CustomException;
import com.bucket.store.exception.error.ErrorCode;
import com.bucket.store.model.user.User;
import com.bucket.store.model.user.UserLog;
import com.bucket.store.repository.user.UserRepository;
import com.bucket.store.util.JsonFileReader;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final EncryptionService encryptionService;

    @Value("${spring.user.password}")
    private String password;

    // 회원가입
    public void userSignUp(UserSignUpDto.Request request) {
        String username = request.getUsername();
        String password = encryptionService.hashPassword(request.getPassword());
        String name = encryptionService.encryptData(request.getName());

        User user = User.builder().username(username).password(password).name(name).build();
        try {
            User savedUser = userRepository.save(user);
//            savedUser.setUserId("user_" + savedUser.getId());
//            userRepository.save(savedUser);
        } catch (Exception e) {
            throw new CustomException(ErrorCode.DUPLICATE_USERNAME);
        }

    }

    public UserLoginDto.Response userLogin(UserLoginDto.Request request) {
        User user = userRepository.findByUsername(request.getUsername()).get();
        if (user == null) throw new CustomException(ErrorCode.USER_NOT_FOUND);

        String rawPassword = request.getPassword();
        String hashedPassword = user.getPassword();
        if (!encryptionService.matchesPassword(rawPassword, hashedPassword)) {
            throw new CustomException(ErrorCode.INVALID_PASSWORD);
        }
        String realName = encryptionService.decryptData(user.getName());
        return UserLoginDto.Response.builder().name(realName).build();
    }

    public void saveUserFromJson(String filePath) throws IOException {
        // JSON 파일에서 데이터 읽기
        List<UserLogDto> userLogDtoList = JsonFileReader.readAccessLogs(filePath);

        Set<String> set = new HashSet<>();
        // DTO를 엔티티로 변환하여 저장
        for (UserLogDto dto : userLogDtoList) {
            set.add(dto.getUserId());
        }

        List<User> userList = new ArrayList<>();
        for (String userId : set) {
//            Long id = Long.parseLong(userId.replace("user_", ""));
            String hashedpassword = encryptionService.hashPassword(password);
            String name = encryptionService.encryptData("정현우");
            User user = User.builder()
//                    .id(id)
                    .userId(userId)
                    .username(userId)
                    .password(hashedpassword)
                    .name(name).build();
            userList.add(user);
        }
        userRepository.saveAll(userList);
    }
}
