package Dino.Duett.domain.term.dto.request;


import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TermCreateRequest {
    @NotNull
    private String content;
    @NotNull
    private String writer;

}
