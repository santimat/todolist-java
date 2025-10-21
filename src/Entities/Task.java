package entities;

import java.time.LocalDate;

import enums.Priority;
import enums.Status;

public class Task {
  private Long id;
  private String title;
  private String description;
  private LocalDate start_date;
  private LocalDate end_date;
  private Status status;
  private Priority priority;

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

  public Long getId() {
    return this.id;
  }

  @Override
  public String toString() {
    return EscapeSecuence.ANSI_BOLD + this.id + EscapeSecuence.ANSI_RESET + " " + this.title + "\n"
        + this.description + "\n"
        + this.start_date.toString() + " - " + this.end_date.toString() + "\n"
        + "Status: " + this.status.toString() + " Priority: " + this.priority.toString();
  }

  public String toCSV() {
    return this.id + "," + this.title + "," + this.description + "," + this.start_date.toString() + ","
        + this.end_date.toString() + "," + this.status.toString() + "," + this.priority.toString();
  }

  public static Task fromCSV(String[] task) throws Exception {
    try {
      Long id = Long.parseLong(task[0]);
      String title = task[1];
      String description = task[2];
      LocalDate startDate = LocalDate.parse(task[3]);
      LocalDate endDate = LocalDate.parse(task[4]);
      Status status = Status.valueOf(task[5]);
      Priority priority = Priority.valueOf(task[6]);
      return new Task(id, title, description, startDate, endDate, status, priority);
    } catch (java.time.format.DateTimeParseException e) {
      throw new Exception("There has been an error with date format");
    } catch (NumberFormatException e) {
      throw new Exception("There has been an error with parse number");
    } catch (IllegalArgumentException e) {
      throw new Exception("Status or Priority value not valid");
    }
  }
}
