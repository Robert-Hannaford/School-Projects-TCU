/*	Program:		 Lab 2
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
import java.util.*;

public class Lab2 extends JFrame implements ActionListener {
	JButton open = new JButton("Next Program");
	JTextArea result = new JTextArea(20,40);
	JLabel errors = new JLabel();
	JScrollPane scroller = new JScrollPane();
	
	public Lab2() {
		setLayout(new java.awt.FlowLayout());
		setSize(500,430);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		add(open); open.addActionListener(this);
		scroller.getViewport().add(result);
		add(scroller);
		add(errors);
	}
	
	public void actionPerformed(ActionEvent evt) {
		result.setText("");	//clear TextArea for next program
		errors.setText("");
		processProgram();
	}
	
	public static void main(String[] args) {
		Lab2 display = new Lab2();
		display.setVisible(true);
	}
	
	String getFileName() {
		JFileChooser fc = new JFileChooser();
		int returnVal = fc.showOpenDialog(this);
		if (returnVal == JFileChooser.APPROVE_OPTION)
			return fc.getSelectedFile().getPath();
		else
			return null;
	}
	
/************************************************************************/
/* Put your implementation of the processProgram method here.           */
/* Use the getFileName method to allow the user to select a program.    */
/* Then simulate the execution of that program.                         */
/* You may add any other methods that you think are appropriate.        */
/* However, you should not change anything in the code that I have      */
/* written.                                                             */
/************************************************************************/

	void processProgram() {
		
		//create and initialize Array Lists, lineNum, error and fileName
	    ArrayList<String> variables = new ArrayList<String>();
	    ArrayList<Double> values = new ArrayList<Double>();
	    int lineNum = 0;
	    boolean error = false;
	    String fileName = getFileName();
	    
// if fileName is not null, create Buffered reader in and String line.
// While there are more lines add to the lineNum and create a StringTokenizer tok
// that will have a space as its delimiter
// while tok has more tokens create String w continue into conditional statements
	    if (fileName != null) {
	        try {
	            BufferedReader in = new BufferedReader(new FileReader(fileName));
	            String line;

	            while ((line = in.readLine()) != null) {
	                lineNum++;
	                StringTokenizer tok = new StringTokenizer(line, " ");
	                
	                while (tok.hasMoreTokens()) {
	                    String w = tok.nextToken();

//If w is "PRINT" and there are more tokens try to print the next token if it exists in variables array list
//Otherwise produce an error
	                    if(w.equals("PRINT")&& tok.hasMoreTokens()){
	                        String variableToPrint = tok.nextToken();
	                        try {
	                            if(variables.contains(variableToPrint)) {
	                                result.append(String.format("%.2f", values.get(variables.indexOf(variableToPrint))) + "\n");
	                                
	                            } else {
	                                error = true; 
	                                errors.setText("In line " + lineNum + " " + variableToPrint + " is undefined");
	                            }
	                            
	                        }catch(NoSuchElementException e){
	                            error = true; 
	                            errors.setText("In line " + lineNum + " " + variableToPrint + " is undefined");
	                        }
	                    }else if(w.equals("PRINT") && !(tok.hasMoreTokens())){
	                    	errors.setText("END is not last statement");

//if w is "GOTO" and next token is a valid integer 
//iterate through the lines until you reach the desired lineNum
//then continue running that line and the lines that follow
//else return error
	                    } else if(w.equals("GOTO") && tok.hasMoreTokens()) { 
	                        try {
	                            lineNum = Integer.parseInt(tok.nextToken());
	                            in.close();
	                            in = new BufferedReader(new FileReader(fileName));

	                            for(int i = 1; i<lineNum; i++) {
	                                in.readLine();
	                            }

	                            line = in.readLine();
	                            tok = new StringTokenizer(line, " ");

	                        } catch(NumberFormatException e) {
	                            error = true; // Invalid line number
	                            errors.setText("In line " + lineNum + " " + "Invalid line number");
	                        } catch(IOException e) {
	                            error = true; // Error reading file
	                            errors.setText("Error reading file");
	                        }
	                        
	                        
//If w is "END" close the file     
	                    }else if(w.equals("END")){
	                    	in.close();

//If w is "IF" and more tokens exist go into simple statements which were already explained above.
//Otherwise produce an error
	                    }else if(w.equals("IF") && tok.hasMoreTokens()) {
	                    String varToken = tok.nextToken();
	                    
	                    if(variables.contains(varToken)) {
	                    	if(tok.hasMoreTokens()) {
	                    		String varIS = tok.nextToken();
	                    		if(varIS.equals("IS")) {
	                    			if(tok.hasMoreTokens()) {	
	                    			String valueOrConstant = tok.nextToken();  
	                    			double varValue = 0;
	                    			
	                    			if(variables.contains(valueOrConstant)) {
	                    				varValue = values.get(variables.indexOf(valueOrConstant));
	       
	                    			}else if(!(variables.contains(valueOrConstant))){
	                    				try {
	                    				varValue = Double.parseDouble(valueOrConstant);
	                    			
	                    			}catch(NumberFormatException e) {
	                    				errors.setText("In line " + lineNum + " illegal statement");
	                    			}
	                    			}
	            	                        if(((values.get(variables.indexOf(varToken))) == (varValue)) &&(tok.hasMoreTokens())){
	            	                        	String thenT = tok.nextToken();
	            	                        	if(thenT.equals("THEN")) {
	            	                        		if(tok.hasMoreTokens()) {
	            	                        			String simpleStatementWord = tok.nextToken();
	            	                        			
	            	                        			//If simpleStatementWord is "PRINT" and there are more tokens try to print the next token if it exists in variables array list
	            	                        			//Otherwise produce an error
	            	                        			   if(simpleStatementWord.equals("PRINT")&& tok.hasMoreTokens()){  
	            	               	                        String variableToPrint = tok.nextToken();
	            	               	                        try {
	            	               	                            if(variables.contains(variableToPrint)) {
	            	               	                                result.append(String.format("%.2f", values.get(variables.indexOf(variableToPrint))) + "\n");
	            	               	                            } else {
	            	               	                                error = true; 
	            	               	                                errors.setText("In line " + lineNum + " " + variableToPrint + " is undefined");
	            	               	                            }
	            	               	                        }catch(NoSuchElementException e){
	            	               	                            error = true;  
	            	               	                            errors.setText("In line " + lineNum + " " + variableToPrint + " is undefined");
	            	               	                        }
	            	               	                        
	            	               	                   //if simpleStatementWord is "GOTO" and next token is a valid integer 
	            	               	                   //iterate through the lines until you reach the desired lineNum
	            	               	                   //then continue running that line and the lines that follow
	            	               	                   //else return error
	            	                        			   }else if(simpleStatementWord.equals("GOTO") && tok.hasMoreTokens()) { 
	            	               	                        try {
	            	            	                            lineNum = Integer.parseInt(tok.nextToken());
	            	            	                            in.close();
	            	            	                            in = new BufferedReader(new FileReader(fileName));
	            	            	                            for(int i = 1; i<lineNum; i++) {
	            	            	                                in.readLine();
	            	            	                            }

	            	            	                            line = in.readLine();
	            	            	                            tok = new StringTokenizer(line, " ");

	            	            	                        } catch(NumberFormatException e) {
	            	            	                            error = true;
	            	            	                            errors.setText("In line " + lineNum + " " + "Invalid line number");
	            	            	                        } catch(IOException e) {
	            	            	                            error = true; 
	            	            	                            errors.setText("Error reading file");
	            	            	                        }
	            	                        			   }else
	            	                        				// variable is being assigned for the first time
	            	                        				   if(!variables.contains(simpleStatementWord) && tok.hasMoreTokens() && tok.nextToken().equals("=")) {
	            	                   	                    	if(tok.hasMoreTokens()) {
	            	                   		                        
	            	                   		                        variables.add(simpleStatementWord);
	            	                   		                        double value1 = evaluateExpression(tok, variables, values);
	            	                   		                        values.add(value1);
	            	                   		                       
	            	                   		                    	}else
	            	                   		                    		errors.setText("In line " + lineNum + " illegal statement");
	            	                   	                    	
	            	                   	                    	
	            	                   	                     // variable already exists, update its value
	            	                   		                    } else if (variables.contains(simpleStatementWord) && tok.hasMoreTokens() && tok.nextToken().equals("=")) {
	            	                   		                    	if(tok.hasMoreTokens()) {
	            	                   		                    	
	            	                   		                        double value1 = evaluateExpression(tok, variables, values);
	            	                   		                        values.set(variables.indexOf(simpleStatementWord), value1);

	            	                   		                        //set error label to the exact error that occurs             		                        
	            	                   		                    	}else
	            	                   		                    		errors.setText("In line " + lineNum + " illegal statement");
	            	                   		                    	
	            	                        		}else
	            	                        			errors.setText("");
	            	                        		
	            	                        	}else
	            	                        		errors.setText("In line " + lineNum + " missing THEN keyword");
	            	                        }else
               	                               
	            	                        	errors.setText("In line " + lineNum + " illegal statement");	
	                    			}else
       	                                
	                    				errors.setText("In line " + lineNum + " illegal statement");
	                    		}else
	                    			errors.setText("In line " + lineNum + " missing IS keyword");
	                    	}else
	                    		errors.setText("In line " + lineNum + " illegal statement");	                    		
	                    }else
	                    	errors.setText("In line " + lineNum + " " + varToken + " is undefined");
	                    	
	                    }  	
	                    
	                 // variable is being assigned for the first time	
	                    }else if (!variables.contains(w) && tok.hasMoreTokens() && tok.nextToken().equals("=")) {
	                    	if(tok.hasMoreTokens()) {
	                        
	                        variables.add(w);
	                        double value = evaluateExpression(tok, variables, values);
	                        values.add(value);
	                        
	                    	}else
	                    		errors.setText("END is not last statement");
	                    	
	                    	
	                        // variable already exists, update its value	
	                    } else if (variables.contains(w) && tok.hasMoreTokens() && tok.nextToken().equals("=")) {
	                    	if(tok.hasMoreTokens()) {
	                        double value = evaluateExpression(tok, variables, values);
	                        values.set(variables.indexOf(w), value);
	                    	}else
	                    		errors.setText("END is not last statement");

	                    }else 
	                    	errors.setText("In line " + lineNum + " illegal variable");
	                }
	            }
	            //close the input file
	            //catch exception
	            in.close();
	        } catch (IOException e) {
	        	System.out.print("");
	        	
	        }
	        
	    }
	    //no errors
	    if (error == true) {
	        result.setText(""); 
	    }
	}
		
		
	//evaluate expression method
	private double evaluateExpression(StringTokenizer tok, ArrayList<String> variables, ArrayList<Double> values) {
	
	    double result = 0;
	    boolean firstTerm = true;
	    String operator = "";

	    while (tok.hasMoreTokens()) {
	        String token = tok.nextToken();
//check if the the token is an operator
	        if (isOperator(token)) {
	            operator = token;
	            
//If the token is not an operator and is the first term and exists in variables arrayList get the value 	
//Otherwise if token doesn't exist in variables arrayList try parsing it into a double
//set boolean firstTerm to false
	        } else if (firstTerm) {
	            if (variables.contains(token)) {
	                result = values.get(variables.indexOf(token));
	            } else {
	                result = Double.parseDouble(token);
	            }
	            firstTerm = false;
	            
//If the token is not an operator and not the first term and exists in variables arrayList get the value 
//Otherwise if token doesn't exist in variables arrayList and isn't the first term try parsing it into a double

	        } else {
	            double term = 0;
	            if (variables.contains(token)) {
	                term = values.get(variables.indexOf(token));
	            } else {
	                term = Double.parseDouble(token);
	            }

//switch statement performs arithmetic operation on the result variable based on what the operator is
	            switch (operator) {
	                case "+":
	                    result += term;
	                    break;
	                case "-":
	                    result -= term;
	                    break;
	                case "*":
	                    result *= term;
	                    break;
	                case "/":
	                    result /= term;
	                    break;
	            }
	        }
	    }
//returns the result
	    return result;
	}
//The isOperator checks whether the operator is a +, -, *, / and returns only if true otherwise it returns false.
	private boolean isOperator(String token) {
	    return token.equals("+") || token.equals("-") || token.equals("*") || token.equals("/");
	}
}