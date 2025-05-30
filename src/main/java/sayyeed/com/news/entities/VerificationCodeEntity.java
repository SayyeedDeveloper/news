package sayyeed.com.news.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "verification_code")
@Getter
@Setter
public class VerificationCodeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "username")
    private String username;

    @Column()
    private LocalDateTime sentTime;
}
