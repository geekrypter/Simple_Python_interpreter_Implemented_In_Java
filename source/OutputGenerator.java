package myoutputgenerator;
import java.util.*;
import myvariablestorage.*;
import myevaluatestring.*;
import myexception.*;
//This is used for providing the output
public class OutputGenerator
	{
	private VariableStorage vs = new VariableStorage();
	
	public static boolean isOperator(char givenOperator)
		{
		if(givenOperator=='*' || givenOperator=='/' || givenOperator=='+' || givenOperator=='-')
			return true;
		return false;
		}
	// This method spilts the input into various parts into an ArrayList for computing the output
	public void splitString(String givenInput) throws IVSTException,NVException,DZException,CNTASGNException
		{
		String input=givenInput;
		String temp="";
		ArrayList<String> expressions = new ArrayList<String>();
		int flag=0,flag1=0,flag2=0;
		for(int i=0;i<input.length();i++)
			{
			if(input.charAt(i)=='(')
				{
				int check=1;
				while(check!=0)
					{
					temp+=input.charAt(i);
					i++;
					if(input.charAt(i)=='(')
						check++;
					else if(input.charAt(i)==')')
						check--;
					}
				temp+=input.charAt(i);
				}
			else if(input.charAt(i)=='*' || input.charAt(i)== '/' || input.charAt(i)=='=')
				{
				expressions.add(temp);
				if(input.charAt(i)=='*')
					{
					expressions.add("*");
					flag++;
					}
				else
					{
					if(input.charAt(i)=='/')
						{
						expressions.add("/");
						flag++;
						}
					else
						{
						if(flag > 0)
							throw new CNTASGNException();
						expressions.add("=");
						}
					}
				temp="";
				}
			else if((input.charAt(i)=='-'|| input.charAt(i)=='+') && i!=0 && input.charAt(i-1)!='(' && !isOperator(input.charAt(i-1)) && input.charAt(i-1)!='=' )
				{
				expressions.add(temp);
				if(input.charAt(i)=='+')
					{
					expressions.add("+");
					flag++;
					}
				else
					{
					expressions.add("-");
					flag++;
					}
				temp="";
				}
			else if(input.charAt(i)=='-' || input.charAt(i)=='+')
				{
				flag++;
				temp+='0';
				temp+=input.charAt(i);
				}
			else if(input.charAt(i)!='(' && input.charAt(i)!= ')')
				{
				temp+=input.charAt(i);
				}
			if(isOperator(input.charAt(i)))
				flag1++;
			}
		if(isOperator(input.charAt(0)))
			flag2=1;
		if(flag1+flag2==input.length()+1)
			throw new IVSTException();
		expressions.add(temp);
		formExpressionString(expressions);
		}
	//This method-forExpressionString: if equal to is present then updates the ArrayList with RHS of the express else : expands the found variables and solve the expression
	private void formExpressionString(ArrayList<String> expressions) throws NVException,DZException
		{
		int newVariable=0,variableFound=0;
		String variableName="";
		//Checking for new variable or variable to be updated
		if(expressions.size()>2 && expressions.get(1)=="=")
			{
			variableName=expressions.get(0);
			int i=0;
			for(String exp : expressions)
				{
				if(i>1)
					{
					expressions.set(i-2,expressions.get(i));
					}
				i++;
				}
			expressions.remove(expressions.size()-1);
			expressions.remove(expressions.size()-1);
			newVariable=1;
			}
		int i=0;
		//expand the variables in the expression with numeric values
		for(String exp : expressions)
			{
			String temp="";
			String foundName="";
			variableFound=0;
			for(int j=0;j<exp.length();j++)
				{
				if((exp.charAt(j)>='a' && exp.charAt(j)<='z') || (exp.charAt(j)>='A' && exp.charAt(j)<='Z'))
					variableFound=1;
				}
			if(variableFound==1)
				{
				int tempVariableFound=0;
				for(int j=0;j<exp.length();j++)
					{
					if(isOperator(exp.charAt(j)) || exp.charAt(j)==')')
						{
						for(int k=0;k<foundName.length();k++)
							{
							if((foundName.charAt(k)>='a' && foundName.charAt(k)<='z' )|| (foundName.charAt(k)>='A' && foundName.charAt(k)<='Z'))
								tempVariableFound=1;
							}
						if(tempVariableFound==1)
							{
							if(vs.retValue(foundName)==null)
								{
								throw new NVException(foundName);
								}
							temp+=Double.toString(vs.retValue(foundName));
							foundName="";
							tempVariableFound=0;
							}
						else
							{
							temp+=foundName;
							foundName="";
							tempVariableFound=0;
							}
						temp+=exp.charAt(j);
						}
					else if(exp.charAt(j)!='(')
						{
						foundName+=exp.charAt(j);	
						}
					else if(exp.charAt(j)=='(')
						temp+="(";
					}
				for(int k=0;k<foundName.length();k++)
					{
					if((foundName.charAt(k)>='a' && foundName.charAt(k)<='z' )|| (foundName.charAt(k)>='A' && foundName.charAt(k)<='Z'))
						tempVariableFound=1;
					}
				if(tempVariableFound==1)
					{
					if(vs.retValue(foundName)==null)
						{
						throw new NVException(foundName);
						}	
					temp+=Double.toString(vs.retValue(foundName));
					foundName="";
					tempVariableFound=0;
					}
				else
					{
					temp+=foundName;
					foundName="";
					tempVariableFound=0;
					}
				expressions.set(i,temp);
				}
			i++;
			}
		i=0;
		//Combines the ArrayList into one single expression String
		for(String exp : expressions)
			{
			String temp="";
			int add=0;
			for(int j=0;j<exp.length();j++)
				{
				if(add!=0 && (isOperator(exp.charAt(j))|| exp.charAt(j)==')'))
					{
					temp+=")";
					temp+=exp.charAt(j);
					add--;
					}
				else if(exp.charAt(j)=='-' && exp.length()!=1 && (j==0 || exp.charAt(j-1)=='(' || isOperator(exp.charAt(j-1))))
					{
					temp+="(0-";
					add++;
					}
				else if(exp.charAt(j)=='+' && exp.length()!=1 && (j==0  || exp.charAt(j-1)=='(' || isOperator(exp.charAt(j-1))))
					{
					temp+="(0+";
					add++;
					}
				else
					{
					temp+=exp.charAt(j);
					}
				}
			while(add!=0)
				{
				temp+=")";
				add--;
				}
			expressions.set(i,temp);
			i++;
			}
		String evalString="";
		for(String exp : expressions)
			{
			if(exp.length()==1)
				evalString+=exp;
			else
				{
				evalString+="(";
				evalString+=exp;
				evalString+=")";
				}
			}	
		if(newVariable==0)
			System.out.println(EvaluateString.evaluateString(evalString));
		else
			{
			vs.addVariable(variableName,EvaluateString.evaluateString(evalString));
			}
		}
	}
