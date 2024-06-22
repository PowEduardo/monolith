package br.com.powtec.finance.monolith.enums;

import lombok.Getter;

@Getter
public enum StockReturnsOperationEnum {

  DIVIDEND("DIVIDEND"),
  JCP("JCP"),
  REFUND("REFUND");

  private String name;

  StockReturnsOperationEnum(String name) {
    this.name = name;
  }
}
