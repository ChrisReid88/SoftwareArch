package regionalOffice;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class RegionalGUI {
	// First set up the panel with the labels and text boxes
	private JPanel inputPanel = new JPanel();
	private JLabel FirstnameLabel = new JLabel("First name");
	private JTextField FirstnameTxt = new JTextField(10);
	
	private JLabel LastnameLabel = new JLabel("First name");
	private JTextField LastnameTxt = new JTextField(10);
	
	private JLabel TimeLabel = new JLabel("First name");
	private JTextField TimeTxt = new JTextField(10);
	
	private JLabel LocationLabel = new JLabel("First name");
	private JTextField LocationTxt = new JTextField(10);
	
	private JLabel ActionLabel = new JLabel("First name");
	private JTextField ActionTxt = new JTextField(10);
	private String data;

	{
		// Initialise the panel
		inputPanel.setLayout(new GridLayout(5, 1));
		inputPanel.add(FirstnameLabel);
		inputPanel.add(FirstnameTxt);
		
		inputPanel.add(LastnameLabel);
		inputPanel.add(LastnameTxt);
		
		inputPanel.add(TimeLabel);
		inputPanel.add(TimeTxt);
		
		inputPanel.add(LocationLabel);
		inputPanel.add(LocationTxt);
		
		inputPanel.add(ActionLabel);
		inputPanel.add(ActionTxt);
	}

	// Next the panel with the buttons
	private JPanel buttonPanel = new JPanel();
	private JButton addButton = new JButton("Enter");

	{
		// Initialise the panel
		buttonPanel.setLayout(new GridLayout(3, 1));
		buttonPanel.add(addButton);
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
	private JTextArea feedbackArea = new JTextArea(10, 40);
	{
		feedbackArea.setEditable(false);
		feedbackPanel.setLayout(new GridLayout(1, 1));
		feedbackPanel.add(feedbackArea);
	}

	// Finally create the window to display the panels
	private JFrame window = new JFrame();
	{
		window.setLayout(new GridLayout(2, 1));
		window.add(topPanel);
		window.add(feedbackPanel);
		window.pack();
	}

	// The application layer that the GUI layer will talk to
	private RegAppLayerInterface appLayer;

	/**
	 * Default constructor. Requires only the application layer to connect to
	 * 
	 * @param appLayer The application layer that the GUI is connected to
	 */
	public RegionalGUI(RegAppLayerInterface appLayer) {
		this.appLayer = appLayer;

		// Add your custom action listeners here
		addButton.addActionListener(new AddButtonListener());

		// The default close action
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
		
		try {
			int serverPort = 7896;
			ServerSocket listenSocket = new ServerSocket(serverPort);
			while(true) {
				Socket clientSocket = listenSocket.accept();
				Connection c = new Connection(clientSocket);
				data = c.getData();
				String result = appLayer.getPatient(data);
				feedbackArea.setText(result);
			}
		} 
		catch(IOException e) {
			System.out.println("Listen: " + e.getMessage());
		}
		
		
		
	}

	// The action listener on the Add button.
	private class AddButtonListener implements ActionListener {
		// Called when the Add button is clicked
		public void actionPerformed(ActionEvent arg0) {
			// Get the required values from the text fields
			String fname = FirstnameTxt.getText();
			String lname = LastnameTxt.getText();
			String time = TimeTxt.getText();
			String location = LocationTxt.getText();
			String action = ActionTxt.getText();
			
			String result = appLayer.addCall(data);
			//System.out.println(data+" DONE");
			// Set the text in the feedback area to the result
			feedbackArea.setText(result);
		}
	}
}
