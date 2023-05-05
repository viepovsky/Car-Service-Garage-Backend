package com.viepovsky.user;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
interface UserRepository extends CrudRepository<User, Long> {
    List<User> findAll();

    Optional<User> findById(Long id);

    Optional<User> findByUsername(String username);
}
