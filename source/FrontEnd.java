package myfrontend;
import mybackend.*;
import java.util.*;

public class FrontEnd 
	{
	//This method scans the input and sends the input to BackEnd class 
	public void implementation()
		{
		BackEnd connector = new BackEnd();
		Scanner scan=new Scanner(System.in);
		String input;
		while(true)
			{
			System.out.println(">>>");
			input= scan.nextLine();
			if(input.equals("stop")||input.equals("exit"))
				break;
			connector.processor(input);
			}
		}
	}
