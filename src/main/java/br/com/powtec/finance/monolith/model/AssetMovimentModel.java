package br.com.powtec.finance.monolith.model;

import br.com.powtec.finance.monolith.enums.AssetMovimentOperationEnum;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "tb_moviments_asset")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AssetMovimentModel extends MovimentModel {

  private Integer amount;
  @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
  @JoinColumn(name = "asset_id")
  private AssetModel asset;
  @Enumerated(EnumType.STRING)
  private AssetMovimentOperationEnum operation;
  private Double unitValue;

}
