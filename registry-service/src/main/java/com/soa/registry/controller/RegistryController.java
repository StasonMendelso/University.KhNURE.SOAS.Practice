package com.soa.registry.controller;

import com.soa.registry.dto.ServiceInfoDto;
import com.soa.registry.dto.ServiceRegisterInfoDto;
import com.soa.registry.dto.ServiceUnregisterInfoDto;
import com.soa.registry.exception.ServiceInfoNotFoundException;
import com.soa.registry.service.RegistryService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author Stanislav Hlova
 */
@Controller
@RequestMapping("/api/v1/registry")
@AllArgsConstructor
public class RegistryController {

    private final RegistryService registryService;

    @GetMapping
    @ResponseBody
    public ResponseEntity<?> listAllServicesOrByName(@RequestParam(name = "service-name", required = false) String serviceName) {
        if (serviceName != null) {
            return ResponseEntity.ok(registryService.findServiceInfoByName(serviceName));
        }
        return ResponseEntity.ok(registryService.findAllServiceInfo());
    }

    @PostMapping
    @ResponseBody
    public ServiceInfoDto registerService(@RequestBody ServiceRegisterInfoDto serviceRegisterInfoDto) {
        return registryService.registerService(serviceRegisterInfoDto);
    }

    @PutMapping
    @ResponseBody
    public ServiceInfoDto unregisterService(@RequestBody ServiceUnregisterInfoDto serviceUnregisterInfoDto) {
        return registryService.unregisterService(serviceUnregisterInfoDto);
    }

    @GetMapping("/contracts")
    public String pageWithContracts() {
        return "/contractsPage";
    }


    @ExceptionHandler(ServiceInfoNotFoundException.class)
    public ResponseEntity<?> handlerException(ServiceInfoNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Service with name = " + exception.getName() + ", version = " + exception.getVersion() + " wasn't found.");
    }
}
