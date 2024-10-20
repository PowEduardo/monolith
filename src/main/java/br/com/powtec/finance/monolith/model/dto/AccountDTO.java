package br.com.powtec.finance.monolith.model.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Builder
@Getter
@NoArgsConstructor
@Setter
public class AccountDTO {
  private Long id;
  private String bank;
  private String number;
  private String branch;
  private Boolean primary;
  private LocalDate createDate;
}
