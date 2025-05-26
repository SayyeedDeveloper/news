package sayyeed.com.news.entities.profile;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import sayyeed.com.news.enums.ProfileStatusEnum;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "Users")
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


}
