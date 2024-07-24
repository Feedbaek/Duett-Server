package Dino.Duett.domain.term.service;

import Dino.Duett.domain.term.dto.response.TermResponse;
import Dino.Duett.domain.term.entity.Term;
import Dino.Duett.domain.term.enums.TermType;
import Dino.Duett.domain.term.repository.TermRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class TermService {
    private final TermRepository termRepository;

    public void createSignUpTerms() throws IOException {
        Term term = Term.builder()
                .type(TermType.SIGN_UP)
                .content(readFileContent("signup-policy.txt"))
                .version(getNextVersion(TermType.SIGN_UP))
                .build();
        termRepository.save(term);
    }

    public void createPrivacyPolicyTerms() throws IOException {
        Term term = Term.builder()
                .type(TermType.PRIVACY_POLICY)
                .content(readFileContent("privacy-policy.txt"))
                .version(getNextVersion(TermType.PRIVACY_POLICY))
                .build();
        termRepository.save(term);
    }

    public TermResponse getLatestTerms(TermType type) {
        return TermResponse.builder()
                .signUpTerm(getLatestSingUpTerms().orElse(null))
                .privacyPolicyTerm(getLatestPrivacyPolicyTerms().orElse(null))
                .build();
    }

    public Optional<Term> getLatestSingUpTerms() {
        return termRepository.findTopByTypeOrderByCreatedDateDesc(TermType.SIGN_UP);
    }

    public Optional<Term> getLatestPrivacyPolicyTerms() {
        return termRepository.findTopByTypeOrderByCreatedDateDesc(TermType.PRIVACY_POLICY);
    }

    private Long getNextVersion(TermType type) {
        Optional<Term> latestTerm = termRepository.findTopByTypeOrderByCreatedDateDesc(type);
        return latestTerm.map(t -> t.getVersion() + 1).orElse(1L);
    }

    public String readFileContent(String fileName) throws IOException {
        ClassPathResource resource = new ClassPathResource(fileName);
        Path path = resource.getFile().toPath();
        return Files.readString(path);
    }
}
