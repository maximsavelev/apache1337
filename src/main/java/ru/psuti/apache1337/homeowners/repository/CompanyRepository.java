package ru.psuti.apache1337.homeowners.repository;

import ru.psuti.apache1337.homeowners.entity.Company;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CompanyRepository extends CrudRepository<Company, Long> {

    Optional<Company> findById(Long id);
}
