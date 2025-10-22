package repository;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import entities.Task;

public class CSVManager {
  public static final String rootPath = "./src/archives/";
  public static final String tasksPath = rootPath + "tasks.csv";
  public static final String taskHeaders = "id,title,description,start_date,end_date,status,priority";

  public static void createFiles() throws IOException {
    File archivesFolder = new File(rootPath);
    if (!archivesFolder.exists()) {
      if (archivesFolder.mkdirs()) {
        System.out.println("The 'Archives' folder was created");
      } else {
        throw new IOException("The 'Archives' couldn't be created.");
      }
    }

    // Check if not exist tasks.csv and users.csv then create them
    File tasksFile = new File(tasksPath);
    if (!tasksFile.exists()) {
      // Create tasks.csv file
      try (BufferedWriter tasksWriter = new BufferedWriter(new FileWriter(tasksPath))) {
        tasksWriter.write(taskHeaders);
        tasksWriter.newLine();
      } catch (IOException e) {
        throw new IOException("There was an error creating tasks.csv." + e.getMessage());
      }
    }
  }

  public static Map<Long, Task> readFile() throws IOException {
    Map<Long, Task> resultMap = new HashMap<>();
    try (BufferedReader reader = new BufferedReader(new FileReader(tasksPath))) {

      // Skip headers
      reader.readLine();
    } catch (IOException e) {
      throw new IOException("There was an error reading tasks. " + e.getMessage());
    }

    return resultMap;
  }

  public static void writeFile(String content) throws IOException {
    try (BufferedWriter taskWriter = new BufferedWriter(new FileWriter(tasksPath, true))) {
      taskWriter.write(content);
      taskWriter.newLine();
    } catch (IOException e) {
      throw new IOException("There was an error writing on tasks.csv file. " + e.getMessage());
    }
  }

  public static void resetFile(String content) throws IOException {
    try (BufferedWriter taskWriter = new BufferedWriter(new FileWriter(tasksPath))) {
      taskWriter.write(taskHeaders);
      taskWriter.newLine();
    } catch (IOException e) {
      throw new IOException("There was an error rewriting tasks.csv file. " + e.getMessage());
    }
  }
}
