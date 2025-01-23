package com.tpe.repository;

import com.tpe.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.validation.constraints.NotBlank;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserName(String username); //username varmi yokmu diye kontrol icin bunu onceden tanimladim

    boolean existsByUserName(@NotBlank String userName);
}
