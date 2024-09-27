package br.com.powtec.finance.monolith.repository.specification;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import br.com.powtec.finance.monolith.model.AssetReturnsMovementModel;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Component
public class AssetReturnsMovementSpecification {

  public static Specification<AssetReturnsMovementModel> getQuery(String parameters, Long assetId) {
    return new Specification<>() {

      @SuppressWarnings("null")
      @Override
      @Nullable
      public Predicate toPredicate(Root<AssetReturnsMovementModel> root, CriteriaQuery<?> query,
          CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        if (parameters != null) {
          for (String param : parameters.split(",")) {
            String keyValue[] = param.split(":");
            if (keyValue[0].equalsIgnoreCase("stock")) {
              predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(keyValue[0]).get("id"), keyValue[1])));
            } else if (keyValue[0].equals("assetType")) {
              predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("stock").get("type"), keyValue[1])));
            } else {
              predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(keyValue[0]), keyValue[1])));
            }
          }
        }
        if (assetId != 0) {
          predicates.add(criteriaBuilder.equal(root.get("stock").get("id"), assetId));
        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
      }

    };
  }
}
