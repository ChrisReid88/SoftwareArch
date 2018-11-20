package regionalOffice;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
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
	private String result;

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
	private JButton sendButton = new JButton("Send");

	{
		// Initialise the panel
		buttonPanel.setLayout(new GridLayout(2, 1));
		buttonPanel.add(addButton);
		buttonPanel.add(sendButton);
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
	private JFrame window = new JFrame("Regional Hospital");
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
		String[] lines = null;
		
		// Add your custom action listeners here
		addButton.addActionListener(new AddButtonListener());
		sendButton.addActionListener(new SendButtonListener());

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
				result = appLayer.getPatient(data);
				lines = result.split("\\s*\\r?\\n\\s*");
				
				feedbackArea.setText("RECEIVED FROM HEAD OFFICE!" + 
									"\nNHS Registration No.:    " +lines[0] +
									"\nFirst name:                        " + lines[1] + 
									"\nLast name:                        " + lines[2] +
									"\nAddress:                            " + lines[3] +
									"\nCondition:                          " + lines[4]);
				
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
			
			String add = appLayer.addCall(data);
			//System.out.println(data+" DONE");
			// Set the text in the feedback area to the result
			feedbackArea.setText(result);
		}
	}
	private class SendButtonListener implements ActionListener {
		// Called when the Add button is clicked
		public void actionPerformed(ActionEvent arg0) {
			// Get the required values from the text fields
	
			
			Socket s = null;
			try {
				int serverPort = 6000;
				s = new Socket("localhost", serverPort);
				DataInputStream in = new DataInputStream( s.getInputStream());
				DataOutputStream out = new DataOutputStream( s.getOutputStream());
				System.out.println(result);
				out.writeUTF(result); // UTF is a string encoding format
//				String co = in.readUTF();
//				System.out.println(co);
				s.close();
			} 
			catch (Exception e){
				System.out.println("Error:"+e.getMessage());
			}
		}
	}
}
