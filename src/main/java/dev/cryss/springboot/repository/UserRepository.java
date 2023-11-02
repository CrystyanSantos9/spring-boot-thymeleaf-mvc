package dev.cryss.springboot.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import dev.cryss.springboot.model.UserModel;

public interface UserRepository extends CrudRepository<UserModel, UUID> {
	Optional<UserModel> findByUsername(String name);
}
