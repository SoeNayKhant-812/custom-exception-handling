package exception_handling;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Custom_exception_example1 {

	public static void writeToFile(String filename, String content) throws IOException, InvalidFileNameException {
		validateFileName(filename);

		File file = new File(filename);

		try (FileWriter writer = new FileWriter(file)) {
			writer.write(content);
			writer.flush();
			System.out.println("File written successfully.");
		}

		if (file.exists()) {
			System.out.println("File path: " + file.getAbsolutePath());
			System.out.println("File size: " + file.length() + " bytes");

			try {
				Desktop desktop = Desktop.getDesktop();
				desktop.open(file);

				try (Scanner scanner = new Scanner(System.in)) {
					System.out.println("Do you want to close, update, or delete the file? (close/update/delete/no)");
					String action = scanner.nextLine();

					switch (action.trim().toLowerCase()) {
					case "close":
						System.out.println("Please close the file manually in the opened application.");
						break;
					case "update":
						System.out.println("Enter new content for the file:");
						String newContent = scanner.nextLine();
						updateFile(file, newContent);
						break;
					case "delete":
						deleteFile(file);
						break;
					case "no":
						System.out.println("No action taken.");
						break;
					default:
						System.out.println("Invalid action.");
					}
				}
			} catch (IOException e) {
				System.err.println("Error opening file: " + e.getMessage());
			}
		} else {
			System.out.println("Failed to create the file.");
		}
	}

	private static void validateFileName(String filename) throws InvalidFileNameException {
		if (!filename.matches("[a-zA-Z0-9_.]+")) {
			throw new InvalidFileNameException("Invalid file name: " + filename);
		}
	}

	private static void updateFile(File file, String newContent) throws IOException {
		try (FileWriter writer = new FileWriter(file, false)) {
			writer.write(newContent);
			writer.flush();
		}
		String content = readContent(file.getAbsolutePath());
		System.out.println("Content inside file: " + content);
	}

	private static void deleteFile(File file) {
		if (file.delete()) {
			System.out.println("File deleted successfully.");
		} else {
			System.out.println("Failed to delete the file.");
		}
	}

	public static String readContent(String filePath) throws IOException {
		StringBuilder content = new StringBuilder();
		try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
			String line;
			while ((line = reader.readLine()) != null) {
				content.append(line).append("\n");
			}
		} catch (FileNotFoundException e) {
			System.out.println("File not found: " + e.getMessage());
		} catch (IOException e) {
			System.out.println("Error reading file: " + e.getMessage());
		}
		return content.toString().trim();
	}

	public static void main(String[] args) {
		try (Scanner scan = new Scanner(System.in)) {
			System.out.println("Do you wanna write a file ?");
			String answer = scan.nextLine();
			if (answer.equals("yes")) {
				writeToFile("valid_file.txt", "Hello, World!");
			}
		} catch (IOException e) {
			System.out.println("IO Exception: " + e.getMessage());
		} catch (InvalidFileNameException e) {
			System.out.println("Validation Exception: " + e.getMessage());
		} finally {
			System.out.println("Finished");
		}
	}
}
