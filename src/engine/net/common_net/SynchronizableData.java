package engine.net.common_net;

import java.io.Serializable;
import java.util.ArrayList;

public class SynchronizableData implements Synchronizable
{
	private ArrayList<Serializable> data;
	
	public SynchronizableData()
	{
		data = new ArrayList<>();
	}
	
	public void addData(Serializable obj)
	{
		data.add(obj);
	}
	
	public ArrayList<Serializable> getData()
	{
		return data;
	}
}
