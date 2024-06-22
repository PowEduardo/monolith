package br.com.powtec.finance.monolith.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PageBuilder {

  public static Pageable pageable(int pageNumber, int pageSize, String sort) {
    return PageRequest.of(pageNumber, pageSize, sort(sort));
  }

  private static Sort sort(String option) {
    if (option == null) {
      return Sort.by(Sort.Direction.DESC, "id");
    } else if (option.contains("-")) {
      option = option.replace("-", option);
      return Sort.by(Sort.Direction.DESC, option);
    } else {
      return Sort.by(Sort.Direction.ASC, option);

    }
  }
}
