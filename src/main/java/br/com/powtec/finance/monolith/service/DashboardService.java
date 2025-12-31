package br.com.powtec.finance.monolith.service;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.powtec.finance.database.library.model.AccountModel;
import br.com.powtec.finance.database.library.model.CreditCardModel;
import br.com.powtec.finance.database.library.model.CreditCardStatementModel;
import br.com.powtec.finance.database.library.model.MovementModel;
import br.com.powtec.finance.database.library.model.dao.AccountDashboardDetails;
import br.com.powtec.finance.database.library.model.dao.DashboardBarChartDetails;
import br.com.powtec.finance.database.library.model.dto.AccountDTO;
import br.com.powtec.finance.database.library.model.dto.dashboard.AccountDashboardDetailsDTO;
import br.com.powtec.finance.database.library.model.dto.dashboard.DashboardBarChartDetailsDTO;
import br.com.powtec.finance.database.library.model.dto.dashboard.DashboardDTO;
import br.com.powtec.finance.database.library.repository.AccountRepository;
import br.com.powtec.finance.database.library.repository.CreditCardRepository;
import br.com.powtec.finance.database.library.repository.CreditCardStatementRepository;
import br.com.powtec.finance.database.library.repository.MovementRepository;
import lombok.extern.log4j.Log4j2;

/**
 * Service for dashboard and chart data
 * Provides aggregated financial information for visualization
 */
@Service
@Log4j2
public class DashboardService {

  @Autowired
  private CreditCardRepository cardRepository;

  @Autowired
  private CreditCardStatementRepository statementRepository;

  @Autowired
  private MovementRepository<MovementModel> movementRepository;

  @Autowired
  private AccountRepository accountRepository;

  /**
   * Get total paid amount across all credit cards
   * Queries database for all statements and calculates totals
   */
  public DashboardDTO getTotalPaidAmountAllCards() {
    log.info("Calculating total paid amount across all cards from database");

    try {
      // Get all statements from database
      var allStatements = statementRepository.findAll();

      BigDecimal totalPaid = BigDecimal.ZERO;
      BigDecimal totalUnpaid = BigDecimal.ZERO;

      for (CreditCardStatementModel statement : allStatements) {
        if (statement.getPaid() != null && statement.getPaid()) {
          totalPaid = totalPaid.add(statement.getValue() != null ? statement.getValue() : BigDecimal.ZERO);
        } else {
          totalUnpaid = totalUnpaid.add(statement.getValue() != null ? statement.getValue() : BigDecimal.ZERO);
        }
      }

      log.info("Total paid: {}, Total unpaid: {}", totalPaid, totalUnpaid);

      return DashboardDTO.builder()
          .totalPaid(totalPaid)
          .totalUnpaid(totalUnpaid)
          .details(statementRepository.nextAndPreviousStatements(YearMonth.now().toString()))
          .build();
    } catch (Exception e) {
      log.error("Error calculating total paid amount", e);
      // Return empty data on error
      return DashboardDTO.builder()
          .totalPaid(BigDecimal.ZERO)
          .totalUnpaid(BigDecimal.ZERO)
          .build();
    }
  }

  /**
   * Get paid amount and other info for a specific card
   * Queries database for card and its statements
   */
  public DashboardDTO getCardPaidAmount(Long cardId) {
    log.info("Calculating paid amount for card: {}", cardId);

    try {
      // Get card from database
      CreditCardModel card = cardRepository.findById(cardId).orElse(null);

      if (card == null) {
        log.warn("Card not found with id: {}", cardId);
        return DashboardDTO.builder()
            .totalPaid(BigDecimal.ZERO)
            .balance(BigDecimal.ZERO)
            .creditLimit(BigDecimal.ZERO)
            .availableCredit(BigDecimal.ZERO)
            .build();
      }

      // Get all statements for this card
      var allStatements = statementRepository.findAll();

      BigDecimal totalPaid = BigDecimal.valueOf(0.0);
      BigDecimal balance = BigDecimal.valueOf(0.0); // card.getBalance() != null ? card.getBalance() :
      BigDecimal creditLimit = BigDecimal.valueOf(0.0); // card.getCreditLimit() != null ? card.getCreditLimit() :

      for (CreditCardStatementModel statement : allStatements) {
        // Check if statement belongs to this card
        if (statement.getCard() != null && statement.getCard().getId().equals(cardId)) {
          if (statement.getPaid() != null && statement.getPaid()) {
            totalPaid = totalPaid.add(statement.getValue() != null ? statement.getValue() : BigDecimal.ZERO);
          }
        }
      }

      BigDecimal availableCredit = creditLimit.subtract(balance);

      log.info("Card {} - Total paid: {}, Balance: {}, Limit: {}, Available: {}",
          cardId, totalPaid, balance, creditLimit, availableCredit);

      return DashboardDTO.builder()
          .totalPaid(totalPaid)
          .balance(balance)
          .creditLimit(creditLimit)
          .availableCredit(availableCredit)
          .details(statementRepository.nextAndPreviousStatementsForSpecifycCard(YearMonth.now().toString(), cardId))
          .build();
    } catch (Exception e) {
      log.error("Error calculating paid amount for card: {}", cardId, e);
      // Return empty data on error
      return DashboardDTO.builder()
          .totalPaid(BigDecimal.ZERO)
          .balance(BigDecimal.ZERO)
          .creditLimit(BigDecimal.ZERO)
          .availableCredit(BigDecimal.ZERO)
          .build();
    }
  }

  /**
   * Get account dashboard data with monthly aggregation
   * Returns income vs expense by month for a specific account
   */
  public DashboardDTO getAccountDashboardData(Long accountId, YearMonth startRange, YearMonth endRange) {
    log.info("Calculating dashboard data for account: {}", accountId);
    AccountModel account = accountRepository.findById(accountId).orElse(null);
    if (account == null) {
      log.warn("Account not found with id: {}", accountId);
      return DashboardDTO.builder()
          .balance(BigDecimal.ZERO)
          .build();
    }

    List<DashboardBarChartDetails> details = movementRepository.getAggregatedIncomeExpenseByMonth(
        accountId, startRange.toString(), endRange.toString());
    AccountDashboardDetails accountDetails = movementRepository.getBalanceForAggregatedIncomeExpenseByMonth(
        accountId, startRange.toString(), endRange.toString()).stream().findFirst().orElse(null);
    return DashboardDTO.builder()
        .barChartDetails(details.stream().map(detail -> DashboardBarChartDetailsDTO.builder()
            .reference(detail.getReference())
            .firstValue(detail.getFirstValue())
            .secondValue(detail.getSecondValue())
            .build()).toList())
        .accountDetails(AccountDashboardDetailsDTO.builder()
            .totalIncome(accountDetails.getIncome())
            .totalExpense(accountDetails.getExpense())
            .balance(accountDetails.getBalance())
            .totalPaid(accountDetails.getTotalPaid())
            .totalUnpaid(accountDetails.getTotalUnpaid())
            .account(AccountDTO.builder()
                .bank(account.getBank())
                .build())
            .build())
        .build();
    // try {
    // // Get all movements for this account
    // var allMovements = movementRepository.findAll();

    // BigDecimal totalIncome = 0.0;
    // BigDecimal totalExpense = 0.0;

    // for (var movement : allMovements) {
    // // Check if movement belongs to this account
    // if (movement.getAccount() != null &&
    // movement.getAccount().getId().equals(accountId)) {
    // BigDecimal value = movement.getValue() != null ? movement.getValue() : 0.0;

    // if (value > 0) {
    // totalIncome += value;
    // } else {
    // totalExpense += Math.abs(value);
    // }
    // }
    // }

    // log.info("Account {} - Total income: {}, Total expense: {}",
    // accountId, totalIncome, totalExpense);

    // return DashboardDTO.builder()
    // .totalIncome(totalIncome)
    // .totalExpense(totalExpense)
    // .balance(totalIncome - totalExpense)
    // .details(movementRepository.nextAndPreviousMovementsByMonth(YearMonth.now().toString(),
    // accountId))
    // .build();
    // } catch (Exception e) {
    // log.error("Error calculating dashboard data for account: {}", accountId, e);
    // // Return empty data on error
    // return DashboardDTO.builder()
    // .balance(0.0)
    // .build();
    // }
  }

}
