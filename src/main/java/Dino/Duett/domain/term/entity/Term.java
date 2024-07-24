package Dino.Duett.domain.term.entity;

import Dino.Duett.domain.term.enums.TermType;
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
    private TermType type;

    @Builder
    public Term(String content, Long version, TermType type) {
        this.content = content;
        this.version = version;
        this.type = type;
        this.createdDate = LocalDate.now();
    }
}
