package br.com.powtec.finance.monolith.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PageBuilder {

  public static Pageable pageable(int pageNumber, int pageSize, String sort) {
    return PageRequest.of(pageNumber, pageSize, sort(sort));
  }

  private static Sort sort(String option) {
    if (option == null || option.isEmpty()) {
        return Sort.by(Sort.Direction.DESC, "id"); // Default sort
    }

    // Split the option string by commas to support multiple order clauses
    String[] options = option.split(",");
    Sort sort = Sort.unsorted();

    for (String field : options) {
        if (field.startsWith("-")) {
            // Descending order for fields with a leading '-'
            sort = sort.and(Sort.by(Sort.Direction.DESC, field.substring(1)));
        } else {
            // Ascending order for other fields
            sort = sort.and(Sort.by(Sort.Direction.ASC, field));
        }
    }

    return sort;
}
}
