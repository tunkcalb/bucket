package com.bucket.store.service.user;

import com.bucket.store.dto.user.UserLogDto;
import com.bucket.store.model.user.User;
import com.bucket.store.model.user.UserLog;
import com.bucket.store.repository.user.UserLogRepository;
import com.bucket.store.repository.user.UserRepository;
import com.bucket.store.util.JsonFileReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserLogService {
    private final UserLogRepository userLogRepository;
    private final UserRepository userRepository;


    public void saveUserLogsFromJson(String filePath) throws IOException {
        // JSON 파일에서 데이터 읽기
        List<UserLogDto> userLogDtoList = JsonFileReader.readAccessLogs(filePath);

        // DTO를 엔티티로 변환하여 저장
        for (UserLogDto dto : userLogDtoList) {
//            LocalDateTime accessTimestamp = JsonFileReader.parseTimestamp(dto.getAccess_timestamp());
            User user = userRepository.findByUserId(dto.getUserId()).get();
            UserLog userLog = UserLog.builder()
                    .user(user)
                    .page(dto.getPage())
                    .accessTimestamp(dto.getAccessTimestamp())
                    .build();
            userLogRepository.save(userLog);
        }
    }
}
