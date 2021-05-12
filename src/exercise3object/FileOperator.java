package exercise3object;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;
import java.util.TreeMap;

public class FileOperator {
	private String fileName;
	public FileOperator(String fileName) {
		this.fileName = fileName;
	}
	public TreeMap<String, String[]> SeafoodMenuRead() throws IOException{
		FileReader fr = null;
		BufferedReader br = null;
		TreeMap<String,String[]> ts;
		ts = new TreeMap<>();

		try {
			File file = new File(fileName);
			fr = new FileReader(file);
			br = new BufferedReader(fr);
			String menu = "";
			while((menu = br.readLine()) != null) {
				String[] content = new String[3];
				StringTokenizer st = new StringTokenizer(menu,",");
				int i = 0;
				while(st.hasMoreTokens()) {
					content[i] = st.nextToken();
					i++;
				}
				ts.put(content[0], content);
			}
		} catch(Exception e) {
			e.printStackTrace();
			System.out.println("メニューの読み込みに失敗しました。");
		}
		return ts;
	}
}
