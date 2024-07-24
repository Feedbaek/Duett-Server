package Dino.Duett.domain.term.dto.response;

import Dino.Duett.domain.term.entity.Term;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class TermResponse {
    @NotNull
    private Term signUpTerm;

    @NotNull
    private Term privacyPolicyTerm;
}
