package br.com.powtec.finance.monolith.model;

import java.time.LocalDate;

import br.com.powtec.finance.monolith.enums.AssetReturnsOperationEnum;
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

@Entity(name = "tb_moviments_asset_return")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AssetReturnsMovimentModel extends MovimentModel {

  private Integer amount;
  @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
  @JoinColumn(name = "asset_id")
  private AssetModel stock;
  @Enumerated(EnumType.STRING)
  private AssetReturnsOperationEnum operation;
  private Double unitValue;
  private LocalDate exDividendDate;
}
