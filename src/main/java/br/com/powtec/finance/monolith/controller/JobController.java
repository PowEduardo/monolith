package br.com.powtec.finance.monolith.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
public class JobController {
  @Autowired
  private JdbcTemplate jdbcTemplate;

  // API to list all distinct job names
  @GetMapping("/jobs")
  public List<String> getAllJobs() {
    String query = "SELECT DISTINCT JOB_NAME FROM BATCH_JOB_INSTANCE ORDER BY JOB_NAME";
    return jdbcTemplate.queryForList(query, String.class);
  }

  // API to list executions for a specific job
  @GetMapping("/jobs/{jobName}/executions")
  public List<Map<String, Object>> getJobExecutions(@PathVariable String jobName) {
    String query = """
            SELECT
                job_execution.JOB_EXECUTION_ID,
                job_execution.STATUS,
                job_execution.START_TIME,
                job_execution.END_TIME
            FROM
                BATCH_JOB_EXECUTION job_execution
            JOIN
                BATCH_JOB_INSTANCE job_instance
            ON
                job_execution.JOB_INSTANCE_ID = job_instance.JOB_INSTANCE_ID
            WHERE
                job_instance.JOB_NAME = ?
            ORDER BY
                job_execution.START_TIME DESC
        """;
    return jdbcTemplate.queryForList(query, jobName);
  }

  @GetMapping("/executions/{executionId}/steps")
  public List<Map<String, Object>> getStepExecutions(@PathVariable Long executionId) {
    String query = """
            SELECT
                STEP_NAME,
                STATUS,
                START_TIME,
                END_TIME,
                READ_COUNT,
                WRITE_COUNT,
                PROCESS_SKIP_COUNT AS SKIP_COUNT,
                EXIT_MESSAGE
            FROM
                BATCH_STEP_EXECUTION
            WHERE
                JOB_EXECUTION_ID = ?
        """;
    return jdbcTemplate.queryForList(query, executionId);
  }
}
