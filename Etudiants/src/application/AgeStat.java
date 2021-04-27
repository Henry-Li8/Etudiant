package application;

import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.XYChart;

public class AgeStat {
	
	@FXML
	private BarChart <String, Integer> barChart;
	
	@FXML
	private CategoryAxis xAxis;
	
	private ObservableList<String> intervalAges = FXCollections.observableArrayList();
	
	@FXML
	private void initialize() {
		intervalAges.add("0-10");
		intervalAges.add("10-20");
		intervalAges.add("20-30");
		intervalAges.add("30-40");
		intervalAges.add("40-50");
		intervalAges.add("50-60");
		intervalAges.add("60-70");
		intervalAges.add("70-80");
		intervalAges.add("80-90");
		intervalAges.add("90-100");
		xAxis.setCategories(intervalAges);
	}
	
	public void SetStats (List<Etudiant> etudiants) {
		int[] ageCounter = new int [10];
		
		for (Etudiant e:etudiants) {
			double age = e.getAge();
			
			if (age <= 10)
				ageCounter[0]++;
			else
				if (age<=20)
					ageCounter[1]++;
				else
					if (age<=30)
						ageCounter[2]++;
					else
						if (age<=40)
							ageCounter[3]++;
						else
							if (age<=50)
								ageCounter[4]++;
							else
								if (age<=60)
									ageCounter[5]++;
								else
									if (age<=70)
										ageCounter[6]++;
									else
										if (age<=80)
											ageCounter[7]++;
										else
											if (age<=90)
												ageCounter[8]++;
											else
												if (age<=100)
													ageCounter[9]++;
			
		}
	XYChart.Series<String, Integer> series = new XYChart.Series<>();
	series.setName("Ages");
	for (int i = 0; i<ageCounter.length; i++) {
		series.getData().add(new XYChart.Data<>(intervalAges.get(i), ageCounter[i]));
	}
	barChart.getData().add(series);
	
	
	}
	

}
