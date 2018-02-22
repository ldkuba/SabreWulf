package engine.entity.component;

import java.io.Serializable;

public class NetIdentityComponent extends AbstractComponent implements Serializable
{
	private int networkId;
	
	public NetIdentityComponent(int id)
	{
		networkId = id;
	}
	
	public void setNetworkId(int id)
	{
		this.networkId = id;
	}
	
	public int getNetworkId()
	{
		return this.networkId;
	}
	
	
}
