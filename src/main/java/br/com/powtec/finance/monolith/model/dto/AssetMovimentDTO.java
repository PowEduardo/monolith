package br.com.powtec.finance.monolith.model.dto;

import br.com.powtec.finance.monolith.enums.AssetMovimentOperationEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class AssetMovimentDTO extends MovimentDTO {

  AssetDTO asset;
  Integer amount;
  AssetMovimentOperationEnum operation;

}
