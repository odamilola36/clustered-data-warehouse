package com.lomari.clustereddatawarehouse.service.serviceImpl;

import com.lomari.clustereddatawarehouse.dto.DealRequestDto;
import com.lomari.clustereddatawarehouse.dto.DealResponseDto;
import com.lomari.clustereddatawarehouse.exceptions.InvalidRequestFieldException;
import com.lomari.clustereddatawarehouse.exceptions.ResourceNotFoundException;
import com.lomari.clustereddatawarehouse.models.Deal;
import com.lomari.clustereddatawarehouse.repository.DealsRepository;
import com.lomari.clustereddatawarehouse.service.DealsService;
import com.lomari.clustereddatawarehouse.service.mapper.DealMapper;
import com.lomari.clustereddatawarehouse.service.validator.DealError;
import com.lomari.clustereddatawarehouse.service.validator.DealValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DealsServiceImpl implements DealsService {

    private final DealsRepository dealsRepository;

    private final DealMapper dealMapper;

    private final DealValidator validator;

    public DealsServiceImpl(DealsRepository dealsRepository, DealMapper dealMapper, DealValidator validator) {
        this.dealsRepository = dealsRepository;
        this.dealMapper = dealMapper;
        this.validator = validator;
    }

    @Override
    public DealResponseDto saveDeal(DealRequestDto dealRequestDto) throws InvalidRequestFieldException {

        List<DealError> requestFields = validator.validateRequestFields(dealRequestDto);
        List<DealError> dealExistence = validator.validateDealExistence(dealRequestDto.getDealUniqueId());

        if (!requestFields.isEmpty()) throw new InvalidRequestFieldException("Invalid Request fields", requestFields);

        if (!dealExistence.isEmpty()) throw new InvalidRequestFieldException("Deal already exist", dealExistence);

        Deal deal = dealMapper.dealDtoToEntity(dealRequestDto);

        Deal deal1 = dealsRepository.save(deal);
        return dealMapper.dealToDealResponseDto(deal1);
    }

    @Override
    public DealResponseDto getDeal(String dealUniqueId) {
        Optional<Deal> deal = dealsRepository.findByDealUniqueId(dealUniqueId);
        if (deal.isEmpty())
            throw new ResourceNotFoundException("Deal with id " + dealUniqueId + " does not exist");
        return dealMapper.dealToDealResponseDto(deal.get());
    }
}
