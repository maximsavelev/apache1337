package ru.psuti.apache1337.homeowners.service;

import ru.psuti.apache1337.homeowners.entity.Company;
import ru.psuti.apache1337.homeowners.exception_handling.NotFoundException;
import ru.psuti.apache1337.homeowners.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;

    private final String COMPANY_NOT_FOUND_MESSAGE = "Company with %d id not found";

    public Company findCompanyById(Long id){
      return   companyRepository.findById(id)
                .orElseThrow(
                        ()-> new NotFoundException(String.format(COMPANY_NOT_FOUND_MESSAGE,id))
                );
    }
}
