package com.perisic.beds.barChart;
/**
 * Display Graphical user interface according to overall customer satisfaction answers.
 * @author Nuwantha Fernando
 *
 */

import com.perisic.beds.predefinemethods.RemoteConnection;
import com.perisic.beds.rmiinterface.RemoteQuestions;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

public class BarChartSample extends Application{
	final static String yes = "Yes";
    final static String no = "No";
    final static String maybe = "Maybe";
    public RemoteQuestions connection;

	@Override
	public void start(Stage stage) throws Exception {

//Set Frame Title
		stage.setTitle("Client Satisfication Summery");
		
		connection = RemoteConnection.remoteConnect();
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final BarChart<String,Number> bc = 
            new BarChart<String,Number>(xAxis,yAxis);
        
//Set Ttiles        
        bc.setTitle("Question 1 Answer Summery");
        xAxis.setLabel("Answer");       
        yAxis.setLabel("Value");
 
//Fill Bar Titles
        XYChart.Series series1 = new XYChart.Series();
        String quiz="question_"+"0";
        int yesCount=connection.getAnswerCountYesNoQuiz(quiz, "Yes");
        int noCount=connection.getAnswerCountYesNoQuiz(quiz, "No");
        int MaybeCount=connection.getAnswerCountYesNoQuiz(quiz, "Maybe");
             
//Generate Bar's According to the database Values
        series1.getData().add(new XYChart.Data(yes, yesCount));
        series1.getData().add(new XYChart.Data(no, noCount));
        series1.getData().add(new XYChart.Data(maybe, MaybeCount));     
        
        Scene scene  = new Scene(bc,800,600);
        bc.getData().addAll(series1);
        stage.setScene(scene);
        stage.show();

//Main method implementation
	}
public static void main(String[] args) {
	launch(args);
	}

}
