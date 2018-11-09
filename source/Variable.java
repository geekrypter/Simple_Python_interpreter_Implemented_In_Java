package myvariable;
//This class stores the information of a variable
public class Variable
	{
	private String variableName;
	private Double variableValue;
	public void setVariableName(String givenName)
		{
		variableName=givenName;
		}
	public void setVariableValue(Double givenValue)
		{
		variableValue=givenValue;
		}
	public String getVariableName()
		{
		return variableName;
		}
	public Double getVariableValue()
		{
		return variableValue;
		}
	}
