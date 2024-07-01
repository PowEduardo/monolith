package br.com.powtec.finance.monolith.model.dto;

import java.time.LocalDate;

import br.com.powtec.finance.monolith.enums.AssetReturnsOperationEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class AssetReturnsMovimentDTO extends MovimentDTO {

  private Integer amount;
  private AssetDTO asset;
  private AssetReturnsOperationEnum operation;
  private Double unitValue;
  private LocalDate exDividendDate;

}
