package br.com.powtec.finance.monolith.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.powtec.finance.database.library.base.BaseCrudRepository;
import br.com.powtec.finance.database.library.mapper.BaseCrudMapper;
import br.com.powtec.finance.database.library.model.dto.VehiclePartDTO;
import br.com.powtec.finance.database.library.model.vehicle.VehiclePartModel;
import br.com.powtec.finance.database.library.repository.specification.BaseCrudSpecification;

@Service
public class VehiclePartServiceImpl extends BaseCrudServiceImpl<VehiclePartModel, VehiclePartDTO> {

    public VehiclePartServiceImpl(
        @Autowired BaseCrudRepository<VehiclePartModel> repository,
        @Autowired BaseCrudMapper<VehiclePartModel, VehiclePartDTO> mapper,
        @Autowired BaseCrudSpecification<VehiclePartModel> specification) {
        this.repository = repository;
        this.mapper = mapper;
        this.specification = specification;
    }

}
