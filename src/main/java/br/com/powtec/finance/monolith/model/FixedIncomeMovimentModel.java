package br.com.powtec.finance.monolith.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "tb_moviments_fixed_income")
@Getter
@Setter
public class FixedIncomeMovimentModel extends MovimentModel {

  LocalDate dueDate;

}
