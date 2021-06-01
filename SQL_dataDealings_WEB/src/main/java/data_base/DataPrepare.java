package data_base;

import java.util.ArrayList;

public class DataPrepare {
	
public ArrayList<ArrayList<String>> collectMostRelevantResults(ResultBean bearBean, ResultBean masterBean,ResultBean ikeaBean){
		
		ArrayList<ArrayList<String>> mostRelevant = new ArrayList<ArrayList<String>>();
		
		
		mostRelevant.addAll(bearBean.getFirstResults());
		mostRelevant.addAll(masterBean.getFirstResults());
		mostRelevant.addAll(ikeaBean.getFirstResults());
		return mostRelevant;

		
	}

public ArrayList<ArrayList<String>> collectRelevantResults(ResultBean bearBean, ResultBean masterBean,ResultBean ikeaBean){
	
	
	ArrayList<ArrayList<String>> relevant = new ArrayList<ArrayList<String>>();

	
	relevant.addAll(bearBean.secondResults);
	relevant.addAll(masterBean.secondResults);
	relevant.addAll(ikeaBean.secondResults);
	return relevant;
	
	
	
	
}

public ArrayList<ArrayList<String>> collectLeastRelevantResults(ResultBean bearBean, ResultBean masterBean,ResultBean ikeaBean){
	

	ArrayList<ArrayList<String>> leastRelevant = new ArrayList<ArrayList<String>>();
	
	
	
	leastRelevant.addAll(bearBean.getThirdResults());
	leastRelevant.addAll(masterBean.getThirdResults());
	leastRelevant.addAll(ikeaBean.getThirdResults());
	return leastRelevant;
	
	
	
}

}
