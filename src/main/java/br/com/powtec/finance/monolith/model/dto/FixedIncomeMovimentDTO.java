package br.com.powtec.finance.monolith.model.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class FixedIncomeMovimentDTO extends MovimentDTO {

  LocalDate dueDate;

}
