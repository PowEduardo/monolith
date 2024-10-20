package br.com.powtec.finance.monolith.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import br.com.powtec.finance.monolith.model.movement.AssetMovementModel;

@Repository("assetMovementRepository")
public interface AssetMovimentRepository
        extends MovementRepository<AssetMovementModel> {
    List<AssetMovementModel> findAllByAssetId(Long id);
}
