package gh.com.id_verification.id_verification.services;

import gh.com.id_verification.id_verification.config.AppConfig;
import gh.com.id_verification.id_verification.dtos.requests.IdVerificationServiceRequestDto;
import gh.com.id_verification.id_verification.utils.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.Objects;

@Service
public class DocumentVerificationServiceImpl {

    @Autowired
    AppConfig appConfig;


    RestTemplate restTemplate = new RestTemplate();

    ResponseEntity<String> response;

    @Transactional
    public String idVerificationRequest(MultipartFile file, String request) {

        //Create NIA Id Verification service request from user request
        IdVerificationServiceRequestDto serviceRequestDto = new IdVerificationServiceRequestDto();
        serviceRequestDto.setPinNumber(request);
        serviceRequestDto.setCenter(appConfig.getCenter());
        serviceRequestDto.setDataType(Helper.getExtensionByApacheCommonLib(Objects.requireNonNull(file.getOriginalFilename())).toUpperCase());
        serviceRequestDto.setImage(Helper.convertFileToBase64(file));
        serviceRequestDto.setMerchantKey(appConfig.getMerchant_key());



        IdVerificationServiceRequestDto requestDto = new IdVerificationServiceRequestDto();
        requestDto.setMerchantKey(appConfig.getMerchant_key());
        requestDto.setCenter(appConfig.getCenter());
        requestDto.setDataType(Helper.getExtensionByApacheCommonLib(Objects.requireNonNull(file.getOriginalFilename())).toUpperCase());
        requestDto.setImage(Helper.convertFileToBase64(file));
        requestDto.setPinNumber(request);
        try {
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            HttpEntity<IdVerificationServiceRequestDto> entity = new HttpEntity<>(requestDto, httpHeaders);
            response = restTemplate.exchange(
                    appConfig.getId_verification_url(),
                    HttpMethod.POST,
                    entity,
                    String.class);
            return response.getBody();
        }catch (HttpClientErrorException ex){
            return ex.getResponseBodyAs(String.class);
        }

    }

    @Transactional
    public String idVerificationRequestNoFile(IdVerificationServiceRequestDto requestDto) {

        //Create NIA Id Verification service request from user request
        try {
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            HttpEntity<IdVerificationServiceRequestDto> entity = new HttpEntity<>(requestDto, httpHeaders);
            response = restTemplate.exchange(
                    appConfig.getId_verification_url(),
                    HttpMethod.POST,
                    entity,
                    String.class);
            return response.getBody();
        }catch (HttpClientErrorException ex){
            return ex.getResponseBodyAs(String.class);
        }

    }


}
