package com.bucket.store.service.user;

import com.bucket.store.repository.user.UserLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserLogService {
    private final UserLogRepository userLogRepository;
}
