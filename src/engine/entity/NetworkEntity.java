package engine.entity;

import java.io.Serializable;

import engine.entity.component.NetDataComponent;
import engine.entity.component.NetIdentityComponent;
import engine.entity.component.NetSpriteAnimationComponent;
import engine.entity.component.NetTransformComponent;

public class NetworkEntity implements Serializable
{
	private int packetId;
	
	private NetIdentityComponent netIdentity;
	private NetTransformComponent netTransform;
	private NetDataComponent netData;
	private NetSpriteAnimationComponent netAnimation;
	//add any net components later
	
	public NetworkEntity()
	{
	}
	
	public int getPacketId()
	{
		return packetId;
	}

	public void setPacketId(int packetId)
	{
		this.packetId = packetId;
	}

	public NetIdentityComponent getNetIdentity()
	{
		return netIdentity;
	}
	public void setNetIdentity(NetIdentityComponent netIdentity)
	{
		this.netIdentity = netIdentity;
	}
	public NetTransformComponent getNetTransform()
	{
		return netTransform;
	}
	public void setNetTransform(NetTransformComponent netTransform)
	{
		this.netTransform = netTransform;
	}
	public NetDataComponent getNetData()
	{
		return netData;
	}
	public void setNetData(NetDataComponent netData)
	{
		this.netData = netData;
	}
	public NetSpriteAnimationComponent getNetAnimation()
	{
		return netAnimation;
	}
	public void setNetAnimation(NetSpriteAnimationComponent netAnimation)
	{
		this.netAnimation = netAnimation;
	}
}
