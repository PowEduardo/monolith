package br.com.powtec.finance.monolith.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import br.com.powtec.finance.monolith.enums.AssetTypeEnum;
import br.com.powtec.finance.monolith.enums.IndexerEnum;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Builder
@Getter
@Setter
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
public class AssetDTO {

  private Long id;
  private String ticker;
  private Double value;
  @NotBlank
  private AssetTypeEnum type;
  private Double interestRate;
  private IndexerEnum indexer;
}
