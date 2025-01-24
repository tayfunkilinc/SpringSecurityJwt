package com.tpe.security.service;

import com.tpe.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailsImpl implements UserDetails {


    //spring security'nin istedigi useri(UserDetails) olusturacagiz
    //kendi userimizdan-->userdetailsimpl objesi olusturacagiz
    private Long id;
    private String username;
    private String password;
    private Collection<? extends  GrantedAuthority> authorities;
    //burada rollerimi alacagim GrantedAuthority ve childlari turunden degiskenleri burda generik olarak kullanacagiz

    //kendi user ===> userdetails olusturalim
    public static UserDetailsImpl build(User user){
        //roller --> simpleGrantedAuthority implements GrantedAuthority
        List<SimpleGrantedAuthority> authorities =
                user.getRoles().stream().
                map(role -> new SimpleGrantedAuthority(role.getType().name())).collect(Collectors.toList());
        //userdetailsImp
        return new UserDetailsImpl(user.getId(), user.getUserName(), user.getPassword(), authorities);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() { //suresi dolmadi
        return true;
    }

    @Override
    public boolean isAccountNonLocked() { //hesap kilitlenmis mi?
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() { //kimlik bilgileri suresi dolmus mu?
        return true;
    }

    @Override
    public boolean isEnabled() { //uyeligi aktif mi?
        return true;
    }
}
