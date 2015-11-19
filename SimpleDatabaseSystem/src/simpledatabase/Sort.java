package simpledatabase;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.rits.cloning.Cloner;


public class Sort extends Operator{
	
	private ArrayList<Attribute> newAttributeList;
	private String orderPredicate;
	ArrayList<Tuple> tuplesResult;
	private int count = 0;

	
	public Sort(Operator child, String orderPredicate){
		this.child = child;
		this.orderPredicate = orderPredicate;
		newAttributeList = new ArrayList<Attribute>();
		tuplesResult = new ArrayList<Tuple>();
		
	}
	
	
	/**
     * The function is used to return the sorted tuple
     * @return tuple
     */
	@Override
	public Tuple next(){
		if (tuplesResult.isEmpty()){
			Tuple tuple1 = child.next();
			Cloner cloner = new Cloner();
			Tuple tuple2 = cloner.deepClone(tuple1);
			while(tuple1 != null){
				tuplesResult.add(tuple2);
				tuple1 = child.next();
				tuple2 = cloner.deepClone(tuple1);
			}
		}
		if (count == tuplesResult.size()){return null;}
		int i = 0;
		for(i = 0; i < tuplesResult.get(0).getAttributeList().size(); i++){
			if(tuplesResult.get(0).getAttributeName(i).equals(orderPredicate)){
				break;
			}
		}
		ArrayList<String> list = new ArrayList<String>();
		for(Tuple t : tuplesResult){
			String str = String.valueOf(t.getAttributeValue(i));
			list.add(str);
		}
		
		Collections.sort(list, new Comparator<String>() {
	        @Override
	        public int compare(String str1, String str2) {
	            return str1.compareTo(str2);
	        }
	    });
		
		int j = 0;
		for(j = 0; j < tuplesResult.size(); j++){
			if(String.valueOf(tuplesResult.get(j).getAttributeValue(i)).equals(list.get(count))){
				count++;
				break;
			}
		}
		return tuplesResult.get(j);
	}
	
	/**
     * The function is used to get the attribute list of the tuple
     * @return attribute list
     */
	public ArrayList<Attribute> getAttributeList(){
		return child.getAttributeList();
	}

}