package sayyeed.com.news.entities.profile;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import sayyeed.com.news.enums.UserRoleEnum;

@Getter
@Setter
@Entity
@Table(name = "profile_role")
public class ProfileRoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "profile_id", updatable = false, insertable = false)
    private Integer profileId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id")
    private ProfileEntity profileEntity;

    @Column
    private UserRoleEnum role;
}
