package br.com.powtec.finance.monolith.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.powtec.finance.database.library.base.BaseCrudRepository;
import br.com.powtec.finance.database.library.mapper.BaseChildCrudMapper;
import br.com.powtec.finance.database.library.model.dto.VehicleFuelDTO;
import br.com.powtec.finance.database.library.model.vehicle.VehicleFuelModel;
import br.com.powtec.finance.database.library.model.vehicle.VehicleModel;
import br.com.powtec.finance.database.library.repository.VehicleFuelRepository;
import br.com.powtec.finance.database.library.repository.VehicleRepository;
import br.com.powtec.finance.database.library.repository.specification.BaseCrudChildSpecification;

@Service
public class VehicleFuelServiceImpl extends BaseCrudChildServiceImpl<VehicleFuelModel, VehicleFuelDTO> {

    @Autowired
    VehicleRepository vehicleRepository;

    VehicleFuelServiceImpl(BaseCrudRepository<VehicleFuelModel> repository,
            BaseChildCrudMapper<VehicleFuelModel, VehicleFuelDTO> mapper,
            BaseCrudChildSpecification<VehicleFuelModel> specification) {
        super(repository, mapper, specification);
    }

    @Override
    public VehicleFuelDTO create(VehicleFuelDTO body, Long parentId) {
        VehicleModel vehicle = vehicleRepository.findById(parentId)
                .orElseThrow(() -> new IllegalArgumentException("Vehicle not found with id: " + parentId));
        VehicleFuelRepository repository = (VehicleFuelRepository) this.repository;
        VehicleFuelModel lastFuel = repository.findLastByDateAndVehicleId(body.getDate(), vehicle.getId());
        VehicleFuelModel nextFuel = repository.findNextByDateAndVehicleId(body.getDate(), vehicle.getId());
        body.setValue(body.getPrice().multiply(body.getLiters()));
        body.setIsFulfilled(false);
        BigDecimal consumption = (body.getMilage() != null && lastFuel != null)
                ? new BigDecimal(body.getMilage() - lastFuel.getMilage()).divide(body.getLiters(), 2, RoundingMode.HALF_UP)
                : BigDecimal.ZERO;
        body.setConsumption(consumption);
        if (nextFuel != null && nextFuel.getMilage() != null) {
            BigDecimal nextConsumption = new BigDecimal(nextFuel.getMilage() - body.getMilage()).divide(nextFuel.getLiters(), 2, RoundingMode.HALF_UP);
            nextFuel.setConsumption(nextConsumption);
            repository.save(nextFuel);
        }
        if (body.getMilage() != null && vehicle.getMilage() != null && vehicle.getMilage() < body.getMilage()) {
            vehicle.setMilage(body.getMilage());
            vehicleRepository.save(vehicle);
        }
        return super.create(body, parentId);
    }

    @Override
    public VehicleFuelDTO update(VehicleFuelDTO body, Long parentId, Long id) {
        VehicleFuelRepository repository = (VehicleFuelRepository) this.repository;
        VehicleFuelModel lastFuel = repository.findLastByDateAndVehicleId(body.getDate(), parentId);
        VehicleFuelModel nextFuel = repository.findNextByDateAndVehicleId(body.getDate(), parentId);
        body.setValue(body.getPrice().multiply(body.getLiters()));
        BigDecimal consumption = (body.getMilage() != null && lastFuel != null)
                ? new BigDecimal(body.getMilage() - lastFuel.getMilage()).divide(body.getLiters(), 2, RoundingMode.HALF_UP)
                : BigDecimal.ZERO;
        body.setConsumption(consumption);
        if (nextFuel != null && nextFuel.getMilage() != null) {
            BigDecimal nextConsumption = new BigDecimal(nextFuel.getMilage() - body.getMilage()).divide(nextFuel.getLiters(), 2, RoundingMode.HALF_UP);
            nextFuel.setConsumption(nextConsumption);
            repository.save(nextFuel);
        }
        return super.update(body, parentId, id);
    }

}
