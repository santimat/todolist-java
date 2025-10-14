package Repository;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import Entities.Task;
import Entities.User;

public class CSVManager {
  public static final String tasksPath = "./src/Archives/tasks.csv";
  public static final String taskHeaders = "id,title,description,start_date,end_date,status,priority";
  public static final String usersPath = "./src/Archives/users.csv";
  public static final String userHeaders = "username,password,salt";

  public static void createFiles() throws IOException {
    File archivesFolder = new File("./src/Archives");
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

  public static <K, V> Map<K, V> readFile(String path) throws IOException {
    // Create an emptyResult to return it and avoid errors
    Map<K, V> emptyResult = (Map<K, V>) new HashMap<>();

    if ("tasks".equalsIgnoreCase(path)) {
      Map<Long, Task> tasks = new HashMap<>();
      try (BufferedReader taskReader = new BufferedReader(new FileReader(tasksPath))) {
        // Skip headers
        taskReader.readLine();
        String line;
        int numberLine = 1;
        while ((line = taskReader.readLine()) != null) {
          numberLine++;
          String[] taskCSV = line.split(",");
          try {
            Task task = Task.fromCSV(taskCSV);
            tasks.put(task.getId(), task);
          } catch (Exception e) {
            System.out.println(e.getMessage() + "\ntasks.csv line number: " + numberLine);
          }
        }
        return (Map<K, V>) tasks;
      } catch (IOException e) {
        throw new IOException("There was an error reading tasks. " + e.getMessage());
      }
    } else if ("users".equalsIgnoreCase(path)) {
      Map<String, User> users = new HashMap<>();
      try (BufferedReader userReader = new BufferedReader(new FileReader(usersPath))) {
        userReader.readLine();
        String line;
        int numberLine = 1;
        while ((line = userReader.readLine()) != null) {
          String[] userCSV = line.split(",");
          try {
            User user = User.fromCSV(userCSV);
            users.put(user.getUsername(), user);
          } catch (Exception e) {
            System.out.println(e.getMessage() + "\nusers.csv line number: " + numberLine);
          }
        }
        return (Map<K, V>) users;
      } catch (IOException e) {
        throw new IOException("There was an error reading users. " + e.getMessage());
      }
    }
    return emptyResult;
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
