/*	Program:		 Lab 3
 *	
 * 	Student Name: 	 Robert Hannaford
 *	Semester: 		 Spring 2023
 *	Class Section: 	 COSC 20203
 *	Instructor: 	 Dr. Rinewalt
 * 
 */
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.prefs.*;
import java.awt.FileDialog;

public class Lab3 extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;

	private JButton addButton;
	private JLabel addressLabel;
	private JPanel addressPanel;
	private JTextField addressTextField;
	private JPanel buttonPanel;
	private JLabel cityLabel;
	private JPanel cityStatePanel;
	private JTextField cityTextField;
	private JButton deleteButton;
	private JButton findButton;
	private JButton firstButton;
	private JLabel givenNameLabel;
	private JPanel givenNamePanel;
	private JTextField givenNameTextField;
	private JButton lastButton;
	private JButton nextButton;
	private JButton previousButton;
	private JLabel stateLabel;
	private JTextField stateTextField;
	private JLabel surnameLabel;
	private JPanel surnamePanel;
	private JTextField surnameTextField;
	private JButton updateButton;
	
	String bookFile = null;
	String indexFile = null;
	
	RandomAccessFile index; 
	RandomAccessFile book;

	public Lab3() {
		setTitle("Address Book");
		setBounds(100, 100, 704, 239);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new java.awt.GridLayout(5, 0));

		surnamePanel = new JPanel();
		surnameLabel = new JLabel();
		surnameTextField = new JTextField();
		givenNamePanel = new JPanel();
		givenNameLabel = new JLabel();
		givenNameTextField = new JTextField();
		addressPanel = new JPanel();
		addressLabel = new JLabel();
		addressTextField = new JTextField();
		cityStatePanel = new JPanel();
		cityLabel = new JLabel();
		cityTextField = new JTextField();
		stateLabel = new JLabel();
		stateTextField = new JTextField();
		buttonPanel = new JPanel();
		firstButton = new JButton();
		nextButton = new JButton();
		previousButton = new JButton();
		lastButton = new JButton();
		findButton = new JButton();
		addButton = new JButton();
		deleteButton = new JButton();
		updateButton = new JButton();

		surnamePanel.setName("surnamePanel");

		surnameLabel.setText("Surname");
		surnameLabel.setName("surnameLabel");
		surnamePanel.add(surnameLabel);

		surnameTextField.setColumns(45);
		surnameTextField.setText("");
		surnameTextField.setName("surnameTextField");
		surnamePanel.add(surnameTextField);

		getContentPane().add(surnamePanel);

		givenNamePanel.setName("givenNamePanel");

		givenNameLabel.setText("Given Names");
		givenNameLabel.setName("givenNameLabel");
		givenNamePanel.add(givenNameLabel);

		givenNameTextField.setColumns(45);
		givenNameTextField.setText("");
		givenNameTextField.setName("givenNameTextField");
		givenNamePanel.add(givenNameTextField);

		getContentPane().add(givenNamePanel);

		addressPanel.setName("addressPanel");

		addressLabel.setText("Street Address");
		addressLabel.setName("addressLabel");
		addressPanel.add(addressLabel);

		addressTextField.setColumns(45);
		addressTextField.setText("");
		addressTextField.setName("addressTextField");
		addressPanel.add(addressTextField);

		getContentPane().add(addressPanel);

		cityStatePanel.setName("cityStatePanel");

		cityLabel.setText("City");
		cityLabel.setName("cityLabel");
		cityStatePanel.add(cityLabel);

		cityTextField.setColumns(30);
		cityTextField.setText("");
		cityTextField.setName("cityTextField");
		cityStatePanel.add(cityTextField);

		stateLabel.setText("State");
		stateLabel.setName("stateLabel");
		cityStatePanel.add(stateLabel);

		stateTextField.setColumns(5);
		stateTextField.setText("");
		stateTextField.setName("stateTextField");
		cityStatePanel.add(stateTextField);

		getContentPane().add(cityStatePanel);

		buttonPanel.setName("buttonPanel");

		firstButton.setText("First");
		firstButton.setName("firstButton");
		firstButton.addActionListener(this);
		buttonPanel.add(firstButton);

		nextButton.setText("Next");
		nextButton.setName("nextButton");
		nextButton.addActionListener(this);
		buttonPanel.add(nextButton);

		previousButton.setText("Previous");
		previousButton.setName("previousButton");
		previousButton.addActionListener(this);
		buttonPanel.add(previousButton);

		lastButton.setText("Last");
		lastButton.setName("lastButton");
		lastButton.addActionListener(this);
		buttonPanel.add(lastButton);

		findButton.setText("Find");
		findButton.setName("findButton");
		findButton.addActionListener(this);
		buttonPanel.add(findButton);

		addButton.setText("Add");
		addButton.setEnabled(false);
		addButton.setName("addButton");
		addButton.addActionListener(this);
		buttonPanel.add(addButton);

		deleteButton.setText("Delete");
		deleteButton.setEnabled(false);
		deleteButton.setName("deleteButton");
		deleteButton.addActionListener(this);
		buttonPanel.add(deleteButton);

		updateButton.setText("Update");
		updateButton.setEnabled(false);
		updateButton.setName("updateButton");
		updateButton.addActionListener(this);
		buttonPanel.add(updateButton);

		getContentPane().add(buttonPanel);

		getFiles();
		
		try {
			index = new RandomAccessFile(indexFile, "r");
			book = new RandomAccessFile(bookFile, "r");
		} catch(IOException ioe) {
			System.out.println(ioe);
			System.exit(0);
		}

	}
	
	void getFiles() {
			FileDialog fd = new FileDialog(this, "Select the Address Book", FileDialog.LOAD);
			fd.setVisible(true);
			String filename = fd.getFile();
			if (filename == null)
				System.exit(0);
			bookFile = fd.getDirectory() + filename;
			fd = new FileDialog(this, "Select the Index File", FileDialog.LOAD);
			fd.setVisible(true);
			filename = fd.getFile();
			if (filename == null)
				System.exit(0);
			indexFile = fd.getDirectory() + filename;
	}


	public static void main(String[] args) {
		Lab3 window = new Lab3();
		window.setVisible(true);
	}

/***************************************************************/

	public void actionPerformed(ActionEvent evt) {
//implement this method
//you may add additional methods as needed
//do not change any of the code that I have written
//other than to activate buttons for extra credit

Object o = evt.getSource();	


//If first button is clicked go to index 0
if(o == firstButton) {
	try {
		index.seek(0);
		read();
		
	}catch(IOException e) {
		System.out.print("caught");
	}
	

//If next button is clicked print the next entry
}else if(o == nextButton) {
read();


//If previous button is clicked go to the previous entry and print
}else if(o == previousButton) {

	try {
		long posIndex = index.getFilePointer();
		index.seek(posIndex - 16);
		read();
	}catch(IOException e) {
		
	}
	
	
//If last button is clicked go to the last entry and print
}else if(o == lastButton) {
	
	try {
		long lengthIndex = index.length();
		index.seek(lengthIndex - 8);
		read();
		
	}catch(IOException e) {
		
	}
	

//If find button is pressed use a binary search to find the index of the exact
//or closest entry and print
}else if(o == findButton) {
    try {
        index.seek(0);
        long upperValue = index.length()-8;
        String first = givenNameTextField.getText();
        String last = surnameTextField.getText();
        index.seek(binarySearch(first, last, 0, upperValue));
       read();
        
    } catch (IOException e) {
    	
    }
}
}


	
//method uses a recursive binary search in order to locate the index of the last name and first name that are searched for
	public long binarySearch(String firstName, String lastName, long lower, long upper) {
	    if (lower > upper)
	        return -1;
	    long mid = (lower + upper) / 2; 
	    mid = divisibleby8(mid); 
		  
	    try {
	        index.seek(mid);
	        book.seek(index.readLong());
	        
	        String midLastName = book.readUTF();
	        String midFirstName = book.readUTF();
	       
	        int firstNameComparison = midFirstName.compareTo(firstName);
	        int lastNameComparison = midLastName.compareTo(lastName);    
	        
	     // Compare the first and last names with the search values 
	     //return base case or recursive step
	        if(lastNameComparison == 0 && firstNameComparison == 1 && mid == 8)
	        	return mid - 8;
	        if(mid == upper)
	        	  return mid;
	
	        if (lastNameComparison == 0) {
	            if (firstNameComparison == 0)
	                return mid;
	            else if (firstNameComparison < 0)
	                return binarySearch(firstName, lastName, mid, upper); 
	            else
	                return binarySearch(firstName, lastName, lower, mid); 
	        } else if (lastNameComparison < 0)
	            return binarySearch(firstName, lastName, mid, upper);
	        else
	            return binarySearch(firstName, lastName, lower, mid);
	        
	    } catch (IOException e) {
	        return -1;
	    }     
	}
	

//method to ensure that the mid is readable/divisible by 8
//if not then shift to the nearest readable location
public long divisibleby8(long num) {
    if (num % 8 == 0) {
        return num;
    } else {
        if (num % 8 < 4) 
            return num - num % 8;    
        else    
            return num + num % 8;
    }
}

//method to read UTF and return the string to the correct text fields
void read(){
	try {
		book.seek(index.readLong());
		String surname = book.readUTF();
		surnameTextField.setText(surname);
		String firstName = book.readUTF();
		givenNameTextField.setText(firstName);
		String streetAddress = book.readUTF();
		addressTextField.setText(streetAddress);
		String city = book.readUTF();
		cityTextField.setText(city);
		String state = book.readUTF();
		stateTextField.setText(state);
	}catch(IOException e) {
}
}

	
}
