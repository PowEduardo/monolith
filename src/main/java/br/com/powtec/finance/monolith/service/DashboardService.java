package br.com.powtec.finance.monolith.service;

import java.time.YearMonth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.powtec.finance.database.library.model.CreditCardModel;
import br.com.powtec.finance.database.library.model.CreditCardStatementModel;
import br.com.powtec.finance.database.library.model.dto.DashboardDTO;
import br.com.powtec.finance.database.library.repository.CreditCardRepository;
import br.com.powtec.finance.database.library.repository.CreditCardStatementRepository;
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

  /**
   * Get total paid amount across all credit cards
   * Queries database for all statements and calculates totals
   */
  public DashboardDTO getTotalPaidAmountAllCards() {
    log.info("Calculating total paid amount across all cards from database");
    
    try {
      // Get all statements from database
      var allStatements = statementRepository.findAll();
      
      Double totalPaid = 0.0;
      Double totalUnpaid = 0.0;
      
      for (CreditCardStatementModel statement : allStatements) {
        if (statement.getPaid() != null && statement.getPaid()) {
          totalPaid += statement.getValue() != null ? statement.getValue() : 0.0;
        } else {
          totalUnpaid += statement.getValue() != null ? statement.getValue() : 0.0;
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
          .totalPaid(0.0)
          .totalUnpaid(0.0)
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
            .totalPaid(0.0)
            .balance(0.0)
            .creditLimit(0.0)
            .availableCredit(0.0)
            .build();
      }
      
      // Get all statements for this card
      var allStatements = statementRepository.findAll();
      
      Double totalPaid = 0.0;
      Double balance =  20.0;//card.getBalance() != null ? card.getBalance() :
      Double creditLimit =  80.0;//card.getCreditLimit() != null ? card.getCreditLimit() :
      
      for (CreditCardStatementModel statement : allStatements) {
        // Check if statement belongs to this card
        if (statement.getCard() != null && statement.getCard().getId().equals(cardId)) {
          if (statement.getPaid() != null && statement.getPaid()) {
            totalPaid += statement.getValue() != null ? statement.getValue() : 0.0;
          }
        }
      }
      
      Double availableCredit = creditLimit - balance;
      
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
          .totalPaid(0.0)
          .balance(0.0)
          .creditLimit(0.0)
          .availableCredit(0.0)
          .build();
    }
  }

}
