package Restaurant.Restaurant.User.service;

import Restaurant.Restaurant.User.Model.CustomUserDetails;
import Restaurant.Restaurant.User.Model.User;
import Restaurant.Restaurant.User.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findByUsername(username);

        optionalUser
                .orElseThrow(()->new UsernameNotFoundException("USERNAME  not found"));

        return optionalUser
                .map(CustomUserDetails::new).get();
    }
}
