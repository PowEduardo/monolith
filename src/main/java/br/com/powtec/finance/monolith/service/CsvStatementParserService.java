package br.com.powtec.finance.monolith.service;

import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.com.powtec.finance.database.library.model.dto.ExternalTransactionDTO;

/**
 * Service to parse external bank CSV files (e.g., Nubank)
 */
@Service
public class CsvStatementParserService {

  private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

  /**
   * Parse Nubank CSV format
   * Expected format: date,title,amount
   * 
   * @param file CSV file uploaded by user
   * @return List of ExternalTransactionDTO parsed from CSV
   * @throws RuntimeException if CSV format is invalid
   */
  public List<ExternalTransactionDTO> parseNubankCsv(MultipartFile file) {
    List<ExternalTransactionDTO> transactions = new ArrayList<>();

    try (InputStreamReader reader = new InputStreamReader(file.getInputStream());
        CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader())) {

      for (CSVRecord record : csvParser) {
        try {
          ExternalTransactionDTO transaction = ExternalTransactionDTO.builder()
              .date(parseDate(record.get("date")))
              .title(record.get("title"))
              .amount(new BigDecimal(record.get("amount")))
              .included(true)  // Default: include all transactions; user can filter in UI
              .build();
          transactions.add(transaction);
        } catch (Exception e) {
          throw new RuntimeException("Error parsing CSV record: " + e.getMessage(), e);
        }
      }
    } catch (IOException e) {
      throw new RuntimeException("Error reading CSV file: " + e.getMessage(), e);
    }

    if (transactions.isEmpty()) {
      throw new RuntimeException("CSV file is empty or contains no valid records");
    }

    return transactions;
  }

  /**
   * Parse date string in YYYY-MM-DD format
   */
  private LocalDate parseDate(String dateString) {
    return LocalDate.parse(dateString, DATE_FORMATTER);
  }
}
