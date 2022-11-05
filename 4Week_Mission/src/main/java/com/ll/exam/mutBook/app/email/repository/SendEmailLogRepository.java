package com.ll.exam.mutBook.app.email.repository;

import com.ll.exam.mutBook.app.email.entity.SendEmailLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SendEmailLogRepository extends JpaRepository<SendEmailLog, Long> {
}
