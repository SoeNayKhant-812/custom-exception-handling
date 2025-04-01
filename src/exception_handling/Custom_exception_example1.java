package exception_handling;

import java.io.*;
import java.util.Scanner;
import java.util.stream.Stream;

public class Custom_exception_example1 {

	private static final String DIRECTORY_PATH = "files";

	public static void writeToFile(String filename, String content) throws IOException, InvalidFileNameException {
		validateFileName(filename);
		ensureDirectoryExists();

		File file = new File(DIRECTORY_PATH, filename);
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

	private static void ensureDirectoryExists() {
		File dir = new File(DIRECTORY_PATH);
		if (!dir.exists()) {
			dir.mkdir();
		}
	}

	public static void listFiles() {
		File dir = new File(DIRECTORY_PATH);
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
		File file = new File(DIRECTORY_PATH, filename);
		if (!file.exists()) {
			System.out.println("File does not exist: " + filename);
			return;
		}

		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			System.out.println("Content of " + filename + ":");
			reader.lines().forEach(System.out::println);
		}
	}

	public static void copyFile(String sourceFilename, String newFilename)
			throws IOException, InvalidFileNameException {
		File sourceFile = new File(DIRECTORY_PATH, sourceFilename);
		File newFile = new File(DIRECTORY_PATH, newFilename);

		if (!sourceFile.exists()) {
			System.err.println("Source file does not exist: " + sourceFilename);
			return;
		}

		validateFileName(newFilename);
		if (newFile.exists()) {
			System.err.println("File already exists: " + newFilename);
			return;
		}

		try (BufferedReader reader = new BufferedReader(new FileReader(sourceFile));
				FileWriter writer = new FileWriter(newFile);
				Stream<String> lines = reader.lines()) {

			lines.forEach(line -> {
				try {
					writer.write(line + System.lineSeparator());
				} catch (IOException e) {
					throw new RuntimeException("Error writing to file: " + e.getMessage(), e);
				}
			});
			writer.flush();
			System.out.println("File copied successfully: " + newFilename);
		} catch (IOException e) {
			System.err.println("Error copying file: " + e.getMessage());
		}
	}

	public static void removeFile(String filename) {
		File file = new File(DIRECTORY_PATH, filename);
		if (file.exists() && file.delete()) {
			System.out.println("File deleted: " + filename);
		} else {
			System.out.println("File not found or could not be deleted.");
		}
	}

	public static void removeAllFiles() {
		File dir = new File(DIRECTORY_PATH);
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
		File file = new File(DIRECTORY_PATH, filename);
		if (!file.exists()) {
			System.out.println("File does not exist: " + filename);
			return;
		}

		try (FileWriter writer = new FileWriter(file)) {
			writer.write(newContent);
			writer.flush();
			System.out.println("File updated successfully.");
		}
	}

	public static void updateAllFiles(String newContent) throws IOException {
		File dir = new File(DIRECTORY_PATH);
		if (dir.exists() && dir.isDirectory()) {
			File[] files = dir.listFiles();
			if (files != null && files.length > 0) {
				for (File file : files) {
					try (FileWriter writer = new FileWriter(file)) {
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
			System.out.println("Enter command (write/list/read/copy/remove/removeAll/update/updateAll):");
			String command = scan.nextLine().trim().toLowerCase();

			switch (command) {
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
				readFile(scan.nextLine());
				break;
			case "copy":
				System.out.println("Enter source filename:");
				String source = scan.nextLine();
				System.out.println("Enter new filename:");
				String destination = scan.nextLine();
				copyFile(source, destination);
				break;
			case "remove":
				System.out.println("Enter filename to remove:");
				removeFile(scan.nextLine());
				break;
			case "removeall":
				removeAllFiles();
				break;
			case "update":
				System.out.println("Enter filename to update:");
				String fileToUpdate = scan.nextLine();
				System.out.println("Enter new content:");
				updateFile(fileToUpdate, scan.nextLine());
				break;
			case "updateall":
				System.out.println("Enter new content for all files:");
				updateAllFiles(scan.nextLine());
				break;
			default:
				System.out.println("Invalid command.");
			}
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
		} finally {
			System.out.println("Finished !!!");
		}
	}
}
