package dk.ucn.jgten.persistence.model;

public class GunReplica extends Product{
	
	private double calibre;
	private String material;

	
	
	public GunReplica(int productNumber, String name, int minStock, int reservedQty) {
		super(productNumber, name, minStock, reservedQty);
		
	}
	
}
