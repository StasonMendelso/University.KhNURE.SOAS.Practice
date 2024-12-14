package com.soa.controller;

import com.soa.controller.doc.SupplyController;
import com.soa.dto.SupplyDto;
import com.soa.dto.SupplyDtoPost;
import com.soa.service.SupplyService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author Stanislav Hlova
 */
@RestController
@RequestMapping("/api/v1/supply")
@AllArgsConstructor
public class SupplyControllerImpl implements SupplyController {

    private final SupplyService supplyService;

    @Override
    @GetMapping()
    public List<SupplyDto> listAllSupplies() {
        return supplyService.getAllSupplies();
    }

    @Override
    @GetMapping("/{id}")
    public SupplyDto getSupplyById(@PathVariable("id") String id) {
        return supplyService.getSupplyById(id);
    }

    @Override
    @PostMapping()
    public SupplyDto createSupply(@RequestBody SupplyDtoPost supplyDtoPost) {
        return supplyService.addSupply(supplyDtoPost);
    }

    @Override
    @PutMapping("/{id}")
    public SupplyDto updateSupply(@PathVariable("id") String id, @RequestBody SupplyDtoPost supplyDtoPost) {
        return supplyService.updateSupply(id, supplyDtoPost);
    }

    @Override
    @GetMapping("/self-price")
    public Map<String, Object> getSelfPrice(@RequestParam("shoe-pair-info-code") String shoePairInfoCode) {
        return Map.of("selfPrice", supplyService.calculateSelfPriceByShoePairInfoCode(shoePairInfoCode));
    }
}
