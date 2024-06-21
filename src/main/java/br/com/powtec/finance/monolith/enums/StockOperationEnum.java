package br.com.powtec.finance.monolith.enums;

import lombok.Getter;

@Getter
public enum StockOperationEnum {

  BUY("BUY"),
  SELL("SELL"),
  SPLIT("SPLIT");

  private String name;

  StockOperationEnum(String name) {
    this.name = name;
  }
}
