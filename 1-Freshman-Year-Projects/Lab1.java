/*	Program:		 Lab 1
 *	
 * 	Student Name: 	 Robert Hannaford
 *	Semester: 		 Spring 2023
 *	Class Section: 	 COSC 20203
 *	Instructor: 	 Dr. Rinewalt
 * 
 */

import javax.swing.*;
import java.awt.event.*;
import java.util.*;

public class Lab1 extends JFrame implements ActionListener {
	static final long serialVersionUID = 1l;
	private JTextField assemblerInstruction;
	private JTextField binaryInstruction;
	private JTextField hexInstruction;
	private JLabel errorLabel;
	
	public Lab1() {
		setTitle("XDS Sigma 9");
		setBounds(100, 100, 400, 400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
// * * * * * * * * * * * * * * * * * * * * * * * * * * * *
		
// SET UP THE ASSEMBLY LANGUAGE TEXTFIELD AND BUTTON
		assemblerInstruction = new JTextField();
		assemblerInstruction.setBounds(25, 24, 134, 28);
		getContentPane().add(assemblerInstruction);

		JLabel lblAssemblyLanguage = new JLabel("Assembly Language");
		lblAssemblyLanguage.setBounds(30, 64, 160, 16);
		getContentPane().add(lblAssemblyLanguage);

		JButton btnEncode = new JButton("Encode");
		btnEncode.setBounds(200, 25, 117, 29);
		getContentPane().add(btnEncode);
		btnEncode.addActionListener(this);	
// * * * * * * * * * * * * * * * * * * * * * * * * * * * *
		
// SET UP THE BINARY INSTRUCTION TEXTFIELD AND BUTTON
		binaryInstruction = new JTextField();
		binaryInstruction.setBounds(25, 115, 330, 28);
		getContentPane().add(binaryInstruction);

		JLabel lblBinary = new JLabel("Binary Instruction");
		lblBinary.setBounds(30, 155, 190, 16);
		getContentPane().add(lblBinary);

		JButton btnDecode = new JButton("Decode Binary");
		btnDecode.setBounds(200, 150, 150, 29);
		getContentPane().add(btnDecode);
		btnDecode.addActionListener(this);	
// * * * * * * * * * * * * * * * * * * * * * * * * * * * *
		
// SET UP THE HEX INSTRUCTION TEXTFIELD AND BUTTON
		hexInstruction = new JTextField();
		hexInstruction.setBounds(25, 220, 134, 28);
		getContentPane().add(hexInstruction);

		JLabel lblHexEquivalent = new JLabel("Hex Instruction");
		lblHexEquivalent.setBounds(30, 260, 131, 16);
		getContentPane().add(lblHexEquivalent);

		JButton btnDecodeHex = new JButton("Decode Hex");
		btnDecodeHex.setBounds(200, 220, 150, 29);
		getContentPane().add(btnDecodeHex);
		btnDecodeHex.addActionListener(this);
// * * * * * * * * * * * * * * * * * * * * * * * * * * * *
		
// SET UP THE LABEL TO DISPLAY ERROR MESSAGES
		errorLabel = new JLabel("");
		errorLabel.setBounds(25, 320, 280, 16);
		getContentPane().add(errorLabel);
	}

	public void actionPerformed(ActionEvent evt) {
		errorLabel.setText("");
		if (evt.getActionCommand().equals("Encode")) {
			encode();
		} else if (evt.getActionCommand().equals("Decode Binary")) {
			decodeBin();
		} else if (evt.getActionCommand().equals("Decode Hex")) {
			decodeHex();
		}
	}

	public static void main(String[] args) {
		Lab1 window = new Lab1();
		window.setVisible(true);
	}

// USE THE FOLLOWING METHODS TO CREATE A STRING THAT IS THE
// BINARY OR HEX REPRESENTATION OF A SORT OR INT
// CONVERT AN INT TO 8 HEX DIGITS
	String displayIntAsHex(int x) {
		String ans="";
		for (int i=0; i<8; i++) {
			int hex = x & 15;
			char hexChar = "0123456789ABCDEF".charAt(hex);
			ans = hexChar + ans;
			x = (x >> 4);
		}
		return ans;
	}
// CONVERT AN INT TO 32 BINARY DIGITS
	String displayIntAsBinary(int x) {
		String ans="";
		for(int i=0; i<32; i++) {
			ans = (x & 1) + ans;
			x = (x >> 1);
		}
		return ans;
	}
	
/************************************************************************/
/* Put your implementation of the encode, decodeBin, and decodeHex      */
/* methods here. You may add any other methods that you think are       */
/* appropriate. However, you MUST NOT change anything in the code       */
/* that I have written.                                                 */
/************************************************************************/
	void encode() {
			
		String varAssemString = assemblerInstruction.getText();
		int machine = 0;

		int posComma = varAssemString.indexOf(",");

		errorLabel.setText("");
		boolean validInput = true;
		boolean validInputD = true;
		boolean validInputX = true;
		boolean validNumber = true; 

		   /** LI SECTION **/
		    // check if the input string has at least 4 characters and the first two characters are "LI"
		    if (varAssemString.length() >= 5 && varAssemString.substring(0, posComma).equals("LI")) {
		        machine = machine | (0b00100010 << 24);
		        int posBlank = varAssemString.indexOf(" ");

		        // check if there is a space after the comma
		        if (posBlank == posComma + 1) {
		            validInput = false;
		        } else {
		        	String registerString = "-1";
		        	
		        	try {
		             registerString = varAssemString.substring(posComma + 1, posBlank);
		        	}catch(StringIndexOutOfBoundsException e) {
		        		
		        	}
		            int registerInt = 0;
		       
		            // check if registerInt is a valid integer
		            try {
		                registerInt = Integer.parseInt(registerString);
		               
		            } catch (NumberFormatException e) {
		                validInput = false;
		            }

		            // check register is between 0-15
		            if ((registerInt >= 0) && (registerInt <= 15)) {
		                machine = machine | (registerInt << 20);
		                String valueString = varAssemString.substring(posBlank + 1);
		                int valueInt = 0;

		                // check if valueInt is a valid integer
		                try {
		                    valueInt = Integer.parseInt(valueString);
		                } catch (NumberFormatException e) {
		                    validNumber = false;
		                }

		                // check if valueInt is between -524288 and 524287
		                if ((valueInt >= -524288) && (valueInt <= 524287)) {
		                    valueInt = valueInt & (0b00000000000011111111111111111111);
		                    machine = machine | valueInt;
		                } else {
		                    validNumber = false;
		                }
		            } else {
		                validInput = false;
		            }
		        }

		     /** LW SECTION **/    
		    }else   if ((posComma == 2 && varAssemString.length() >= 5) && ((varAssemString.substring(0, posComma).equals("LW")))) {
		        machine = machine | (0b00110010 << 24);
		        int posBlank = varAssemString.indexOf(" ");

		        // Check if there is a space after the comma
		        if (posBlank == posComma + 1) {
		            validInput = false;
	
		        } else {
		            String registerString = varAssemString.substring(posComma + 1, posBlank);
		            int registerInt = 0;

		            // Check if registerInt is a valid integer
		            try {
		                registerInt = Integer.parseInt(registerString);
		            } catch (NumberFormatException e) {
		                validInput = false;
		            }

		            // Check register is between 0-15
		            if (registerInt >= 0 && registerInt <= 15) {
		                machine = machine | (registerInt << 20);
		                // Check whether register has a space after it or a space and then an asterisk. If neither, validInput = false;
		                
		                try {
		                    String afterRegisterString = varAssemString.substring(posBlank + 1);
		                    
		                    if (afterRegisterString.contains(",")) {
		                    	
		                    	//if there is an asterisk replace the high order bit with a 1
		    	                if (afterRegisterString.startsWith("*")) {
		    	                	machine = machine | (0b1<<31);
		    	                	afterRegisterString = afterRegisterString.substring(1);
		    	                }
		    	                
		    	                //split the string into the displacement and index register values
		    	                if (afterRegisterString.contains(",")) {
		    	                    String[] parts = afterRegisterString.split(",");
		    	                    if (parts.length == 2) {
		    	                        String displacementString = parts[0];
		    	                        String indexRegisterString = parts[1];
		    	                        int displacementInt = 0;
		    	                        int indexRegisterInt = 0;

		    	                        try {
		    	                            displacementInt = Integer.parseInt(displacementString);
		    	                        } catch (NumberFormatException e) {
		    	                            validInputD = false;
		    	                        }
		    	                        
		    	                        // Check if displacement value is between 0 – 131071
		    	                        if (displacementInt >= 0 && displacementInt < 131071) {
		    	                            machine = machine | (displacementInt);
		    	                            
		    	                        }
		    	                        if(indexRegisterString.contains(" ")) {
		    	                        	validInput = false;
		    	                        }
		    	                        try {
		    	                            indexRegisterInt = Integer.parseInt(indexRegisterString);
		    	                        } catch (NumberFormatException e) {
		    	                            validNumber = false;
		    	                        }
		    	                        // check if the index register value is between 1-7
		    	                        if (indexRegisterInt >= 1 && indexRegisterInt <= 7) {
		    	                            machine = machine | (indexRegisterInt << 17);
		    	                            		    	                       
		    	                        }else
		    	                        		validInputX = false; 
		    	                    }
		    	                }
		    	                
		                    }else {
		                    	// runs if there is no comma after posBlank
		                    	String displacementString = afterRegisterString.substring(0);
		                    	 if (afterRegisterString.startsWith("*")) {
			    	                	machine = machine | (0b1<<31);
			    	                	displacementString = afterRegisterString.substring(1);
			    	                }
		                  
		                    	 int displacementInt = -1;
		                    	 try {
	    	                            displacementInt = Integer.parseInt(displacementString);
	    	                        } catch (NumberFormatException e) {
	    	                            validNumber = false;
	    	                        }

	    	                        // Check if displacement value is between 0 – 131071
	    	                        if (displacementInt >= 0 && displacementInt < 131071) {
	    	                            machine = machine | (displacementInt);
	    	                            
	    	                      }else
	    	                        	validInputD = false;
		                    	
		                    }
		                } catch (StringIndexOutOfBoundsException e) {
		                    validInput = false;
		                }
		                
		               

		            }
		        }
		        /** AW SECTION **/ 
		    }else if(varAssemString.length() >= 5 && varAssemString.substring(0, posComma).equals("AW")) {
		    	
		    	 machine = machine | (0b00110000 << 24);
			        int posBlank = varAssemString.indexOf(" ");
			      
			        // Check if there is a space after the comma
			        if (posBlank == posComma + 1) {
			            validInput = false;
			            
			        } else {
			            String registerString = varAssemString.substring(posComma + 1, posBlank);
			            int registerInt = 0;

			            // Check if registerInt is a valid integer
			            try {
			                registerInt = Integer.parseInt(registerString);
			            } catch (NumberFormatException e) {
			                validInput = false;
			            }

			            // Check register is between 0-15
			            if (registerInt >= 0 && registerInt <= 15) {
			                machine = machine | (registerInt << 20);
			                // Check whether register has a space after it or a space and then an asterisk. If neither, validInput = false;
			                
			                try {
			                    String afterRegisterString = varAssemString.substring(posBlank + 1);
			                    
			                    if (afterRegisterString.contains(",")) {
			                    	
			                    	//if there is an asterisk replace the high order bit with a 1
			    	                if (afterRegisterString.startsWith("*")) {
			    	                	machine = machine | (0b1<<31);
			    	                	afterRegisterString = afterRegisterString.substring(1);
			    	                }
			    	                
			    	                //split the string into the displacement and index register values
			    	                if (afterRegisterString.contains(",")) {
			    	                    String[] parts = afterRegisterString.split(",");
			    	                    if (parts.length == 2) {
			    	                        String displacementString = parts[0];
			    	                        String indexRegisterString = parts[1];
			    	                        int displacementInt = 0;
			    	                        int indexRegisterInt = 0;

			    	                        try {
			    	                            displacementInt = Integer.parseInt(displacementString);
			    	                        } catch (NumberFormatException e) {
			    	                            validInputD = false;
			    	                        }

			    	                        // Check if displacement value is between 0 – 131071
			    	                        if (displacementInt >= 0 && displacementInt < 131071) {
			    	                            machine = machine | (displacementInt);
			    	                            
			    	                        }
			    	                        if(indexRegisterString.contains(" ")) {
			    	                        	validInput = false;
			    	                        }
			    	                        try {
			    	                            indexRegisterInt = Integer.parseInt(indexRegisterString);
			    	                        } catch (NumberFormatException e) {
			    	                            validNumber = false;
			    	                        }
			    	                        // check if the index register value is between 1-7
			    	                        if (indexRegisterInt >= 1 && indexRegisterInt <= 7) {
			    	                            machine = machine | (indexRegisterInt << 17);
			    	                            		    	                       
			    	                        }else
			    	                        		validInputX = false; 
			    	                    }
			    	                }
			    	                
			                    }else {
			                    	// runs if there is no comma after posBlank
			                    	String displacementString = afterRegisterString.substring(0);
			                    	 if (afterRegisterString.startsWith("*")) {
				    	                	machine = machine | (0b1<<31);
				    	                	displacementString = afterRegisterString.substring(1);
				    	                }
			                  
			                    	 int displacementInt = -1;
			                    	 try {
		    	                            displacementInt = Integer.parseInt(displacementString);
		    	                        } catch (NumberFormatException e) {
		    	                            validNumber = false;
		    	                        }

		    	                        // Check if displacement value is between 0 – 131071
		    	                        if (displacementInt >= 0 && displacementInt < 131071) {
		    	                            machine = machine | (displacementInt);
		    	                            
		    	                      }else
		    	                        	validInputD = false;
			                    	
			                    }
			                } catch (StringIndexOutOfBoundsException e) {
			                    validInput = false;
			                }
			                
			            }
			        }
			 /** STW SECTION **/
		    }else if(varAssemString.length() >= 6 && varAssemString.substring(0, posComma).equals("STW")) {
		    	machine = machine | (0b00110101 << 24);
		        int posBlank = varAssemString.indexOf(" ");

		      
		        // Check if there is a space after the comma
		        if (posBlank == posComma + 1) {
		            validInput = false;

		        } else {
		            String registerString = varAssemString.substring(posComma + 1, posBlank);
		            int registerInt = 0;

		            // Check if registerInt is a valid integer
		            try {
		                registerInt = Integer.parseInt(registerString);

		            } catch (NumberFormatException e) {
		                validInput = false;
		            }

		            // Check register is between 0-15
		            if (registerInt >= 0 && registerInt <= 15) {
		                machine = machine | (registerInt << 20);
		                // Check whether register has a space after it or a space and then an asterisk. If neither, validInput = false;
		                
		                try {
		                    String afterRegisterString = varAssemString.substring(posBlank + 1);
		                    if (afterRegisterString.contains(",")) {
		                    	
		                    	//if there is an asterisk replace the high order bit with a 1
		    	                if (afterRegisterString.startsWith("*")) {
		    	                	machine = machine | (0b1<<31);
		    	                	afterRegisterString = afterRegisterString.substring(1);
		    	                }
		    	                
		    	                //split the string into the displacement and index register values
		    	                if (afterRegisterString.contains(",")) {
		    	                    String[] parts = afterRegisterString.split(",");
		    	                    if (parts.length == 2) {
		    	                        String displacementString = parts[0];
		    	                        String indexRegisterString = parts[1];
		    	                        int displacementInt = 0;
		    	                        int indexRegisterInt = 0;

		    	                        try {
		    	                            displacementInt = Integer.parseInt(displacementString);
		    	                        } catch (NumberFormatException e) {
		    	                            validInputD = false;
		    	                        }

		    	                        // Check if displacement value is between 0 – 131071
		    	                        if (displacementInt >= 0 && displacementInt < 131071) {
		    	                            machine = machine | (displacementInt);
		    	                            
		    	                        }
		    	                        if(indexRegisterString.contains(" ")) {
		    	                        	validInput = false;
		    	                        }
		    	                        try {
		    	                            indexRegisterInt = Integer.parseInt(indexRegisterString);
		    	                        } catch (NumberFormatException e) {
		    	                        	validNumber = false;
		    	                        }
		    	                        // check if the index register value is between 1-7
		    	                        if (indexRegisterInt >= 1 && indexRegisterInt <= 7) {
		    	                            machine = machine | (indexRegisterInt << 17);
		    	 		    	                            		    	                       
		    	                        }else
		    	                        		validInputX = false; 
		    	                    }
		    	                }
		    	                
		                    }else {
		                    	// runs if there is no comma after posBlank
		                    	String displacementString = afterRegisterString.substring(0);
		                    	 if (afterRegisterString.startsWith("*")) {
			    	                	machine = machine | (0b1<<31);
			    	                	displacementString = afterRegisterString.substring(1);
			    	                }
		                  
		                    	 int displacementInt = -1;
		                    	 try {
	    	                            displacementInt = Integer.parseInt(displacementString);
	    	                        } catch (NumberFormatException e) {
	    	                            validNumber = false;
	    	                        }

	    	                        // Check if displacement value is between 0 – 131071
	    	                        if (displacementInt >= 0 && displacementInt < 131071) {
	    	                            machine = machine | (displacementInt);
	    	                            
	    	                      }else
	    	                        	validInputD = false;
		                    	
		                    }
		                } catch (StringIndexOutOfBoundsException e) {
		                    validInput = false;
		                }
		              
		            }
		        }
		    	
		    }
		    
		    else {
		        validInput = false;
		    }

			//Different error types and error messages
		if(validInput && validInputD && validInputX && validNumber) {
			hexInstruction.setText(displayIntAsHex(machine));
			binaryInstruction.setText(displayIntAsBinary(machine));
			errorLabel.setText("");
			
		}else if(validInput == false) {
			 errorLabel.setText("ERROR - Illegal Assembly Format");
				hexInstruction.setText("");
				binaryInstruction.setText("");
			
		}else if(validInputD == false) {
			 errorLabel.setText("ERROR - Illegal Value For D");
				hexInstruction.setText("");
				binaryInstruction.setText("");
				
		}else if(validInputX == false) {
			errorLabel.setText("ERROR - Illegal Value For X");
				hexInstruction.setText("");
				binaryInstruction.setText("");
				
		}else if(validNumber == false) {
			 errorLabel.setText("ERROR - Illegal Number");
				hexInstruction.setText("");
				binaryInstruction.setText("");
			
		}
	}
	

	void decodeBin() {
		
		boolean validBinary = true;
		boolean validBinaryLength = true;
		boolean validOpCode = true;
		boolean validVInput = true;
		boolean validRvalue = true;
		boolean validXvalue = true;
		boolean validDvalue = true;
		
		int opCode;
		int register;
		int indexRegister;
		int displacement;
		int value;
		String opCodeString = "";
		
		String varBinString = binaryInstruction.getText();
		
		//BINARY STRING TO HEX
		//check if binary string length is equal to 32
		// if != 32 give an error
		if (varBinString.length() == 32) {
			validBinaryLength = true;
		}else
			validBinaryLength = false;
		
		long varBinLong = 0;
		
		//parse the string into varBinLong
		//if incorrect value is entered catch exception and return an error
		try {
			varBinLong = Long.parseLong(varBinString, 2);
			
		}catch (NumberFormatException e) {
			validBinary = false;
		}
		int varBinInt = (int)varBinLong;
		hexInstruction.setText(displayIntAsHex(varBinInt));
				
		//BINARY STRING TO ASSEMBLY
		opCode = (varBinInt & 0xFF000000);
		register = ((varBinInt & 0x00F00000)>>20);                       
	    	                        
		indexRegister = (varBinInt & 0b00000000000011100000000000000000);
		
		displacement = varBinInt & (0b00000000000000011111111111111111);
		value = (varBinInt & 0x000FFFFF);
		
		//check if opCode, register and value are valid
		//if valid print result else produce error message
		if((opCode) == (0b00100010<<24)) {
			opCodeString = "LI";
		
			if((register >= 0) & (register <= 15)){
				if(((value >= -524288) && (value <= 524287))) {
					assemblerInstruction.setText(opCodeString + "," + register + " " + value);

				}else
					validVInput = false;
				
			}else {
				validRvalue = false;
			}
			
			//check if opCode, register, displacement and index register values are valid
			//if valid print result else produce error message		
		}else if((opCode == ((0b00110010<<24)) | (opCode == (0b10110010<<24)))) {
			opCodeString = "LW";
			String asterisk = "";
			
			if(opCode != (0b00110010<<24)) {
				asterisk = "*";
			}

			if((register >= 0) & (register <= 15)){
				if(((displacement >= 0) && (displacement <= 131071))) {
					
					if(((indexRegister >= (0b1<<17)) & (indexRegister <= (0b111<<17)))) {
						assemblerInstruction.setText(opCodeString + "," + register + " " + asterisk + displacement + "," + (indexRegister>>17));
						
					}else if((indexRegister == 0)) {
						assemblerInstruction.setText(opCodeString + "," + register + " " + asterisk + displacement);
						
					}else
						validXvalue = false;

				}else
					validDvalue = false;
				
			}else {
				
				validRvalue = false;
			}
			
			//check if opCode, register, displacement and index register values are valid
			//if valid print result else produce error message		
		}else if((opCode == ((0b00110000<<24)) | (opCode == (0b10110000<<24)))) {
			opCodeString = "AW";
			String asterisk = "";
			
			if(opCode != (0b00110000<<24)) {
				asterisk = "*";
			}

			if((register >= 0) & (register <= 15)){
				if(((displacement >= 0) && (displacement <= 131071))) {
					
					if(((indexRegister >= (0b1<<17)) & (indexRegister <= (0b111<<17)))) {
						assemblerInstruction.setText(opCodeString + "," + register + " " + asterisk + displacement + "," + (indexRegister>>17));
						
					}else if((indexRegister == 0)) {
						assemblerInstruction.setText(opCodeString + "," + register + " " + asterisk + displacement);
						
					}else
						validXvalue = false;

				}else
					validDvalue = false;
				
			}else {
				
				validRvalue = false;
			}
			
			//check if opCode, register, displacement and index register values are valid
			//if valid print result else produce error message			
		}else if((opCode == ((0b00110101<<24)) | (opCode == (0b10110101<<24)))) {
			opCodeString = "STW";
			String asterisk = "";
			
			if(opCode != (0b00110101<<24)) {
				asterisk = "*";
			}

			if((register >= 0) & (register <= 15)){
				if(((displacement >= 0) && (displacement <= 131071))) {
					
					if(((indexRegister >= (0b1<<17)) & (indexRegister <= (0b111<<17)))) {
						assemblerInstruction.setText(opCodeString + "," + register + " " + asterisk + displacement + "," + (indexRegister>>17));
						
					}else if((indexRegister == 0)) {
						assemblerInstruction.setText(opCodeString + "," + register + " " + asterisk + displacement);
						
					}else
						validXvalue = false;

				}else
					validDvalue = false;
				
			}else {
				
				validRvalue = false;
			}
			
		}else
			validOpCode = false;
		
		//Different error types and error messages
		if(validBinary == false){
			errorLabel.setText("ERROR - Illegal Binary Number");
			hexInstruction.setText("");
			assemblerInstruction.setText("");
		}else if(validBinaryLength == false) {
			errorLabel.setText("ERROR - Binary must be 32 digits");
			hexInstruction.setText("");
			assemblerInstruction.setText("");
		}else if(validOpCode == false) {
			errorLabel.setText("ERROR - Illegal op code");
			hexInstruction.setText("");
			assemblerInstruction.setText("");
		}else if(validRvalue == false) {
			errorLabel.setText("ERROR - Illegal r value");
			hexInstruction.setText("");
			assemblerInstruction.setText("");
		}else if(validXvalue == false) {
			errorLabel.setText("ERROR - Illegal x value");
			hexInstruction.setText("");
			assemblerInstruction.setText("");
		}else if(validVInput == false) {
			errorLabel.setText("ERROR - Illegal v value in LI");
			hexInstruction.setText("");
			assemblerInstruction.setText("");
			
		}else if(validDvalue == false) {
			errorLabel.setText("ERROR - Illegal d value");
			hexInstruction.setText("");
			assemblerInstruction.setText("");
		}
		
	}

	void decodeHex() {
		
		boolean validHex = true;
		boolean validHexLength = true;
		boolean validOpCode = true;
		boolean validVInput = true;
		boolean validRvalue = true;
		boolean validXvalue = true;
		boolean validDvalue = true;
		
		int opCode;
		int register;
		int indexRegister;
		int displacement;
		int value;
		String opCodeString = "";
		
		String varHexString = hexInstruction.getText();
		
		//HEX String to Binary
		//check if hex string is equal to 8
		// if != 8 give an error
		if (varHexString.length() == 8) {
			validHexLength = true;
		}else
			validHexLength = false;
		
		long varHexLong = 0;
		
		//parse the string into varBinLong
		//if incorrect value is entered catch exception and return an error
		try {
		 varHexLong = Long.parseLong(varHexString, 16);
			
		}catch (NumberFormatException e) {
			validHex = false;
		}
		int varHexInt = (int)varHexLong;
		binaryInstruction.setText(displayIntAsBinary(varHexInt));
				
		//HEX STRING TO ASSEMBLY
		opCode = (varHexInt & 0xFF000000);
		register = ((varHexInt & 0x00F00000)>>20);                       
	    	                        
		indexRegister = (varHexInt & 0b00000000000011100000000000000000);
		
		displacement = varHexInt & (0b00000000000000011111111111111111);
		value = (varHexInt & 0x000FFFFF);
		
		//check if opCode, register, value are valid
		//if valid print result else produce error message
		if((opCode) == (0b00100010<<24)) {
			opCodeString = "LI";
			
			if((register >= 0) & (register <= 15)){
				if(((value >= -524288) && (value <= 524287))) {
					assemblerInstruction.setText(opCodeString + "," + register + " " + value);

				}else
					validVInput = false;
				
			}else {
				validRvalue = false;
			}

			//check if opCode, register, displacement and index register values are valid
			//if valid print result else produce error message				
		}else if((opCode == ((0b00110010<<24)) | (opCode == (0b10110010<<24)))) {
			opCodeString = "LW";
			String asterisk = "";
			
			if(opCode != (0b00110010<<24)) {
				asterisk = "*";
			}

			if((register >= 0) & (register <= 15)){
				if(((displacement >= 0) && (displacement <= 131071))) {
					
					if(((indexRegister >= (0b1<<17)) & (indexRegister <= (0b111<<17)))) {
						assemblerInstruction.setText(opCodeString + "," + register + " " + asterisk + displacement + "," + (indexRegister>>17));
						
					}else if((indexRegister == 0)) {
						assemblerInstruction.setText(opCodeString + "," + register + " " + asterisk + displacement);
						
					}else
						validXvalue = false;

				}else
					validDvalue = false;
				
			}else {
				
				validRvalue = false;
			}

			//check if opCode, register, displacement and index register values are valid
			//if valid print result else produce error message	
		}else if((opCode == ((0b00110000<<24)) | (opCode == (0b10110000<<24)))) {
			opCodeString = "AW";
			String asterisk = "";
			
			if(opCode != (0b00110000<<24)) {
				asterisk = "*";
			}

			if((register >= 0) & (register <= 15)){
				if(((displacement >= 0) && (displacement <= 131071))) {
					
					if(((indexRegister >= (0b1<<17)) & (indexRegister <= (0b111<<17)))) {
						assemblerInstruction.setText(opCodeString + "," + register + " " + asterisk + displacement + "," + (indexRegister>>17));
						
					}else if((indexRegister == 0)) {
						assemblerInstruction.setText(opCodeString + "," + register + " " + asterisk + displacement);
						
					}else
						validXvalue = false;

				}else
					validDvalue = false;
				
			}else {
				
				validRvalue = false;
			}

			//check if opCode, register, displacement and index register values are valid
			//if valid print result else produce error message
		}else if((opCode == ((0b00110101<<24)) | (opCode == (0b10110101<<24)))) {
			opCodeString = "STW";
			String asterisk = "";
			
			if(opCode != (0b00110101<<24)) {
				asterisk = "*";
			}

			if((register >= 0) & (register <= 15)){
				if(((displacement >= 0) && (displacement <= 131071))) {
					
					if(((indexRegister >= (0b1<<17)) & (indexRegister <= (0b111<<17)))) {
						assemblerInstruction.setText(opCodeString + "," + register + " " + asterisk + displacement + "," + (indexRegister>>17));
						
					}else if((indexRegister == 0)) {
						assemblerInstruction.setText(opCodeString + "," + register + " " + asterisk + displacement);
						
					}else
						validXvalue = false;

				}else
					validDvalue = false;
				
			}else {
				
				validRvalue = false;
			}
		
		}else
			validOpCode = false;
		
		//Different error types and error messages
		if(validHex == false){
			errorLabel.setText("ERROR - Illegal Hex Number");
			hexInstruction.setText("");
			assemblerInstruction.setText("");
			binaryInstruction.setText("");
		}else if(validHexLength == false) {
			errorLabel.setText("ERROR - Hex must be 8 digits");
			hexInstruction.setText("");
			assemblerInstruction.setText("");
		}else if(validOpCode == false) {
			errorLabel.setText("ERROR - Illegal op code");
			assemblerInstruction.setText("");
		}else if(validRvalue == false) {
			errorLabel.setText("ERROR - Illegal r value");
			hexInstruction.setText("");
			assemblerInstruction.setText("");
		}else if(validXvalue == false) {
			errorLabel.setText("ERROR - Illegal x value");
			hexInstruction.setText("");
			assemblerInstruction.setText("");
		}else if(validVInput == false) {
			errorLabel.setText("ERROR - Illegal v value in LI");
			hexInstruction.setText("");
			assemblerInstruction.setText("");
			
		}else if(validDvalue == false) {
			errorLabel.setText("ERROR - Illegal d value");
			hexInstruction.setText("");
			assemblerInstruction.setText("");
		}
		
	}
}