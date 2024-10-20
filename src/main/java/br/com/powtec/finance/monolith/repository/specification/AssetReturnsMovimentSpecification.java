package br.com.powtec.finance.monolith.repository.specification;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import br.com.powtec.finance.monolith.model.AssetReturnsMovimentModel;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Component
public class AssetReturnsMovimentSpecification {

  public static Specification<AssetReturnsMovimentModel> getQuery(String parameters, Long assetId) {
    return new Specification<>() {

      @SuppressWarnings("null")
      @Override
      @Nullable
      public Predicate toPredicate(Root<AssetReturnsMovimentModel> root, CriteriaQuery<?> query,
          CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        if (parameters != null) {
          for (String param : parameters.split(",")) {
            String keyValue[] = param.split(":");
            if (keyValue[0].equalsIgnoreCase("stock")) {
              predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(keyValue[0]).get("id"), keyValue[1])));
            } else {
              predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(keyValue[0]), keyValue[1])));
            }
          }
        }
        predicates.add(criteriaBuilder.equal(root.get("stock").get("id"), assetId));
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
      }

    };
  }
}
