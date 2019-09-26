package camunda.drools;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class ProcessVariablesDelegate implements JavaDelegate {

	public void execute(DelegateExecution execution) throws Exception {
		System.out.println("Process Variable ...");
		execution.setVariable("author","echasin");
		execution.setVariable("email","echasin@innvosolutions.com");
		execution.setVariable("content","This is a demo tweet - John Doe");
		
		String author = (String) execution.getVariable("author");
		String email = (String) execution.getVariable("email");
		String content = (String) execution.getVariable("content");
		
		System.out.println("ProcesVariablesDelegate-Process variables: " + author + " - " + email + " - " + content);

	}
}