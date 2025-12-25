package br.com.powtec.finance.monolith.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.powtec.finance.database.library.model.dto.DashboardDTO;
import br.com.powtec.finance.monolith.service.DashboardService;
import lombok.extern.log4j.Log4j2;

/**
 * REST Controller for dashboard and chart endpoints.
 * Provides aggregated financial data for visualization.
 */
@RestController
@Log4j2
@RequestMapping("/api/v1/dashboard")
public class DashboardController {

  @Autowired
  private DashboardService dashboardService;

  /**
   * GET /api/v1/dashboard/cards/paid-amount
   * Get total paid amount across all credit cards
   * Used for: Total paid value in chart
   */
  @GetMapping("/cards/paid-amount")
  public ResponseEntity<DashboardDTO> getTotalPaidAmount() {
    log.info("Getting total paid amount across all cards");
    DashboardDTO dashboard = dashboardService.getTotalPaidAmountAllCards();
    return ResponseEntity.ok().body(dashboard);
  }

  /**
   * GET /api/v1/dashboard/cards/{cardId}/paid-amount
   * Get total paid amount for a specific credit card
   * Used for: Card-specific paid value in chart
   */
  @GetMapping("/cards/{cardId}/paid-amount")
  public ResponseEntity<DashboardDTO> getCardPaidAmount(@PathVariable Long cardId) {
    log.info("Getting paid amount for card id: {}", cardId);
    DashboardDTO dashboard = dashboardService.getCardPaidAmount(cardId);
    return ResponseEntity.ok().body(dashboard);
  }

}
