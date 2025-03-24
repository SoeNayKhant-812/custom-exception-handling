package exception_handling;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Custom_exception_example1 {

	public static void writeToFile(String filename, String content) throws IOException, InvalidFileNameException {
		validateFileName(filename);

		File dir = new File("files");
		if (!dir.exists()) {
			dir.mkdir();
		}

		File file = new File(dir, filename);

		try (FileWriter writer = new FileWriter(file)) {
			writer.write(content);
			writer.flush();
			System.out.println("File written successfully: " + file.getAbsolutePath());
		}
	}

	private static void validateFileName(String filename) throws InvalidFileNameException {
		if (!filename.matches("[a-zA-Z0-9_.-]+")) {
			throw new InvalidFileNameException("Invalid file name: " + filename);
		}
	}

	public static void listFiles() {
		File dir = new File("files");
		if (!dir.exists() || !dir.isDirectory()) {
			System.out.println("No files found.");
			return;
		}

		File[] files = dir.listFiles();
		if (files != null && files.length > 0) {
			System.out.println("Existing files:");
			for (File file : files) {
				System.out.println(file.getName());
			}
		} else {
			System.out.println("No files found.");
		}
	}

	public static void readFile(String filename) throws IOException {
		File file = new File("files", filename);
		if (!file.exists()) {
			System.out.println("File does not exist: " + filename);
			return;
		}

		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			String line;
			System.out.println("Content of " + filename + ":");
			while ((line = reader.readLine()) != null) {
				System.out.println(line);
			}
		}
	}

	public static void removeFile(String filename) {
		File file = new File("files", filename);
		if (file.exists() && file.delete()) {
			System.out.println("File deleted: " + filename);
		} else {
			System.out.println("File not found or could not be deleted.");
		}
	}

	public static void removeAllFiles() {
		File dir = new File("files");
		if (dir.exists() && dir.isDirectory()) {
			File[] files = dir.listFiles();
			if (files != null) {
				for (File file : files) {
					file.delete();
				}
			}
			System.out.println("All files have been deleted.");
		} else {
			System.out.println("No files to delete.");
		}
	}

	public static void updateFile(String filename, String newContent) throws IOException {
		File file = new File("files", filename);
		if (!file.exists()) {
			System.out.println("File does not exist: " + filename);
			return;
		}

		try (FileWriter writer = new FileWriter(file, false)) {
			writer.write(newContent);
			writer.flush();
			System.out.println("File updated successfully.");
		}
	}

	public static void updateAllFiles(String newContent) throws IOException {
		File dir = new File("files");
		if (dir.exists() && dir.isDirectory()) {
			File[] files = dir.listFiles();
			if (files != null && files.length > 0) {
				for (File file : files) {
					try (FileWriter writer = new FileWriter(file, false)) {
						writer.write(newContent);
						writer.flush();
					}
				}
				System.out.println("All files updated successfully.");
			} else {
				System.out.println("No files to update.");
			}
		} else {
			System.out.println("No files to update.");
		}
	}

	public static void main(String[] args) {
		try (Scanner scan = new Scanner(System.in)) {
			System.out.println("Enter command (write/list/read/remove/removeAll/update/updateAll):");
			String command = scan.nextLine();

			switch (command.toLowerCase()) {
			case "write":
				System.out.println("Enter filename:");
				String filename = scan.nextLine();
				System.out.println("Enter content:");
				String content = scan.nextLine();
				writeToFile(filename, content);
				break;

			case "list":
				listFiles();
				break;

			case "read":
				System.out.println("Enter filename to read:");
				String fileToRead = scan.nextLine();
				readFile(fileToRead);
				break;

			case "remove":
				System.out.println("Enter filename to remove:");
				String fileToRemove = scan.nextLine();
				removeFile(fileToRemove);
				break;

			case "removeall":
				removeAllFiles();
				break;

			case "update":
				System.out.println("Enter filename to update:");
				String fileToUpdate = scan.nextLine();
				System.out.println("Enter new content:");
				String newContent = scan.nextLine();
				updateFile(fileToUpdate, newContent);
				break;

			case "updateall":
				System.out.println("Enter new content for all files:");
				String contentForAll = scan.nextLine();
				updateAllFiles(contentForAll);
				break;

			default:
				System.out.println("Invalid command.");
			}
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		} finally {
			System.out.println("Finished !!!");
		}
	}
}
