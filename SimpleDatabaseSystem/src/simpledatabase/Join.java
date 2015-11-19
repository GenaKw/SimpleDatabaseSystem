package simpledatabase;
import java.util.ArrayList;

public class Join extends Operator{

	private ArrayList<Attribute> newAttributeList;
	private String joinPredicate;
	ArrayList<Tuple> tuples1;
	private int rightKey = -1;
	private int leftKey = -1;

	
	//Join Constructor, join fill
	public Join(Operator leftChild, Operator rightChild, String joinPredicate){
		this.leftChild = leftChild;
		this.rightChild = rightChild;
		this.joinPredicate = joinPredicate;
		newAttributeList = new ArrayList<Attribute>();
		tuples1 = new ArrayList<Tuple>();
	}

	
	/**
     * It is used to return a new tuple which is already joined by the common attribute
     * @return the new joined tuple
     */
	//The record after join with two tables
	@Override
	public Tuple next(){
		do{
			Tuple rTuple = rightChild.next();
			if(rTuple == null){return null;}
			newAttributeList.clear();
			
			if (tuples1.isEmpty()){
				Tuple lTuple = leftChild.next();
				while(lTuple != null){
					tuples1.add(lTuple);
					lTuple = leftChild.next();
				}
			}
			
			//finding the key
			findkey:
			if(rightKey == -1 && leftKey == -1){
				for(rightKey = 0; rightKey < rTuple.getAttributeList().size(); rightKey++){
					for(leftKey = 0; leftKey < tuples1.get(0).getAttributeList().size(); leftKey++){
						if(rTuple.getAttributeName(rightKey).equals(tuples1.get(0).getAttributeName(leftKey))){
							break findkey;
						}
					}
					
				}
			}
			
			if (rightKey == rTuple.getAttributeList().size()){
				rightKey = -1; 
				leftKey = -1;
				return null;}
			
			
			for (int i = 0; i < tuples1.size(); i++){
				if(rTuple.getAttributeValue(rightKey).equals(tuples1.get(i).getAttributeValue(leftKey))){
					newAttributeList.addAll(rTuple.getAttributeList());
					newAttributeList.addAll(tuples1.get(i).getAttributeList());
					newAttributeList.remove(rightKey);
					break;
				}
			}
		}while(newAttributeList.isEmpty());
		
		
		Tuple newTuple = new Tuple(newAttributeList);
		return newTuple;
	}
	
	
	
	/**
     * The function is used to get the attribute list of the tuple
     * @return attribute list
     */
	public ArrayList<Attribute> getAttributeList(){
		if(joinPredicate.isEmpty())
			return child.getAttributeList();
		else
			return(newAttributeList);
	}

}