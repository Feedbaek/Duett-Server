package Dino.Duett.domain.term.repository;


import Dino.Duett.domain.term.entity.Term;
import Dino.Duett.domain.term.enums.TermType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TermRepository extends JpaRepository<Term, Long> {
    Optional<Term> findTopByTypeOrderByCreatedDateDesc(TermType type);
}