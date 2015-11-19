package simpledatabase;
import java.util.ArrayList;

public class Projection extends Operator{
	
	ArrayList<Attribute> newAttributeList;
	private String attributePredicate;


	public Projection(Operator child, String attributePredicate){
		
		this.attributePredicate = attributePredicate;
		this.child = child;
		newAttributeList = new ArrayList<Attribute>();
		
	}
	
	
	/**
     * Return the data of the selected attribute as tuple format
     * @return tuple
     */
	@Override
	public Tuple next(){
		Tuple tuple = child.next();
		if(tuple == null){return null;}
		newAttributeList.clear();
		int i = 0;
		int j = tuple.getAttributeList().size();
		for(i = 0; i < j; i++){
			if(tuple.getAttributeName(i).equals(attributePredicate)){
				newAttributeList.add(0,tuple.getAttributeList().get(i));
			}
		}
		Tuple newTuple = new Tuple(newAttributeList);	
		return newTuple;
	}
		

	
	/**
     * The function is used to get the attribute list of the tuple
     * @return attribute list
     */
	public ArrayList<Attribute> getAttributeList(){
		return child.getAttributeList();
	}

}