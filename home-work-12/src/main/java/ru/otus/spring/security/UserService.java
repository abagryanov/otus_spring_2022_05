package ru.otus.spring.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.otus.spring.model.Person;
import ru.otus.spring.repository.PersonRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final PersonRepository personRepository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        UserDetails userDetails;
        Optional<Person> personOptional = personRepository.findByLogin(login);
        if (personOptional.isPresent()) {
            Person person = personOptional.get();
            userDetails = User.withUsername(person.getLogin())
                    .password(person.getPassword())
                    .roles("USER")
                    .build();
        } else {
            throw new UsernameNotFoundException("No such user");
        }
        return userDetails;
    }
}
