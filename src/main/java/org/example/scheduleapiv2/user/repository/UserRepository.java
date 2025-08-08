package org.example.scheduleapiv2.user.repository;

import org.example.scheduleapiv2.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
