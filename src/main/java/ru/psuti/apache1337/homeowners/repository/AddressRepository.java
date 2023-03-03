package ru.psuti.apache1337.homeowners.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.psuti.apache1337.homeowners.entity.Address;

@Repository
public interface AddressRepository extends CrudRepository<Address, Long> {
}
