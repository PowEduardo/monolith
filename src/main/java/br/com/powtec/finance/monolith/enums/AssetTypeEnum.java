package br.com.powtec.finance.monolith.enums;

import lombok.Getter;

@Getter
public enum AssetTypeEnum {

  STOCK("STOCK"),
  REIT("REIT"),
  DIRECT_TREASURE("DIRECT_TREASURE");

  private String name;

  AssetTypeEnum(String name) {
    this.name = name;
  }
}
