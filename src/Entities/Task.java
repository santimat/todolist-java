package Entities;

import java.time.LocalDate;

import Enums.Priority;
import Enums.Status;

public class Task {
  private Long id;
  private String title;
  private String description;
  private LocalDate start_date;
  private LocalDate end_date;
  private Status status;
  private Priority priority;

  public Task(String title, String description, LocalDate start_date, LocalDate end_date, Status status,
      Priority priority) {
    this.title = title;
    this.description = description;
    this.start_date = start_date;
    this.end_date = end_date;
    this.status = status;
    this.priority = priority;
  }

  public Task(Long id, String title, String description, LocalDate start_date, LocalDate end_date, Status status,
      Priority priority) {
    this.id = id;
    this.title = title;
    this.description = description;
    this.start_date = start_date;
    this.end_date = end_date;
    this.status = status;
    this.priority = priority;
  }

  @Override
  public String toString() {
    return EscapeSecuence.ANSI_BOLD + this.id + EscapeSecuence.ANSI_RESET + " " + this.title + "\n"
        + this.description + "\n"
        + this.start_date.toString() + " - " + this.end_date.toString() + "\n"
        + "Status: " + this.status.toString() + " Priority: " + this.priority.toString();
  }
}
