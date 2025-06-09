package sayyeed.com.news.entities.sms;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "sms_history")
public class SmsHistoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column()
    private String phoneNumber;

    @Column()
    private String body;

    @Column
    private String code;

    @Column
    private Integer attemptCount = 0;

    @Column
    private LocalDateTime dateTime;
}
