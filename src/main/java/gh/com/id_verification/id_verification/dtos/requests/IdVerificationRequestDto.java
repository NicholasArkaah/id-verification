package gh.com.id_verification.id_verification.dtos.requests;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import java.io.Serializable;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Validated
@JsonIgnoreProperties(ignoreUnknown = true)
public class IdVerificationRequestDto implements Serializable {

    @NotNull(message = "pinNumber field is missing")
    @NotBlank(message = "pinNumber value is blank")
    @JsonProperty("pinNumber")
    private String pinNumber;

    @NotNull(message = "center field is missing")
    @NotBlank(message = "center value is blank")
    @JsonProperty("center")
    private String center;

}
