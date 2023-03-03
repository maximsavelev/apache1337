package ru.psuti.apache1337.homeowners.repository.service;

import ru.psuti.apache1337.homeowners.entity.Service;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceRepository extends CrudRepository<Service, Long> {

}
