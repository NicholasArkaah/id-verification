package gh.com.id_verification.id_verification.controllers;


import gh.com.id_verification.id_verification.dtos.requests.IdVerificationRequestDto;
import gh.com.id_verification.id_verification.dtos.requests.IdVerificationServiceRequestDto;
import gh.com.id_verification.id_verification.dtos.responses.IdVerificationResponseDto;
import gh.com.id_verification.id_verification.services.DocumentVerificationServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/api/v1/document-verification")
public class DocumentVerificationController {


    @Autowired
    DocumentVerificationServiceImpl documentVerificationService;

    @Operation(summary = "Query id verification upload document")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful."),
            @ApiResponse(responseCode = "500", description = "There was an error while performing id verification. kindly contact Admin for support.")
    })
    @RequestMapping(value="/verify-file", produces = { "application/json" },consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}, method = RequestMethod.POST)
    public @ResponseBody ResponseEntity<IdVerificationResponseDto> queryIdVerificationWithFile( @RequestPart(value = "file") MultipartFile file,
                                                                                       @RequestPart String idNumber){
        return ResponseEntity.status(HttpStatus.OK).body(documentVerificationService.idVerificationRequest(file, idNumber));
    }

    @Operation(summary = "Query id verification")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful."),
            @ApiResponse(responseCode = "500", description = "There was an error while performing id verification. kindly contact Admin for support.")
    })
    @RequestMapping(value="/verify", produces = { "application/json" }, method = RequestMethod.POST)
    public @ResponseBody ResponseEntity<IdVerificationResponseDto> queryIdVerification(@RequestBody @Valid IdVerificationServiceRequestDto requestDto){
        return ResponseEntity.status(HttpStatus.OK).body(documentVerificationService.idVerificationRequestNofile(requestDto));
    }

}
