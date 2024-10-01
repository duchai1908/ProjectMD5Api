package com.ra.projectmd5.security.principle;


import com.ra.projectmd5.model.entity.User;
import com.ra.projectmd5.model.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
//Xử lý sau khi gửi lên dựa vào username
public class UserDetailCustomService implements UserDetailsService {
    private final IUserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return UserDetailCustom.builder()
                .users(user)
                .authorities(user.getRoles().stream().map(roles-> new SimpleGrantedAuthority(roles.getRoleName().name())).toList())
                .build();
    }
}
