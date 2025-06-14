package sayyeed.com.news.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import sayyeed.com.news.entities.profile.ProfileEntity;
import sayyeed.com.news.enums.profile.ProfileRoleEnum;
import sayyeed.com.news.exceptions.AppBadException;
import sayyeed.com.news.repositories.profile.ProfileRepository;
import sayyeed.com.news.repositories.profile.ProfileRoleRepository;

import java.util.List;
import java.util.Optional;


@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private ProfileRoleRepository profileRoleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<ProfileEntity> optional = profileRepository.findByUsernameAndVisibleIsTrue(username);
        if (optional.isEmpty()) {
            throw new AppBadException("User name not found");
        }
        ProfileEntity profile = optional.get();
        List<ProfileRoleEnum> roleList = profileRoleRepository.getRoleListByProfileId(profile.getId());

        return new CustomUserDetails(profile.getId(),
                profile.getUsername(),
                profile.getPassword(),
                profile.getStatus(),
                roleList);
    }
}

