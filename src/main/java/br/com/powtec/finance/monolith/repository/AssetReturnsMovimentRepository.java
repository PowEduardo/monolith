package br.com.powtec.finance.monolith.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import br.com.powtec.finance.monolith.model.AssetReturnsMovimentModel;

@Repository(value = "assetReturnRepository")
public interface AssetReturnsMovimentRepository extends MovimentRepository<AssetReturnsMovimentModel> {
    List<AssetReturnsMovimentModel> findAllByStockId(Long id);
}
