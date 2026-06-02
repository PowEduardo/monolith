package br.com.powtec.finance.monolith.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.powtec.finance.database.library.model.CreditCardInstallmentModel;
import br.com.powtec.finance.database.library.model.CreditCardStatementModel;
import br.com.powtec.finance.database.library.model.dto.ExternalTransactionDTO;
import br.com.powtec.finance.database.library.model.dto.InstallmentValidationDTO;
import br.com.powtec.finance.database.library.model.dto.StatementValidationResultDTO;
import br.com.powtec.finance.database.library.repository.CreditCardInstallmentRepository;
import br.com.powtec.finance.database.library.repository.specification.CreditCardInstallmentSpecification;

/**
 * Service to validate card statements against external bank data
 * Uses stateless, mathematical comparison (no persistence)
 */
@Service
public class CardStatementValidationService {

  @Autowired
  private CreditCardInstallmentRepository installmentRepository;

  @Autowired
  private CreditCardInstallmentSpecification installmentSpecification;

  /**
   * Validate a statement against external transactions
   * Performs installment-by-installment matching by amount value
   * 
   * @param statement The internal card statement
   * @param externalTransactions Filtered transactions from external source (CSV)
   * @return Validation result with match status per installment
   */
  public StatementValidationResultDTO validateStatement(
      CreditCardStatementModel statement,
      List<ExternalTransactionDTO> externalTransactions) {

    // Get installments for this statement
    List<CreditCardInstallmentModel> installments = installmentRepository
        .findAll(installmentSpecification.getQuery("statement:" + statement.getId()));

    // Calculate sums
    BigDecimal externalTotal = calculateExternalTotal(externalTransactions);
    BigDecimal statementTotal = statement.getValue() != null ? statement.getValue() : BigDecimal.ZERO;

    // Perform matching
    List<InstallmentValidationDTO> installmentValidations = new ArrayList<>();
    List<ExternalTransactionDTO> matchedTransactions = new ArrayList<>();
    Set<Integer> matchedExternalIndexes = new HashSet<>();

    if (installments != null && !installments.isEmpty()) {
      for (CreditCardInstallmentModel installment : installments) {
        Optional<Integer> matchIndex = findMatchingTransaction(installment.getValue(),
            externalTransactions, matchedExternalIndexes);

        InstallmentValidationDTO validation = InstallmentValidationDTO.builder()
            .installmentId(installment.getId())
            .installmentNumber(installment.getInstallment())
            .installmentValue(installment.getValue())
            .hasMatch(matchIndex.isPresent())
            .build();

        if (matchIndex.isPresent()) {
          ExternalTransactionDTO matched = externalTransactions.get(matchIndex.get());
          validation.setMatchedTransaction(matched);
          matchedExternalIndexes.add(matchIndex.get());
          matchedTransactions.add(matched);
        }

        installmentValidations.add(validation);
      }
    }

    // Find unmatched external transactions
    List<ExternalTransactionDTO> unmatchedExternal = new ArrayList<>();
    for (int i = 0; i < externalTransactions.size(); i++) {
      if (!matchedExternalIndexes.contains(i)) {
        unmatchedExternal.add(externalTransactions.get(i));
      }
    }

    // Determine overall validation status
    boolean isValid = unmatchedExternal.isEmpty() && installmentValidations.stream()
        .allMatch(v -> v.getHasMatch());
    boolean totalMatches = externalTotal.compareTo(statementTotal) == 0;

    return StatementValidationResultDTO.builder()
        .isValid(isValid)
        .externalTotal(externalTotal)
        .statementTotal(statementTotal)
        .totalMatches(totalMatches)
        .installmentValidations(installmentValidations)
        .unmatchedExternalTransactions(unmatchedExternal)
        .build();
  }

  /**
   * Find a matching external transaction by amount value
   * Uses first-match strategy: finds first unmatched transaction with same value
   * 
   * @param installmentValue The value to match
   * @param externalTransactions List of external transactions
   * @param alreadyMatched Set of indexes already matched (to avoid duplicates)
   * @return Optional index of matched transaction
   */
  private Optional<Integer> findMatchingTransaction(
      BigDecimal installmentValue,
      List<ExternalTransactionDTO> externalTransactions,
      Set<Integer> alreadyMatched) {

    for (int i = 0; i < externalTransactions.size(); i++) {
      if (!alreadyMatched.contains(i)) {
        ExternalTransactionDTO transaction = externalTransactions.get(i);
        if (transaction.getAmount().compareTo(installmentValue) == 0) {
          return Optional.of(i);
        }
      }
    }

    return Optional.empty();
  }

  /**
   * Calculate sum of external transactions
   */
  private BigDecimal calculateExternalTotal(List<ExternalTransactionDTO> transactions) {
    return transactions.stream()
        .map(ExternalTransactionDTO::getAmount)
        .reduce(BigDecimal.ZERO, BigDecimal::add);
  }
}
