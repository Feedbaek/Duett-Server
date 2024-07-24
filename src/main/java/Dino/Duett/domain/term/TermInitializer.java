package Dino.Duett.domain.term;

import Dino.Duett.domain.term.entity.Term;
import Dino.Duett.domain.term.repository.TermRepository;
import Dino.Duett.domain.term.service.TermService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Component
public class TermInitializer implements ApplicationRunner {
    @Autowired
    private TermRepository termRepository;
    @Autowired
    private TermService termService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (termRepository.count() == 0) {
            Term signUpTerms = Term.builder()
                    //.content("Initial Sign-Up Terms Content")
                    .content(termService.readFileContent("signup-policy.txt"))
                    .version(1L)
                    .type(Term.TermsType.SIGN_UP)
                    .build();
            termRepository.save(signUpTerms);

            Term privacyPolicy = Term.builder()
                    .content(termService.readFileContent("privacy-policy.txt"))
                    .version(1L)
                    .type(Term.TermsType.PRIVACY_POLICY)
                    .build();
            termRepository.save(privacyPolicy);
        }
    }
}
