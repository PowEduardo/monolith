package br.com.powtec.finance.monolith.enums;

import lombok.Getter;

@Getter
public enum MovimentTypeEnum {
  ASSET_RETURN("ASSET_RETURN"),
  ASSET_MOVIMENT("ASSET_MOVIMENT");

  private String name;

  MovimentTypeEnum(String name) {
    this.name = name;
  }
}
