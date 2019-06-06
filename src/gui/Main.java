package gui;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

public class Main {

	public static void main(String[] args) throws ParseException {
		
		dateCalculator.AniversaryList list;
		if (args.length == 0){
			GUI gui = new GUI();
			gui.setEnabled(true);
			gui.setVisible(true);
		} else {
			if (args.length == 1){
				System.out.println(
						"Usage: " + System.lineSeparator() + 
						"=========" + System.lineSeparator() + 
						"The following parameters could be used:" + System.lineSeparator() + 
						"/date:2015-04-22" + System.lineSeparator() + 
						"/name:Daisys birthday" + System.lineSeparator());
			} else {
				StringBuilder arguments = new StringBuilder();
				for (String arg : args){
					System.out.println(arg);
					arguments.append(arg);
				}
				Map<String, String> parameters = new HashMap<String, String>();
				String[] pairs = arguments.toString().split("/");
				if (pairs.length < 2){
					pairs = arguments.toString().split("\\");
				}
				for (String pair : pairs){
					String key = null;
					String value = null;
					if (pair.contains(":")){
						key = pair.split(":")[0].trim().toLowerCase();
						value = pair.split(":")[1].trim();
					} else {
						if (pair.contains("=")){
							key = pair.split("=")[0].trim().toLowerCase();
							value = pair.split("=")[1].trim();
						}
					}
					if (key != null){
						System.out.println("Run time parameter '" + key + "' set to '" + value + "'.");
						parameters.put(key, value);
					}
					if (parameters.get("name") != null && parameters.get("date") != null){
						try {
							SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
							list = new dateCalculator.AniversaryList(new dateCalculator.DateOfImportance(parameters.get("name"), format.parse(parameters.get("date"))));
							list.PrintList();
						} catch (ParseException e) {
							e.printStackTrace();
						}
					} else {
						System.out.println("Not the right parameters. Try aniversaries.jar /help.");
					}
				}
			}
		}
	}

}
