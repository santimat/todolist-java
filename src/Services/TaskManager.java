package services;

import entities.Task;
import repository.CSVManager;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TaskManager {

  private static Map<Long, Task> tasks = new HashMap<>();

  private static void loadFileTasks() throws IOException {
    try {
      tasks.putAll(CSVManager.readFile());
    } catch (IOException e) {
      throw new IOException(e.getMessage());
    }
  }

  private static void addTask(Map<String, String> newTask) throws IOException {
    try {
      Task task = Task.fromCSV();
    } catch (IOException e) {
      throw new IOException(e.getMessage());
    }
  }
}
