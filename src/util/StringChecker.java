package util;

public class StringChecker {
	public boolean isBlank(String string)
	{
		if(string==null)
		{
			return true;
		}
		else
		{
			for(int i = 0; i < string.length();i++)
			{
				if(string.charAt(i)!=' ')
				{
					return false;
				}
			}
			return true;
		}
	}
}
