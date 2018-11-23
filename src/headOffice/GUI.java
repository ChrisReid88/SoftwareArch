package headOffice;

import java.awt.*;
import java.awt.event.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.*;

public class GUI {
	// First set up the panel with the labels and text boxes

	private JPanel inputPanel = new JPanel();
	private JLabel firstNameLabel = new JLabel("First name");
	private JTextField firstNameTxt = new JTextField(10);
	private JLabel lastNameLabel = new JLabel("Last name");
	private JTextField lastNameTxt = new JTextField(10);
	private JLabel regNumberLabel = new JLabel("NHS Registration number");
	private JTextField regNumberTxt = new JTextField(10);
	private JLabel addressLabel = new JLabel("Address");
	private JTextField addressTxt = new JTextField(10);
	private JLabel conditionLabel = new JLabel("Medical condition");
	private JTextField conditionTxt = new JTextField(10);

	{
		// Initialise the panel
		inputPanel.setLayout(new GridLayout(5, 1));
		inputPanel.add(firstNameLabel);
		inputPanel.add(firstNameTxt);
		inputPanel.add(lastNameLabel);
		inputPanel.add(lastNameTxt);
		inputPanel.add(regNumberLabel);
		inputPanel.add(regNumberTxt);
		inputPanel.add(addressLabel);
		inputPanel.add(addressTxt);
		inputPanel.add(conditionLabel);
		inputPanel.add(conditionTxt);
	}

	// Next the panel with the buttons
	private JPanel buttonPanel = new JPanel();
	private JButton addButton = new JButton("Enter Details");
	private JButton clearButton = new JButton("Clear fields");
	private JButton exitButton = new JButton("Exit");
//	private JButton removeButton = new JButton("Remove Student");
	{
		// Initialise the panel
		buttonPanel.setLayout(new GridLayout(3, 1));
		buttonPanel.add(addButton);
		buttonPanel.add(clearButton);
		buttonPanel.add(exitButton);
	}

	// Now create a panel with the input and button panels in. This is the top panel
	private JPanel topPanel = new JPanel();
	{
		topPanel.setLayout(new FlowLayout());
		topPanel.add(inputPanel);
		topPanel.add(buttonPanel);
	}

	// Create the panel which will display the feedback text
	private JPanel feedbackPanel = new JPanel();
	private JTextArea feedbackArea = new JTextArea(5, 30);
	{
		feedbackArea.setEditable(false);
		feedbackPanel.setLayout(new GridLayout(1, 1));
		feedbackPanel.add(feedbackArea);
	}

	// Finally create the window to display the panels
	private JFrame window = new JFrame("Head Office");
	{
		window.setLayout(new GridLayout(2, 1));
		window.add(topPanel);
		window.add(feedbackPanel);
		window.pack();
	}

	// The application layer that the GUI layer will talk to
	private PatientApplicationLayerInterface appLayer;

	/**
	 * Default constructor. Requires only the application layer to connect to
	 * 
	 * @param appLayer The application layer that the GUI is connected to
	 */
	public GUI(PatientApplicationLayerInterface appLayer) {
		this.appLayer = appLayer;

		// Add your custom action listeners here
		addButton.addActionListener(new AddButtonListener());
		exitButton.addActionListener(new ExitButtonListener());
		clearButton.addActionListener(new ClearButtonListener());

		// The default close action
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
		feedbackArea.setText("Call-out details sent to Regional Hospital!");
	}

	// The action listener on the Add button.
	private class AddButtonListener implements ActionListener {
		// Called when the Add button is clicked
		public void actionPerformed(ActionEvent arg0) {
			// Get the required values from the text fields
			String firstname = firstNameTxt.getText();
			String lastname = lastNameTxt.getText();
			String regNumber = regNumberTxt.getText();
			String address = addressTxt.getText();
			String condition = conditionTxt.getText();
			// Try and add the student record. Get the result from the operation
			String result = appLayer.addPatient(firstname, lastname, regNumber, address, condition);
			// Set the text in the feedback area to the result

		}
	}

	private class ClearButtonListener implements ActionListener {
		// Called when the Add button is clicked
		public void actionPerformed(ActionEvent arg0) {
			// Close the program
			firstNameTxt.setText("");
			lastNameTxt.setText("");
			regNumberTxt.setText("");
			addressTxt.setText("");
			conditionTxt.setText("");
			
		}
	}

	private class ExitButtonListener implements ActionListener {
		// Called when the Add button is clicked
		public void actionPerformed(ActionEvent arg0) {
			// Close the program
			System.exit(0);

		}
	}
}
