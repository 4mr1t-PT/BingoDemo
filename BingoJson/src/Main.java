import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public class Main {
	
	public static void main(String[] args) throws InterruptedException, ExecutionException, TimeoutException, IOException {
		//JsonSubscriber sub = new JsonSubscriber();
		Thread.sleep(1000);
		JsonPublisher pub = new JsonPublisher();
		pub.newGame();
	}
}
