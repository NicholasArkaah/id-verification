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
public class IdVerificationServiceRequestDto implements Serializable {

    @NotNull(message = "pinNumber field is missing")
    @NotBlank(message = "pinNumber value is blank")
    @JsonProperty("pinNumber")
    private String pinNumber;

    @NotNull(message = "center field is missing")
    @NotBlank(message = "center value is blank")
    @JsonProperty("center")
    private String center;

    @NotNull(message = "dataType field is missing")
    @NotBlank(message = "dataType value is blank")
    @JsonProperty("dataType")
    private String dataType;

    @NotNull(message = "image field is missing")
    @NotBlank(message = "image value is blank")
    @JsonProperty("image")
    private String image;

    @NotNull(message = "merchantKey field is missing")
    @NotBlank(message = "merchantKey value is blank")
    @JsonProperty("merchantKey")
    private String merchantKey;
}
