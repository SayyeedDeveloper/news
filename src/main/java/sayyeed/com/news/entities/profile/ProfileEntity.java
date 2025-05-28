package sayyeed.com.news.entities.profile;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import sayyeed.com.news.enums.profile.ProfileStatusEnum;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "users")
public class ProfileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String name;

    @Column
    private String surname;

    @Column
    private  String username;

    @Column
    private String password;

    @Enumerated(EnumType.STRING)
    @Column
    private ProfileStatusEnum status;

    @Column
    private Boolean visible = Boolean.TRUE;

    @Column
    private LocalDateTime createdDate;

    @Column
    private String photoId;

    @OneToMany(mappedBy = "profile")
    private List<ProfileRoleEntity> roleList;
}
