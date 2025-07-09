package br.com.powtec.finance.monolith.service.impl;

import org.springframework.stereotype.Service;

import br.com.powtec.finance.database.library.base.BaseCrudRepository;
import br.com.powtec.finance.database.library.mapper.BaseChildCrudMapper;
import br.com.powtec.finance.database.library.model.dto.VehicleFuelDTO;
import br.com.powtec.finance.database.library.model.vehicle.VehicleFuelModel;
import br.com.powtec.finance.database.library.repository.specification.BaseCrudChildSpecification;

@Service
public class VehicleFuelServiceImpl extends BaseCrudChildServiceImpl<VehicleFuelModel, VehicleFuelDTO> {
    VehicleFuelServiceImpl(BaseCrudRepository<VehicleFuelModel> repository,
            BaseChildCrudMapper<VehicleFuelModel, VehicleFuelDTO> mapper,
            BaseCrudChildSpecification<VehicleFuelModel> specification) {
        super(repository, mapper, specification);
    }

}
