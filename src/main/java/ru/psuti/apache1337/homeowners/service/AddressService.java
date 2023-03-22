package ru.psuti.apache1337.homeowners.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.psuti.apache1337.homeowners.entity.Address;
import ru.psuti.apache1337.homeowners.exception_handling.NotFoundException;
import ru.psuti.apache1337.homeowners.repository.AddressRepository;

@Service
@RequiredArgsConstructor
public class AddressService {
    private final AddressRepository addressRepository;

    private final String ADDRESS_NOT_FOUND = "Address with id %d not found";
    public Address findAddressById(Long id){
        return addressRepository.findById(id)
                .orElseThrow(
                        ()-> new NotFoundException(String.format(ADDRESS_NOT_FOUND,id))
                );
    }
}
