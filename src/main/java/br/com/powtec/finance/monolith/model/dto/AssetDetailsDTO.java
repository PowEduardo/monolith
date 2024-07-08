package br.com.powtec.finance.monolith.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

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
public class AssetDetailsDTO {
  private Double amount;
  private Double average;
  private Double currentValue;
  private Double difference;
  private Double monthlyReturn;
  private Double lastReturn;
  private Double dy;
  private Double ady;
  private Integer targetAmount;
  private Double paidValue;
  private Double returns;
}
