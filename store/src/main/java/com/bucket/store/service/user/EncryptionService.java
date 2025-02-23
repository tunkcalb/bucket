package com.bucket.store.service.user;

import com.bucket.store.exception.CustomException;
import com.bucket.store.exception.error.ErrorCode;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
@RequiredArgsConstructor
public class EncryptionService {
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private static final String AES_ALGORITHM = "AES";

    @Value("${spring.aes.secret.key}")  // 32바이트 키 주입
    private String secretKeyString;

    private SecretKeySpec secretKey;  // `SecretKeySpec`으로 변환 후 사용

    @PostConstruct
    public void init() {
        byte[] keyBytes = secretKeyString.getBytes(StandardCharsets.UTF_8);
        this.secretKey = new SecretKeySpec(keyBytes, AES_ALGORITHM);
    }


    // 비밀번호 단방향 암호화
    public String hashPassword(String password) {
        return passwordEncoder.encode(password);
    }

    // 비밀번호 매칭 검증
    public boolean matchesPassword(String rawPassword, String hashedPassword) {
        return passwordEncoder.matches(rawPassword, hashedPassword);
    }

    // 양방향 AES 암호화
    public String encryptData(String data) {
        try {
            Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encryptedBytes = cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            throw new CustomException(ErrorCode.ENCRYPTION_ERROR);
        }
    }

    // 양방향 AES 복호화
    public String decryptData(String encryptedData) {
        try {
            Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] decodedBytes = Base64.getDecoder().decode(encryptedData);
            return new String(cipher.doFinal(decodedBytes), StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new CustomException(ErrorCode.DECRYPTION_ERROR);
        }
    }
}