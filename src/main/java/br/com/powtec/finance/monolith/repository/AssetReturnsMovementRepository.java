package br.com.powtec.finance.monolith.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import br.com.powtec.finance.monolith.model.movement.AssetReturnsMovementModel;

@Repository(value = "assetReturnRepository")
public interface AssetReturnsMovementRepository extends MovementRepository<AssetReturnsMovementModel> {
    List<AssetReturnsMovementModel> findAllByStockId(Long id);
}
