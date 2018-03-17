package engine.entity.component;

import java.io.Serializable;
import java.util.HashMap;

public class NetDataComponent extends AbstractComponent implements Serializable{

    private HashMap<String, Serializable> data;
    public NetDataComponent(){
        data = new HashMap<String, Serializable>();
    }
    public HashMap<String, Serializable> getAllData(String name){
        return data;
    }
    public Serializable getData(String name){
        return data.get(name);
    }
    public void addData(String name, Serializable information){
        this.data.put(name, information);
    }
    public void removeData(String name, Serializable information){
        this.data.remove(name, information);
    }

    @Override
	public void update()
	{
	}
}
