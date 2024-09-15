package gh.com.id_verification.id_verification.dtos.responses;


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
public class IdVerificationResponseDto implements Serializable {

    @NotNull(message = "success field is missing")
    @NotBlank(message = "success value is blank")
    @JsonProperty("success")
    private String success;

    @NotNull(message = "code field is missing")
    @NotBlank(message = "code value is blank")
    @JsonProperty("code")
    private String code;

    @NotNull(message = "subcode field is missing")
    @NotBlank(message = "subcode value is blank")
    @JsonProperty("subcode")
    private String subcode;

    @NotNull(message = "data field is missing")
    @NotBlank(message = "data value is blank")
    @JsonProperty("data")
    private String data;

    @NotNull(message = "msg field is missing")
    @NotBlank(message = "msg value is blank")
    @JsonProperty("msg")
    private String msg;

}
