import java.util.List;
import java.util.Map;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.cbor.CBORFactory;
import com.fasterxml.jackson.dataformat.cbor.CBORGenerator;
import com.pushtechnology.diffusion.client.Diffusion;
import com.pushtechnology.diffusion.client.features.control.topics.TopicControl;
import com.pushtechnology.diffusion.client.features.control.topics.TopicUpdateControl;
import com.pushtechnology.diffusion.client.features.control.topics.TopicUpdateControl.Updater.UpdateCallback;
import com.pushtechnology.diffusion.client.features.Topics.FetchStream;
import com.pushtechnology.diffusion.client.session.Session;
import com.pushtechnology.diffusion.client.features.Topics;
import com.pushtechnology.diffusion.datatype.json.JSON;
import com.pushtechnology.diffusion.datatype.json.JSONDataType;
import com.pushtechnology.diffusion.client.topics.details.TopicSpecification;
import com.pushtechnology.diffusion.client.topics.details.TopicType;

public class JsonPublisher {
	private static final Logger LOG = LoggerFactory.getLogger(JsonPublisher.class);
	private static final UpdateCallback.Default UPDATE_CALLBACK = new UpdateCallback.Default();

	private final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
    private static final String ROOT_TOPIC = "foo/test";

	final TopicUpdateControl updateControl;
	final UpdateCallback updateCallback;
	private final CBORFactory cborFactory = new CBORFactory();
    private final JSONDataType jsonDataType = Diffusion.dataTypes().json();
	private static List<String> calledStrings = new ArrayList<>();
	private static List<String> unusedStrings;
	private boolean isBingo = false;
	JsonSubscriber sub;
	// private Subscriber subscriber;

	public JsonPublisher() throws InterruptedException, ExecutionException, TimeoutException, IOException {
		
        cborFactory.setCodec(new ObjectMapper());


		final Session session = Diffusion.sessions().principal("admin").password("password")
				.open("ws://localhost:8080");

		// Get the TopicControl and TopicUpdateControl feature
		final TopicControl topicControl = session.feature(TopicControl.class);
		
		 // Create the root topic that will remove itself when the session closes
        final TopicSpecification specification =
            topicControl.newSpecification(TopicType.JSON).withProperty(
                TopicSpecification.REMOVAL,
                "When no session has '$SessionId is \"" +
                session.getSessionId().toString() +
                "\"' remove '" +
                "?" + ROOT_TOPIC + "//'");
        topicControl.addTopic(ROOT_TOPIC, specification).get(5, TimeUnit.SECONDS);

		updateControl = session.feature(TopicUpdateControl.class);
	

		
		isBingo = false;

		// Update the topic
		updateCallback = new UpdateCallback.Default();
		
		sub = new JsonSubscriber();

	}
	
	public static List<String> generatePossibleBingoStrings() {
		unusedStrings = new ArrayList<>();
		for(int i = 1; i<76; i++) {
			if(i < 16) {
				unusedStrings.add("B"+i);
			} else if(i<=30 && i> 15) {
				unusedStrings.add("I"+i);
			} else if(i<=45 && i>30) {
				unusedStrings.add("N"+i);
			} else if(i<=60 && i>44) {
				unusedStrings.add("G"+i);				
			} else if(i<=75 && i>60) {
				unusedStrings.add("O"+i);
			}
		}
		//System.out.println("size of unusedS");
		return unusedStrings;
	}

	public static List<Integer> generateNumberLists(int min, int max) {
		List<Integer> UnusedNumbers = new ArrayList<>();
		for (int i = min; i <= max; i++) {
			UnusedNumbers.add(i);
		}
		return UnusedNumbers;
	}
	
	public void updateJsonServer() throws InterruptedException {
		while(isBingo == false && !unusedStrings.isEmpty()) {
			int rand_num = randInt(0,unusedStrings.size()-1);
			String bingo = unusedStrings.get(rand_num);
			unusedStrings.remove(rand_num);
		}
	}
	
	public void updateServerWithValue(List<String> value) throws InterruptedException, IOException {
		updateTopic(mapStringtoJson(value));

	}
	
	private JSON mapStringtoJson(List<String> value) throws IOException {
		final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        final CBORGenerator generator = cborFactory.createGenerator(baos);
        generator.writeObject(value);
        return jsonDataType.readValue(baos.toByteArray());
	}
	
	public void newGame() throws InterruptedException, ExecutionException, TimeoutException, IOException {
		System.out.println("trying to start a new game");
		sub.restartSub();
		generatePossibleBingoStrings();
		calledStrings = new ArrayList<>();
		isBingo = false;
		List<String> bingoFinished = new ArrayList<>();
		bingoFinished.add("BingoGameFinished");
		updateTopic(mapToJSON(bingoFinished));
		Thread.sleep(3000);
		updateServer();
	}

	public void updateServer() throws InterruptedException, IOException, ExecutionException, TimeoutException {
		while(isBingo == false && !unusedStrings.isEmpty() && sub.getIsValueBingo() != true) {
			int rand_num = randInt(0,unusedStrings.size()-1);
			String bingo = unusedStrings.get(rand_num);
			unusedStrings.remove(rand_num);
			calledStrings.add(bingo);
			mapToJSON(calledStrings);
			updateTopic(mapToJSON(calledStrings));
			Thread.sleep(5000);
		}
		
		
		if(sub.getIsValueBingo() == true) {
			System.out.println("Bingo Game Finished");
			Thread.sleep(12000);
			newGame();
		}
			Thread.sleep(10000);
			System.out.println("Bingo Game Finished");
			newGame();
		
	}

	

	public int randInt(int min, int max) {
		Random rand = new Random();
		int randomNum = rand.nextInt((max - min) + 1) + min;
		return randomNum;
	}
	
	public void updateTopic(JSON value) throws InterruptedException {
		updateControl.updater().valueUpdater(JSON.class).update("foo/test", value, updateCallback);
		// Thread.sleep(1000);
	}

	public void setBingo(boolean value) {
		isBingo = value;
	}
	
    /**
     * Convert a given map to a JSON object.
     */
    JSON mapToJSON(List<String> values) throws IOException {
        // Use the third-party Jackson library to write out the values map as a
        // CBOR-format binary.
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        final CBORGenerator generator = cborFactory.createGenerator(baos);
        generator.writeObject(values);
        return jsonDataType.readValue(baos.toByteArray());
    }

}
