package br.com.powtec.finance.monolith.enums;

import lombok.Getter;

@Getter
public enum AssetMovimentOperationEnum {

  BUY("BUY"),
  SELL("SELL"),
  SPLIT("SPLIT");

  private String name;

  AssetMovimentOperationEnum(String name) {
    this.name = name;
  }
}
