package camunda.drools;

import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.Map.Entry;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.Expression;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.drools.KnowledgeBase;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatefulKnowledgeSession;




public class DroolsDelegate implements JavaDelegate {

	private Expression drlFile;
	private Expression facts;

	private HashMap<String, Object> factMap = new HashMap<String, Object>();

	public void execute(DelegateExecution execution) throws Exception {
		System.out.println( "Bootstrapping the Rule Engine ..." );	
		
		//Get process variables from Camunda bpnm
		String author = (String) execution.getVariable("author");
		String email = (String) execution.getVariable("email");
		String content = (String) execution.getVariable("content");
		System.out.println( "Process variables: " + author + " - " + email + " - " +content );	
		
		
		String drlFileName = (String) drlFile.getValue(execution);
			
		
		KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
	    kbuilder.add(ResourceFactory.newClassPathResource(drlFileName, getClass()), ResourceType.DRL);  
	    if (kbuilder.hasErrors()) {
	     throw new RuntimeException("Error in drools: " + kbuilder.getErrors().toString());
	    }
	    
		
	    KnowledgeBase knowledgeBase = kbuilder.newKnowledgeBase();
	    StatefulKnowledgeSession workingMemory = knowledgeBase.newStatefulKnowledgeSession();
       
	   
	    if (facts != null) {
	      //Verifying that facts are not null
	      System.out.println( "facts != null: " + (String) facts.getValue(execution))	;
	      // Very easy implementation to fetch the parameters :-) Must be improved
	      // for real live
	      StringTokenizer st = new StringTokenizer((String) facts.getValue(execution), ",");
	      
	      System.out.println( "facts: " + (String) facts.getValue(execution));
	      System.out.println( "drlFile: " + (String) drlFile.getValue(execution));
	      System.out.println( "Token Count: " + st.countTokens());
	      System.out.println( "Token String: " + st.toString());
	      
	      while (st.hasMoreTokens()) {
	        String variableName = st.nextToken().trim();

	        // TODO: Add retrieval of variable and insert it into the working memory
	        Object variable = execution.getVariable(variableName);
	        System.out.println( "variableName: " + variableName);
	        
	        workingMemory.insert(variable);
	        
	       System.out.println( "workingMemory.getFactCount(): " + workingMemory.getFactCount());

	        // remember the variable to update it later in the process context
	        factMap.put(variableName, variable);
	      }
	    }

	  
	    workingMemory.fireAllRules();
	    System.out.println( "workingMemory.fireAllRules()..." );
	   
	    
	    // update variables
	    for (Entry<String, Object> factEntry : factMap.entrySet()) {
	      // update variable in the process variables
	      execution.setVariable(factEntry.getKey(), factEntry.getValue());
	    }
	  
	  }
}