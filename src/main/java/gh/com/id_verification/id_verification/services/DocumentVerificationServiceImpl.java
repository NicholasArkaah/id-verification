package gh.com.id_verification.id_verification.services;

import gh.com.id_verification.id_verification.IdVerificationApplication;
import gh.com.id_verification.id_verification.config.AppConfig;
import gh.com.id_verification.id_verification.dtos.requests.IdVerificationRequestDto;
import gh.com.id_verification.id_verification.dtos.requests.IdVerificationServiceRequestDto;
import gh.com.id_verification.id_verification.dtos.responses.IdVerificationResponseDto;
import gh.com.id_verification.id_verification.utils.Helper;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Objects;

@Service
public class DocumentVerificationServiceImpl {

    @Autowired
    AppConfig appConfig;


    RestTemplate restTemplate = new RestTemplate();

    ResponseEntity<IdVerificationResponseDto> response;

    @Transactional
    public IdVerificationResponseDto idVerificationRequest(MultipartFile file, String request) {

        //Create NIA Id Verification service request from user request
        IdVerificationServiceRequestDto serviceRequestDto = new IdVerificationServiceRequestDto();
        serviceRequestDto.setPinNumber(request);
        serviceRequestDto.setCenter(appConfig.getCenter());
        serviceRequestDto.setDataType(Helper.getExtensionByApacheCommonLib(Objects.requireNonNull(file.getOriginalFilename())).toUpperCase());
        serviceRequestDto.setImage(Helper.convertFileToBase64(file));
        serviceRequestDto.setMerchantKey(appConfig.getMerchant_key());



        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Content-Type", "application/json");
        headers.add("Accept", "application/json");

        HashMap<String, String> requestPayload = new HashMap<>();
        requestPayload.put("pinNumber", request);
        requestPayload.put("center", appConfig.getCenter());
        requestPayload.put("dataType",Helper.getExtensionByApacheCommonLib(Objects.requireNonNull(file.getOriginalFilename())).toUpperCase());
        requestPayload.put("image",Helper.convertFileToBase64(file));
        requestPayload.put("merchantKey",appConfig.getMerchant_key());

        JSONObject requestObject = new JSONObject(requestPayload);
        System.out.println("Request Payload: "+requestObject.toString());
        //return restTemplate.postForObject(appConfig.getId_verification_url(), requestPayload, IdVerificationResponseDto.class);
       try {
             response = restTemplate.exchange(
                    appConfig.getId_verification_url(),
                    HttpMethod.POST, new HttpEntity<Object>(requestObject, headers),
                    IdVerificationResponseDto.class);
            return response.getBody();
        }catch (HttpClientErrorException ex){
          return ex.getResponseBodyAs(IdVerificationResponseDto.class);
       }

    }

    @Transactional
    public IdVerificationResponseDto idVerificationRequestNofile(IdVerificationServiceRequestDto requestDto) {

        //Create NIA Id Verification service request from user request




        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Content-Type", "application/json");
        headers.add("Accept", "application/json");

//        HashMap<String, String> requestPayload = new HashMap<>();
//        requestPayload.put("pinNumber", request);
//        requestPayload.put("center", appConfig.getCenter());
//        requestPayload.put("dataType",Helper.getExtensionByApacheCommonLib(Objects.requireNonNull(file.getOriginalFilename())).toUpperCase());
//        requestPayload.put("image",Helper.convertFileToBase64(file));
//        requestPayload.put("merchantKey",appConfig.getMerchant_key());

        JSONObject requestObject = new JSONObject(requestDto);
        System.out.println("Request Payload: "+requestObject.toString());
        //return restTemplate.postForObject(appConfig.getId_verification_url(), requestPayload, IdVerificationResponseDto.class);
        try {
            response = restTemplate.exchange(
                    appConfig.getId_verification_url(),
                    HttpMethod.POST, new HttpEntity<Object>(requestObject, headers),
                    IdVerificationResponseDto.class);
            return response.getBody();
        }catch (HttpClientErrorException ex){
            return ex.getResponseBodyAs(IdVerificationResponseDto.class);
        }

    }


}
