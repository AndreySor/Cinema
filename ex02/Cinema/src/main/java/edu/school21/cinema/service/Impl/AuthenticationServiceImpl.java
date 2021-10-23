package edu.school21.cinema.service.Impl;

import edu.school21.cinema.model.Authentication;
import edu.school21.cinema.model.User;
import edu.school21.cinema.repository.AuthenticationRepository;
import edu.school21.cinema.repository.UserRepository;
import edu.school21.cinema.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class AuthenticationServiceImpl implements AuthenticationService {

    private AuthenticationRepository authenticationRepository;
    private UserRepository userRepository;

    public AuthenticationServiceImpl(@Qualifier("authenticationRepositoryImpl") AuthenticationRepository authenticationRepository,
                                     @Qualifier("userRepositoryImpl") UserRepository userRepository) {
        this.authenticationRepository = authenticationRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Long authUser(User user, String ip) {
        User checkUser = userRepository.findByLogin(user.getLogin());
        if (checkUser == null) {
            user.setId(userRepository.save(user));
        } else {
            user = checkUser;
        }
        authenticationRepository.save(new Authentication()
                .withIpAddress(ip)
                .withDate(new Date())
                .withUser(user));
        return user.getId();
    }

    @Override
    public List<Authentication> getAllByUserId(Long id) {
        return authenticationRepository.getAllByUserId(id);
    }
}
