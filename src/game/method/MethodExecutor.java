package game.method;

import java.util.ArrayList;

public class MethodExecutor
{
	private ArrayList<Method> list;
	
	public MethodExecutor()
	{
		list = new ArrayList<Method>();
	}
	
	public void execute()
	{
		for(Method method : list)
		{
			method.execute();
		}
		
		list.clear();
	}
	
	public void add(Method method)
	{
		list.add(method);
	}
}
