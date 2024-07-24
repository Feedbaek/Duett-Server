package Dino.Duett.domain.term.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Entity
@Table(name = "term")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Term {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Lob
    @Column(columnDefinition = "TEXT")
    private String content;
    private Long version;
    private LocalDate createdDate;

    @Enumerated(EnumType.STRING)
    private TermsType type;

    public enum TermsType {
        SIGN_UP,
        PRIVACY_POLICY
    }

    @Builder
    public Term(String content, Long version, TermsType type) {
        this.content = content;
        this.version = version;
        this.type = type;
        this.createdDate = LocalDate.now();
    }
}
