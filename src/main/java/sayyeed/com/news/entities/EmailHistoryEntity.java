package sayyeed.com.news.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "email_history")
@Getter
@Setter
public class EmailHistoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "body", length = 2048)
    private String body;

    @Column(name = "code")
    private String code;

    @Column(name = "username")
    private String username;

    @Column(name = "attempt_count")
    private Integer attemptCount = 0;

    @Column()
    private LocalDateTime sentTime;
}
