package repository;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import entities.Task;
import entities.User;

public class CSVManager {
  public static final String rootPath = "./src/archives/";
  public static final String tasksPath = rootPath + "tasks.csv";
  public static final String usersPath = rootPath + "users.csv";
  public static final String taskHeaders = "id,title,description,start_date,end_date,status,priority";
  public static final String userHeaders = "username,password,salt";

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
    File usersFile = new File(usersPath);
    if (!usersFile.exists()) {
      // Create users.csv file
      try (BufferedWriter usersWriter = new BufferedWriter(new FileWriter(usersPath))) {
        usersWriter.write(userHeaders);
        usersWriter.newLine();
      } catch (IOException e) {
        throw new IOException("There was an error creating users.csv. " + e.getMessage());
      }
    }
  }

  public static <K, V> Map<K, V> readFile(String path) {
    Map<K, V> resultMap = new HashMap<>();
    String fileName = path + ".csv";
    Function<String[], V> csvConverter;

    if ("tasks".equalsIgnoreCase(path)) {
      csvConverter = Task::fromCSV;
    } else if ("users".equalsIgnoreCase(path)) {
    }
  }

  public static void writeFile(String path, String content) throws IOException {
    if ("tasks".equalsIgnoreCase(path)) {
      try (BufferedWriter taskWriter = new BufferedWriter(new FileWriter(tasksPath, true))) {
        taskWriter.write(content);
        taskWriter.newLine();
      } catch (IOException e) {
        throw new IOException("There was an error writing on tasks.csv file. " + e.getMessage());
      }
    } else if ("users".equalsIgnoreCase(path)) {
      try (BufferedWriter usersWriter = new BufferedWriter(new FileWriter(usersPath, true))) {
        usersWriter.write(content);
        usersWriter.newLine();
      } catch (IOException e) {
        throw new IOException("There was an error writing on users.csv file. " + e.getMessage());
      }
    }
  }

  public static void resetFile(String path, String content) throws IOException {
    if ("tasks".equalsIgnoreCase(path)) {
      try (BufferedWriter taskWriter = new BufferedWriter(new FileWriter(tasksPath))) {
        taskWriter.write(taskHeaders);
        taskWriter.newLine();
      } catch (IOException e) {
        throw new IOException("There was an error rewriting tasks.csv file. " + e.getMessage());
      }
    } else if ("users".equalsIgnoreCase(path)) {
      try (BufferedWriter usersWriter = new BufferedWriter(new FileWriter(usersPath))) {
        usersWriter.write(userHeaders);
        usersWriter.newLine();
      } catch (IOException e) {
        throw new IOException("There was an error rewriting users.csv file. " + e.getMessage());
      }
    }
  }
}
