package br.com.powtec.finance.monolith.model.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import br.com.powtec.finance.monolith.enums.MovimentTypeEnum;
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
public class MovimentDTO {

  Long id;
  LocalDate date;
  Double value;
  MovimentTypeEnum type;
}
