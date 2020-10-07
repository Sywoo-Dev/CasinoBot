package fr.sywoo.casino.objs;

import java.util.HashMap;

public class CoinsFlipObject {

	private int mise;
	private long owner;
	
	static HashMap<Long, CoinsFlipObject> values = new HashMap<>();
	
	public CoinsFlipObject(long msg, long owner, int mise) {
		this.mise = mise;
		this.owner = owner;
		this.mise = mise;
		values.put(msg, this);
		System.out.println("CF mis en ligne !");
	}
	
	public int getMise() {
		return mise;
	}

	public void setMise(int mise) {
		this.mise = mise;
	}

	public long getOwner() {
		return owner;
	}

	public void setOwner(long owner) {
		this.owner = owner;
	}

	public static CoinsFlipObject getCF(long msg_id) {
		if(values.containsKey(msg_id)) {
			return values.get(msg_id);
		}
		return null;
	}
}
