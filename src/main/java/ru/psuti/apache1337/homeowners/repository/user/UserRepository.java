package ru.psuti.apache1337.homeowners.repository.user;

import ru.psuti.apache1337.homeowners.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    void deleteByPhone(String phone);


    List<User> findAll();

    Optional<User> findByPhone(String phone);




}
