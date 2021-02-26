package com.playground.client;

import com.playground.client.dto.AddressDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@FeignClient(value = "user-service", path = "v1/address")
public interface UserAddressClient {

    // TODO : move to user service and add as dependency.
    //  Provide this client in "user-api" jar after creating artifactory(jfrog) + jenkins

    @GetMapping("/all")
    List<AddressDTO> getAll();

    /**
     * see https://www.programmersought.com/article/72114451663/
     * Error message: RequestParam.vale() was empty on parameter 0
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/random")
    List<AddressDTO> createRandom(@RequestParam(value = "quantity") Integer quantity);
}
