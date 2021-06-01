package data_base;

import java.util.ArrayList;

public class ResultBean {
	ArrayList<ArrayList<String>> firstResults, secondResults, thirdResults;
	

	public ResultBean() {
		super();
	firstResults = new ArrayList<ArrayList<String>>();
	secondResults = new ArrayList<ArrayList<String>>();
	thirdResults = new ArrayList<ArrayList<String>>();
		
		
	}

	public ArrayList<ArrayList<String>> getFirstResults() {
		return firstResults;
	}

	public void setFirstResults(ArrayList<ArrayList<String>> firstResults) {
		this.firstResults = firstResults;
	}

	public ArrayList<ArrayList<String>> getSecondResults() {
		return secondResults;
	}

	public void setSecondResults(ArrayList<ArrayList<String>> secondResults) {
		this.secondResults = secondResults;
	}


	public ArrayList<ArrayList<String>> getThirdResults() {
		return thirdResults;
	}

	public void setThirdResults(ArrayList<ArrayList<String>> thirdResults) {
		this.thirdResults = thirdResults;
	}

}
