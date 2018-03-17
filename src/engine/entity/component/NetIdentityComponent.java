package engine.entity.component;

import java.io.Serializable;

import engine.entity.Entity;
import engine.net.common_net.NetworkManager;

public class NetIdentityComponent extends AbstractComponent implements Serializable
{
	private int networkId;
	
	public NetIdentityComponent(int id, NetworkManager netManager)
	{
		networkId = id;
		//register
		netManager.registerNetEntity(this);
	}
	
	public void setNetworkId(int id)
	{
		this.networkId = id;
	}
	
	public int getNetworkId()
	{
		return this.networkId;
	}

	@Override
	public void update()
	{
	}	
}
