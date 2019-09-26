package camunda.drools;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

/**
 * Rejection is just done via a sysout since the fox platform does not support the Mail Task of Activiti!
 * See https://app.camunda.com/confluence/display/foxUserGuide/Activiti+5+Support for details.
 * 
 * Use your own Mail mechanisms for this or use your application server features. 
 */
public class ApprovalNotificationDelegate implements JavaDelegate {
  
  public void execute(DelegateExecution execution) throws Exception {
    Tweet tweet = (Tweet) execution.getVariable("tweet");
    
    System.out.println("Hi!\n\n"
           + "Your Tweet has been approved.\n\n"
           //+ "Original content: " + tweet.getContent() + "\n\n"
           );
  }
  
}
