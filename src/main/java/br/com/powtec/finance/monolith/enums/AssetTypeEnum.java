package br.com.powtec.finance.monolith.enums;

import lombok.Getter;

@Getter
public enum AssetTypeEnum {

  STOCK("STOCK"),
  REIT("REIT");

  private String name;

  AssetTypeEnum(String name) {
    this.name = name;
  }
}
