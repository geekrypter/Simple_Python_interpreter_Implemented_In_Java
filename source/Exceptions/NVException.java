package myexception;

public class NVException extends Exception
	{
	private String variable;
	public NVException(String givenVariable)
	{
		variable=givenVariable;
	}
	public void Message()
		{
		System.out.println("Variable : "+"' "+variable+" '"+"does not exist!");
		}
	}
