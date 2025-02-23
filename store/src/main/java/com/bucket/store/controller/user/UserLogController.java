package com.bucket.store.controller.user;

import com.bucket.store.service.user.UserLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserLogController {
    private final UserLogService userLogService;
}
