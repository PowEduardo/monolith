package br.com.powtec.finance.monolith.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "tb_movements_fixed_income")
@Getter
@Setter
public class FixedIncomeMovementModel extends MovementModel {

  LocalDate dueDate;

}
