package br.com.powtec.finance.monolith.service.impl;

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
        body.setValue(body.getPrice() * body.getLiters());
        body.setIsFulfilled(false);
        double consumption = (body.getMilage() != null && lastFuel != null)
                ? (body.getMilage() - lastFuel.getMilage()) / body.getLiters()
                : 0.0;
        body.setConsumption(Math.round(consumption * 100.0) / 100.0);
        if (nextFuel != null && nextFuel.getMilage() != null) {
            double nextConsumption = (nextFuel.getMilage() - body.getMilage()) / nextFuel.getLiters();
            nextFuel.setConsumption(Math.round(nextConsumption * 100.0) / 100.0);
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
        body.setValue(Math.round(body.getPrice() * body.getLiters())*1.0);
        double consumption = (body.getMilage() != null && lastFuel != null)
                ? (body.getMilage() - lastFuel.getMilage()) / body.getLiters()
                : 0.0;
        body.setConsumption(Math.round(consumption * 100.0) / 100.0);
        if (nextFuel != null && nextFuel.getMilage() != null) {
            double nextConsumption = (nextFuel.getMilage() - body.getMilage()) / nextFuel.getLiters();
            nextFuel.setConsumption(Math.round(nextConsumption * 100.0) / 100.0);
            repository.save(nextFuel);
        }
        return super.update(body, parentId, id);
    }

}
