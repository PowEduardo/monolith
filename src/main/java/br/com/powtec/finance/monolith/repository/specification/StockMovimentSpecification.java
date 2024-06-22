package br.com.powtec.finance.monolith.repository.specification;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import br.com.powtec.finance.monolith.model.StockMovimentModel;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Component
public class StockMovimentSpecification {

  public static Specification<StockMovimentModel> getQuery(String parameters) {
    return new Specification<>() {

      @SuppressWarnings("null")
      @Override
      @Nullable
      public Predicate toPredicate(Root<StockMovimentModel> root, CriteriaQuery<?> query,
          CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        for (String param : parameters.split(",")) {
          String keyValue[] = param.split(":");
          predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(keyValue[0]), keyValue[1])));
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
      }

    };
  }

}
