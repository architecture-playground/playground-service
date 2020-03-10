package com.playground.controller;

import com.playground.client.UserAddressClient;
import com.playground.client.dto.AddressDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("v1/feign")
@RestController
public class FeignClientController {

    private final UserAddressClient userAddressClient;

    @GetMapping("/address/all")
    public List<AddressDTO> getAllAddresses() {
        log.info("Request all addresses via feign client");
        return userAddressClient.getAll();
    }

    @PostMapping("/address/random")
    public List<AddressDTO> createRandomAddresses(@RequestParam Integer quantity) {
        log.info("Request to create random addresses via feign client");
        return userAddressClient.createRandom(quantity);
    }
}
