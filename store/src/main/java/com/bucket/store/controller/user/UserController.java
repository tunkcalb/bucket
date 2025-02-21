package com.bucket.store.controller.user;

import com.bucket.store.dto.Response;
import com.bucket.store.dto.user.UserLoginDto;
import com.bucket.store.dto.user.UserSignUpDto;
import com.bucket.store.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public Response userSignUp(@RequestBody UserSignUpDto.Request request) {
        userService.userSignUp(request);
        return new Response(201, "회원가입 성공");
    }

    @PostMapping("/login")
    public Response<UserLoginDto.Response> userLogin(@RequestBody UserLoginDto.Request request) {
        UserLoginDto.Response response = userService.userLogin(request);
        return new Response(200, "로그인 성공", response);
    }
}
