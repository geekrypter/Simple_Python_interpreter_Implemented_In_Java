package myvariablestorage;
import java.util.*;
import myvariable.*;
//This class is used for storing objects of Variable class
public class VariableStorage
	{
	//Stores the objects of Variable class
	private ArrayList<Variable> variableObjects= new ArrayList<Variable>();
	//Updates variable if present else adds a new variabe
	public void addVariable(String givenVariableName,Double givenVariableValue)
		{
		for(Variable v:variableObjects)
			{
			if(givenVariableName.equals(v.getVariableName()))
				{
				v.setVariableValue(givenVariableValue);
				return;
				}
			}
		Variable v = new Variable();
		v.setVariableName(givenVariableName);
		v.setVariableValue(givenVariableValue);
		variableObjects.add(v);
		}
	//Returns the value of the variable
	public Double retValue(String givenVariableName)
		{
		for(Variable v : variableObjects)
			{
			if(givenVariableName.equals(v.getVariableName()))
				return v.getVariableValue();
			}
		return null;
		}
	}
