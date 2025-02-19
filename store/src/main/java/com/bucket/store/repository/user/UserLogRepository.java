package com.bucket.store.repository.user;

import com.bucket.store.model.user.UserLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserLogRepository extends JpaRepository<UserLog, Long> {
}
