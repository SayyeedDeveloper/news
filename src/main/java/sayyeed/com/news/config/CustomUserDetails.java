package sayyeed.com.news.config;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import sayyeed.com.news.enums.profile.ProfileRoleEnum;
import sayyeed.com.news.enums.profile.ProfileStatusEnum;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;


public class CustomUserDetails implements UserDetails {
    private Integer id;
    private String username;
    private String password;
    private ProfileStatusEnum status;
    private List<SimpleGrantedAuthority> roles;

    public CustomUserDetails(Integer id, String username, String password, ProfileStatusEnum status, List<ProfileRoleEnum> roleList) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.status = status;
        List<SimpleGrantedAuthority> roles = new LinkedList<>();
        roleList.forEach( role -> {
            roles.add(new SimpleGrantedAuthority(role.name()));
        });
        this.roles = roles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return status.equals(ProfileStatusEnum.ACTIVE);
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Integer getId() {
        return id;
    }
}

