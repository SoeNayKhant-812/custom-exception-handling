package stream_event_handling;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class StreamEventHandlingExample {
	private static List<String> eventLogs = new ArrayList<>(); // Stores event logs

	public static void main(String[] args) {
		// Create Frame
		JFrame frame = new JFrame("Event Handling with Streams");
		frame.setSize(400, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new FlowLayout());

		// Center the frame on the screen
		frame.setLocationRelativeTo(null);

		// Create Buttons
		JButton clickButton = new JButton("Click Me");
		JButton filterLogsButton = new JButton("Show Click Logs");
		JButton exitButton = new JButton("Exit");

		// SimpleDateFormat for custom date-time format
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy/HH/mm/ss");

		// Event Listener using Lambda for the click button
		clickButton.addActionListener(e -> {
			String currentDateTime = dateFormat.format(new Date()); // Format current date-time
			String log = "Button clicked at " + currentDateTime + " | Source: " + e.getSource();
			eventLogs.add(log);
			System.out.println(log);
		});

		// Using Stream to filter and process logs for the filterLogsButton
		filterLogsButton.addActionListener(e -> {
			List<String> filteredLogs = eventLogs.stream().filter(log -> log.contains("Button clicked"))
					.collect(Collectors.toList());

			// Convert logs to a single string
			String logText = String.join("\n", filteredLogs);

			// Create JTextArea for displaying logs
			JTextArea textArea = new JTextArea(logText, 10, 30); // Medium-sized (10 rows, 30 columns)
			textArea.setEditable(false);

			// Add scroll pane to make it scrollable
			JScrollPane scrollPane = new JScrollPane(textArea);
			scrollPane.setPreferredSize(new Dimension(600, 200)); // Set preferred size for the dialog

			// Show logs in a scrollable dialog
			JOptionPane.showMessageDialog(frame, scrollPane, "Filtered Logs", JOptionPane.INFORMATION_MESSAGE);
		});

		// Event Listener for Exit Button
		exitButton.addActionListener(_ -> System.exit(0)); // Exit the application when clicked

		// Add components to frame
		frame.add(clickButton);
		frame.add(filterLogsButton);
		frame.add(exitButton); // Add the exit button to the frame

		// Show Frame
		frame.setVisible(true);
	}
}
