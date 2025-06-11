package br.com.powtec.finance.monolith.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.powtec.finance.database.library.base.BaseCrudRepository;
import br.com.powtec.finance.database.library.mapper.BaseChildCrudMapper;
import br.com.powtec.finance.database.library.model.dto.VehiclePartDTO;
import br.com.powtec.finance.database.library.model.vehicle.VehiclePartModel;
import br.com.powtec.finance.database.library.repository.specification.BaseCrudChildSpecification;

@Service
public class VehiclePartServiceImpl extends BaseCrudChildServiceImpl<VehiclePartModel, VehiclePartDTO> {

    public VehiclePartServiceImpl(
        @Autowired BaseCrudRepository<VehiclePartModel> repository,
        @Autowired BaseChildCrudMapper<VehiclePartModel, VehiclePartDTO> mapper,
        @Autowired BaseCrudChildSpecification<VehiclePartModel> specification) {
        super(repository, mapper, specification);
    }

}
