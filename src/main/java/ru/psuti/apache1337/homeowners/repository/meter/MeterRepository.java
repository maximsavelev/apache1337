package ru.psuti.apache1337.homeowners.repository.meter;

import ru.psuti.apache1337.homeowners.dto.MeterDto;
import ru.psuti.apache1337.homeowners.entity.Meter;
import ru.psuti.apache1337.homeowners.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MeterRepository extends CrudRepository<Meter, Long> {
    void deleteByIdAndUser(Long id, User user);

    Meter findByIdAndUser(Long id, User user);

    List<Meter> findAllByUser(User user);
}