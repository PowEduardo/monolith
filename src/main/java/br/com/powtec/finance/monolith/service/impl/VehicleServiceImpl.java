package br.com.powtec.finance.monolith.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.powtec.finance.database.library.base.BaseCrudRepository;
import br.com.powtec.finance.database.library.mapper.BaseCrudMapper;
import br.com.powtec.finance.database.library.model.dto.VehicleDTO;
import br.com.powtec.finance.database.library.model.vehicle.VehicleModel;
import br.com.powtec.finance.database.library.repository.specification.BaseCrudSpecification;

@Service
public class VehicleServiceImpl extends BaseCrudServiceImpl<VehicleModel, VehicleDTO> {

    // Constructor
    public VehicleServiceImpl(
        @Autowired BaseCrudRepository<VehicleModel> repository,
        @Autowired BaseCrudMapper<VehicleModel, VehicleDTO> mapper,
        @Autowired BaseCrudSpecification<VehicleModel> specification) {
        this.repository = repository;
        this.mapper = mapper;
        this.specification = specification;
    }

    // Additional methods specific to Vehicle can be added here

}
