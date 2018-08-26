package com.sonti.chatnoir.config.security;

import com.sonti.chatnoir.entity.ChatUserAccount;
import com.sonti.chatnoir.repository.ChatUserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private ChatUserAccountRepository chatUserAccountRepository;

    public UserDetails loadUserByUsername(String login)
            throws UsernameNotFoundException {

        ChatUserAccount userAccount = chatUserAccountRepository.findByLogin(login);
        if (userAccount == null) {
            throw new UsernameNotFoundException("No user found with username: " + login);
        }
        boolean enabled = true;
        boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = true;
        ChatUser user = new ChatUser(userAccount.getLogin(), userAccount.getPassword(),
                enabled, accountNonExpired, credentialsNonExpired, accountNonLocked,
                getAuthorities(Collections.singletonList(userAccount.getRole())));
        user.setUserAccount(userAccount);
        return user;
    }

    private static List<GrantedAuthority> getAuthorities(List<String> roles) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String role : roles) {
            authorities.add(new SimpleGrantedAuthority(role));
        }
        return authorities;
    }
}

