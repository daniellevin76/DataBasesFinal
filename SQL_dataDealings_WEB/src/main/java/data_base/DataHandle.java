package data_base;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashSet;

public class DataHandle {
	
	static Connection conn = null;
	static PreparedStatement stmt = null;
	
	String bearTable = "bearbase";
	String masterTable = "masterscplist";
	String ikeaTable = "ikea_names";
	
	
	
	
	public DataHandle(String searchWord, ResultBean bearBean, ResultBean masterBean, ResultBean ikeaBean) {
		
		connectSQL();
		
		ArrayList<String> searchWords = getWordsSubstring(searchWord);
		ArrayList<String> bearQueries = getQueries(bearTable, searchWords, DataHandle.getHeaders(bearTable));
		ArrayList<String> masterQueries = getQueries(masterTable, searchWords, DataHandle.getHeaders(masterTable));
		ArrayList<String> ikeaQueries = getQueries(ikeaTable, searchWords, DataHandle.getHeaders(ikeaTable));
		
		System.out.println(ikeaQueries);
		
		getResultsOneTable(bearQueries, bearTable, bearBean);
		getResultsOneTable(masterQueries, masterTable, masterBean);
		getResultsOneTable(ikeaQueries, ikeaTable, ikeaBean);
	}

	

	public ArrayList<String> getWordsSubstring(String inputString) {
		ArrayList<String> substrArr = new ArrayList<String>();

		substrArr.add(inputString);

		String[] words = inputString.split(" ");
		

		for (String word: words) {
			if (!word.equals(inputString)) {
				substrArr.add(word);
			}

		}

		for (String word: words) {
			for (int i = 1; i < word.length() - 2; i++) {
				String substr1 = word.substring(0, word.length() - i);
				// String substr2 = word.substring(word.length()-3-i, word.length());
				substrArr.add(substr1);

			}

		}
		return substrArr;
	}
	
	

	public static boolean connectSQL() {

		try {

			// driver setup
			Class.forName("com.mysql.cj.jdbc.Driver");

		} catch (Exception ex) {
			System.out.println("Exception Driver " + ex.getMessage());

			return false;
		}

		try {

			conn = DriverManager.getConnection("jdbc:mysql://localhost:3308/final_project", "root", "");
			if (conn != null) {

			}

			return true;

		} catch (SQLException ex) {

			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
			return false;
		}

	}

	public static ArrayList<String> getHeaders(String tableName) {
		ArrayList<String> headers = new ArrayList<String>();
		String requestHeaders = "SELECT COLUMN_NAME, ORDINAL_POSITION FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = ? ";
		ResultSet rs = null;

		try {
			stmt = conn.prepareStatement(requestHeaders);
			stmt.setString(1, tableName);
			rs = stmt.executeQuery();
			while (rs.next()) {

				headers.add(rs.getString("COLUMN_NAME"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Catching error" + e.getMessage());
		}
		return headers;
	}

	public ArrayList<String> getQueries(String tableName, ArrayList<String> searchWords, ArrayList<String> headers) {

		ArrayList<String> queries = new ArrayList<String>();

		for (String searchWord : searchWords) {
			String rQ = "SELECT * FROM " + tableName + " WHERE ";

			for (int i = 0; i < headers.size(); i++) {
				rQ = rQ + tableName + "." + headers.get(i) + " LIKE " + "'%" + searchWord + "%'";

				if (i < headers.size() - 1) {
					rQ = rQ + " OR ";
				}
			}
			queries.add(rQ);

		}

		return queries;

	}

	public static ArrayList<ArrayList<String>> getResultFromQuery(String query, ArrayList<String> headers) {

		ArrayList<ArrayList<String>> searchResult = new ArrayList<ArrayList<String>>();
		// searchResult.add(headers);
		ResultSet rs = null;

		try {

			stmt = conn.prepareStatement(query);
			rs = stmt.executeQuery();
			
			while (rs.next()) {
				

				ArrayList<String> resultRow = new ArrayList<String>();
				for (int i = 1; i <= headers.size(); i++) {
					resultRow.add(rs.getString(i));
					resultRow.remove(null);
				}

				searchResult.add(resultRow);

				// rs.close();
				// conn.endRequest();
				// conn.close();

			}

			return searchResult;

		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
		return null;

	}

	public static ArrayList<ArrayList<String>> removeDuplicatesFromResults(ArrayList<ArrayList<String>> searchResults) {

		// System.out.println("searchResult: " + searchResults);
		LinkedHashSet<ArrayList<String>> hashSet = new LinkedHashSet<>(searchResults);

		ArrayList<ArrayList<String>> listWithoutDuplicates = new ArrayList<ArrayList<String>>(hashSet);

		return listWithoutDuplicates;
	}

	public void getResultsOneTable(ArrayList<String> queries, String tableName, ResultBean resultBean) {

		ArrayList<ArrayList<String>> firstResults = new ArrayList<ArrayList<String>>();
		ArrayList<ArrayList<String>> secondResults = new ArrayList<ArrayList<String>>();
		ArrayList<ArrayList<String>> thirdResults = new ArrayList<ArrayList<String>>();

		for (int i = 0; i < queries.size(); i++) {

			
			ArrayList<ArrayList<String>> result;
			try {
				result = getResultFromQuery(queries.get(i), DataHandle.getHeaders(tableName));
				System.out.println("Result in query:" + result);
			

			if (i == 0 && !result.isEmpty()) {
				firstResults.addAll(result);
				resultBean.setFirstResults(firstResults);
				// System.out.println(resultBean.getFirstResults());
			}
			if (i == 1 && !result.isEmpty()) {

				// remove all results we got in the first results
				result.removeAll(firstResults);
				secondResults.addAll(result);
				resultBean.setSecondResults(secondResults);
			}
			if (!result.isEmpty()) {
				// remove all results we get from the first and the second results
				result.removeAll(firstResults);
				result.removeAll(secondResults);
				thirdResults.addAll(result);
				resultBean.setThirdResults(thirdResults);
			}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println(e.getMessage());
			}

		}

	}
	
	
	
	

}
