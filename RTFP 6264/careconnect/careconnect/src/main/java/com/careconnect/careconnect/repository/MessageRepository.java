package com.careconnect.careconnect.repository;

import com.careconnect.careconnect.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
    void deleteBySender(String sender); // 👈 Add this line
}
