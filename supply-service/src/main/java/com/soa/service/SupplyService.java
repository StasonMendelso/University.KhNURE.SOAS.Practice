package com.soa.service;

import com.soa.dao.SupplyDao;
import com.soa.dto.SupplyDto;
import com.soa.dto.SupplyDtoPost;
import com.soa.entity.ShoePairInfo;
import com.soa.entity.Supply;
import com.soa.entity.SupplyStatus;
import com.soa.mapper.SupplyMapper;
import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

/**
 * @author Stanislav Hlova
 */
@Service
@AllArgsConstructor
public class SupplyService {

    private final SupplyDao supplyDao;
    private final SupplyMapper supplyMapper;

    public List<SupplyDto> getAllSupplies() {
        return supplyDao.findAll()
                .stream()
                .map(supplyMapper::toDto)
                .toList();
    }

    public SupplyDto getSupplyById(String id) {
        ObjectId objectId = new ObjectId(id);
        return supplyDao.findById(objectId)
                .map(supplyMapper::toDto)
                .orElse(null);
    }

    public BigDecimal calculateSelfPriceByShoePairInfoCode(String shoePairInfoCode) {
        Optional<Supply> supply = supplyDao.findSupplyByCode(shoePairInfoCode);
        if (supply.isEmpty()) {
            return BigDecimal.ZERO;
        }
        Supply supply1 = supply.get();
        int totalItemInSupply = supply1.getShoePairInfoSupplyList().stream()
                .mapToInt(shoePairInfoSupply -> shoePairInfoSupply.getShoePairInfoList().stream()
                        .mapToInt(value -> value.getCodeList().size())
                        .sum())
                .sum();
        BigDecimal deliveryAmountForEachItem = supply1.getDeliveryTotal().divide(BigDecimal.valueOf(totalItemInSupply), RoundingMode.CEILING);
        Optional<ShoePairInfo> pairInfo = supply1.getShoePairInfoSupplyList().stream()
                .flatMap(shoePairInfoSupply -> shoePairInfoSupply.getShoePairInfoList().stream())
                .filter(shoePairInfo -> shoePairInfo.getCodeList().contains(shoePairInfoCode))
                .findFirst();
        ShoePairInfo shoePairInfo = pairInfo.get();
        return shoePairInfo.getPrice().add(deliveryAmountForEachItem);
    }

    public SupplyDto addSupply(SupplyDtoPost supplyDtoPost) {
        Supply supply = supplyMapper.toEntity(supplyDtoPost);
        if (supplyDtoPost.getSupplyStatus() == SupplyStatus.ON_DELIVERY) {
            //without calling of generating codes and a service
            //only save in our database
            supply = supplyDao.save(supply);
        } else if (supplyDtoPost.getSupplyStatus() == SupplyStatus.SHIPPED_TO_WAREHOUSE) {
            //with calling of generating codes and adding it to the warehouse
            // save in our database and on the other service
        }
        return supplyMapper.toDto(supply);
    }

    public SupplyDto updateSupply(String id, SupplyDtoPost supplyDtoPost) {
        Supply supply = supplyMapper.toEntity(supplyDtoPost);
        Supply previousSupply = supplyDao.findById(new ObjectId(id)).get();
        if (previousSupply.getSupplyStatus() != SupplyStatus.SHIPPED_TO_WAREHOUSE
         && supply.getSupplyStatus() == SupplyStatus.SHIPPED_TO_WAREHOUSE) {
            // add to accounting service
            // get codes
        } else if (needsToUpdateShoeInAccountingService(previousSupply, supply)) {
            //update shoes in accounting service
            // get new set of codes.
        }
        Supply updated = supplyDao.update(new ObjectId(id), supply);
        return supplyMapper.toDto(updated);
    }

    private boolean needsToUpdateShoeInAccountingService(Supply previousSupply, Supply supply) {
        return false;
    }
}
