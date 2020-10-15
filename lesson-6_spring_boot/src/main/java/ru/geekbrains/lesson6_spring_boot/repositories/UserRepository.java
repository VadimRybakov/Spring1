package ru.geekbrains.lesson6_spring_boot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.geekbrains.lesson6_spring_boot.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findOneByLogin(String login);
}
