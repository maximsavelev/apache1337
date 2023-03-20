package ru.psuti.apache1337.homeowners.service.user;

import ru.psuti.apache1337.homeowners.security.UserDetailsUserImpl;
import ru.psuti.apache1337.homeowners.entity.User;
import ru.psuti.apache1337.homeowners.exception_handling.NotFoundException;
import ru.psuti.apache1337.homeowners.repository.user.UserRepository;
import ru.psuti.apache1337.homeowners.dto.UserDto;
import ru.psuti.apache1337.homeowners.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.psuti.apache1337.homeowners.util.UserMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final CompanyService companyService;

    private final UserMapper userMapper;

    private final String NOT_FOUND_MESSAGE = "Request with id %d not found";

    @Override
    public UserDetailsUserImpl loadUserByUsername(String phone) throws UsernameNotFoundException {
        User user = findUserByPhone(phone);
        return new UserDetailsUserImpl(user);
    }

    public User findUserByPhone(String phone) {
        return userRepository
                .findByPhone(phone)
                .orElseThrow(()-> new NotFoundException("User not found"));
    }

    public User findUserById(Long id) {
        return userRepository
                .findById(id)
                .orElseThrow(()-> new NotFoundException(String.format(NOT_FOUND_MESSAGE,id)));
    }


    public List<User> getAll() {
        return userRepository.findAll();
    }

    public UserDto create(User user) {
        user.setCompany(companyService.findCompanyById(1L));
        userRepository.save(user);
        return userMapper.toUsedDto(user);
    }
    public void update(UserDto userTo) {
        User user = userMapper.toEntityFromDto(userTo);
        user.setCompany(companyService.findCompanyById(1L));
        userRepository.save(user);
    }
    public void delete(String phone) {
        userRepository.deleteByPhone(phone);
    }

}
