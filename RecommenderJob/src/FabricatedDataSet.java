import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class FabricatedDataSet {

	String productFile = "products1.csv";
	String orderFile = "Sales.csv";
	
	String writeFile = "FilteredSales.csv";
	
	List<String> products = new ArrayList<String>();
	Map<String, String> prodMap = new HashMap<String, String>();
	
	int MAX_PROD_COUNT = 50;
	
	public FabricatedDataSet() {
	}
	
	public void readProductList() {
		BufferedReader br = null;
		try {
			String line;
			br = new BufferedReader(new FileReader(productFile));

			for (int i = 0; i < MAX_PROD_COUNT; i++) {
				line = br.readLine();
				products.add(line);
				prodMap.put(line, line);
			}
			
			int i = 0;
			while ((line = br.readLine()) != null) {
				prodMap.put(line, products.get((i++) % MAX_PROD_COUNT));
			}
						
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
	
	
	public void processFile(){
		BufferedReader br = null;
		String line;
		try {
			br = new BufferedReader(new FileReader(orderFile));
			
			File file = new File(writeFile);
			if (!file.exists()) {
				file.createNewFile();
			}
			
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			
			
			while ((line = br.readLine()) != null) {
				
				String prod[] = line.split("\\|");
				
				try{
					String newline = line.replace(prod[17], prodMap.get(prod[17]));
					bw.write(newline + "\n");
				} catch (Exception e) {
					System.out.println(line);
				}
			}
			
			bw.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		
	}
	
	public static void main(String[] args) {
		new FabricatedDataSet(){{readProductList(); processFile();}};
	}
}
