package Dino.Duett.domain.term.service;

import Dino.Duett.domain.term.dto.request.TermCreateRequest;
import Dino.Duett.domain.term.dto.response.TermResponse;
import Dino.Duett.domain.term.entity.Term;
import Dino.Duett.domain.term.repository.TermRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class TermService {
    private final TermRepository termRepository;

    public void createSignUpTerms(TermCreateRequest termCreateRequest) {
        Term term = Term.builder()
                .type(Term.TermsType.SIGN_UP)
                .content(termCreateRequest.getContent())
                .writer(termCreateRequest.getWriter())
                .version(getNextVersion(Term.TermsType.SIGN_UP))
                .build();
        termRepository.save(term);
    }

    public void createPrivacyPolicyTerms(TermCreateRequest termCreateRequest) {
        Term term = Term.builder()
                .type(Term.TermsType.PRIVACY_POLICY)
                .content(termCreateRequest.getContent())
                .writer(termCreateRequest.getWriter())
                .version(getNextVersion(Term.TermsType.PRIVACY_POLICY))
                .build();
        termRepository.save(term);
    }

    public TermResponse getLatestTerms(Term.TermsType type) {
        return TermResponse.builder()
                .signUpTerm(getLatestSingUpTerms().orElse(null))
                .privacyPolicyTerm(getLatestPrivacyPolicyTerms().orElse(null))
                .build();
    }

    public Optional<Term> getLatestSingUpTerms() {
        return termRepository.findTopByTypeOrderByCreatedDateDesc(Term.TermsType.SIGN_UP);
    }

    public Optional<Term> getLatestPrivacyPolicyTerms() {
        return termRepository.findTopByTypeOrderByCreatedDateDesc(Term.TermsType.PRIVACY_POLICY);
    }

    private Long getNextVersion(Term.TermsType type) {
        Optional<Term> latestTerm = termRepository.findTopByTypeOrderByCreatedDateDesc(type);
        return latestTerm.map(t -> t.getVersion() + 1).orElse(1L);
    }
}
