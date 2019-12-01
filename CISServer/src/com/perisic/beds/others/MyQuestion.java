package com.perisic.beds.others;
/**
 * MyQuestion class interact with DB questions.
 * 
 * @author Kavindi De Silva
 *
 */

public class MyQuestion {
	private int quizID;
	private String quiz;
	private int answerID;
	public int getQuizID() {
		return quizID;
	}
	public void setQuizID(int quizID) {
		this.quizID = quizID;
	}
	public String getQuiz() {
		return quiz;
	}
	public void setQuiz(String quiz) {
		this.quiz = quiz;
	}
	public int getAnswerID() {
		return answerID;
	}
	public void setAnswerID(int answerID) {
		this.answerID = answerID;
	}
	
}
