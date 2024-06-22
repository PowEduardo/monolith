package br.com.powtec.finance.monolith.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "tb_asset")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AssetModel {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String ticker;
  private Double value;
  @OneToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY, mappedBy = "stock")
  private List<StockMovimentModel> moviments;
  @OneToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY, mappedBy = "stock")
  private List<StockReturnsMovimentModel> returns;

}
