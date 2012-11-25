package in.brightpush.resque.aim.synchronization;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.taskqueue.Taskqueue;
import com.google.api.services.taskqueue.TaskqueueRequest;
import com.google.api.services.taskqueue.TaskqueueRequestInitializer;
import com.google.api.services.taskqueue.TaskqueueScopes;
import com.google.api.services.taskqueue.model.Task;
import com.google.api.services.taskqueue.model.Tasks;
import com.google.api.client.util.Base64;

public class Main {

	static Logger log = Logger.getLogger(Main.class.getName());
	
	private static final String project = "s~aim-api";
	private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
	private static final JsonFactory JSON_FACTORY = new JacksonFactory();
	  
	/**
	 * @param args
	 * @throws IOException 
	 * @throws GeneralSecurityException 
	 */
	public static void main(String[] args) throws GeneralSecurityException, IOException {
		log.info("Starting TaskQueue <tokens-create / tokens-update> processor");
		
		Properties properties = new Properties();
		properties.load(Main.class.getClassLoader().getResourceAsStream("googleapi.properties"));
		
        // service account credentials
        GoogleCredential credential = new GoogleCredential.Builder().setTransport(HTTP_TRANSPORT)
            .setJsonFactory(JSON_FACTORY)
            .setServiceAccountScopes(TaskqueueScopes.TASKQUEUE)
            .setServiceAccountPrivateKeyFromP12File(new File(properties.getProperty("privatekeyfile")))
            .setServiceAccountId(properties.getProperty("serviceaccountid"))
            //.setServiceAccountUser("santthosh@appinmap.com")
            .build();
        
        // set up Taskqueue
        Taskqueue taskQueue = new Taskqueue.Builder(
            HTTP_TRANSPORT, JSON_FACTORY, credential).setApplicationName("aim-token-synchronization/1.0")
            .setTaskqueueRequestInitializer(new TaskqueueRequestInitializer() {
              @Override
              public void initializeTaskqueueRequest(TaskqueueRequest<?> request) {
                request.setPrettyPrint(true);
              }
            }).build();
        
        com.google.api.services.taskqueue.model.TaskQueue createQueue = getQueue(taskQueue,"tokens-create");
        
        java.util.List<Task> tasksList = null;
        do {
            Tasks tasks = getLeasedTasks(taskQueue,"tokens-create",10);
            tasksList = tasks.getItems();
            if(tasksList != null) {
                for(Task task : tasksList) {
                    executeTask(task);	
                    //deleteTask(taskQueue,task,"tokens-create");
                }		
            }
        } while(tasksList != null);
        
        com.google.api.services.taskqueue.model.TaskQueue updateQueue = getQueue(taskQueue,"tokens-update");
        
        java.util.List<Task> updateTasksList = null;
        do {
            Tasks tasks = getLeasedTasks(taskQueue,"tokens-update",10);
            updateTasksList = tasks.getItems();
            if(tasksList != null) {
                for(Task task : updateTasksList) {
                    executeTask(task);	
                    //deleteTask(taskQueue,task,"tokens-create");
                }		
            }
        } while(tasksList != null);
        
		log.info("Finishing TaskQueue <tokens-create / tokens-update> processor");
	}
	
	  /**
	   * Method that sends a get request to get the queue.
	   * 
	   * @param taskQueue The task queue that should be used to get the queue from.
	   * @return {@link com.google.api.services.taskqueue.model.TaskQueue}
	   * @throws IOException if the request fails.
	   */
	  private static com.google.api.services.taskqueue.model.TaskQueue getQueue(Taskqueue taskQueue,String taskQueueName)
	      throws IOException {
	    Taskqueue.Taskqueues.Get request = taskQueue.taskqueues().get(project, taskQueueName);
	    request.setGetStats(true);
	    return request.execute();
	  }

	  /**
	   * Method that sends a lease request to the specified task queue.
	   * 
	   * @param taskQueue The task queue that should be used to lease tasks from.
	   * @return {@link Tasks}
	   * @throws IOException if the request fails.
	   */
	  private static Tasks getLeasedTasks(Taskqueue taskQueue,String taskQueueName,int count) throws IOException {
	    Taskqueue.Tasks.Lease leaseRequest =
	        taskQueue.tasks().lease(project, taskQueueName, count, 60);
	    return leaseRequest.execute();
	  }

	  /**
	   * This method actually performs the desired work on tasks. It can make use of payload of the
	   * task. By default, we are just printing the payload of the leased task.
	   * 
	   * @param task The task that should be executed.
	   */
	  private static void executeTask(Task task) {
		String payloadBase64 = task.getPayloadBase64();
		
		if(payloadBase64 != null) {
			byte[] payload = Base64.decodeBase64(payloadBase64.getBytes());
			log.info("Payload for the task:" + new String(payload));
		}
	  }

	  /**
	   * Method that sends a delete request for the specified task object to the taskqueue service.
	   * 
	   * @param taskQueue The task queue the specified task lies in.
	   * @param task The task that should be deleted.
	   * @throws IOException if the request fails
	   */
	  private static void deleteTask(Taskqueue taskQueue, Task task,String taskQueueName) throws IOException {
	    Taskqueue.Tasks.Delete request =
	        taskQueue.tasks().delete(project, taskQueueName, task.getId());
	    request.execute();
	  }
}
