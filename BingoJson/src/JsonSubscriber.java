
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

import com.pushtechnology.diffusion.client.Diffusion;
import com.pushtechnology.diffusion.client.content.Content;
import com.pushtechnology.diffusion.client.features.Topics;
import com.pushtechnology.diffusion.client.features.Topics.ValueStream;
import com.pushtechnology.diffusion.client.session.Session;
import com.pushtechnology.diffusion.client.topics.details.TopicSpecification;
import com.pushtechnology.diffusion.datatype.json.JSON;
import com.pushtechnology.diffusion.datatype.json.JSONDataType;
import com.pushtechnology.repackaged.jackson.core.type.TypeReference;
import com.pushtechnology.repackaged.jackson.dataformat.cbor.CBORParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.cbor.CBORFactory;

public class JsonSubscriber {

	private  List<String> bingoStringsPlayed;
	private boolean isValueBingo;
	private String current;
	//private  BingoBoard board = new BingoBoard();
	private final CBORFactory cborFactory = new CBORFactory();
	private final JSONDataType jsonDataType = Diffusion.dataTypes().json();
	private final ObjectMapper mapper = new ObjectMapper();

	public JsonSubscriber() throws InterruptedException, ExecutionException, TimeoutException, IOException {

		final Session session = Diffusion.sessions().open("ws://localhost:8080");

		final Topics topics = session.feature(Topics.class);
		topics.addStream(">foo/test", JSON.class, new ValueStreamPrintLn());

		topics.subscribe("foo/test").whenComplete((voidResult, exception) -> {
			if (exception != null) {
				System.out.print("Error");
			}
		});

		bingoStringsPlayed = new ArrayList<>();
		isValueBingo = false;
		current = "";
		System.out.println("New subscriber created");

	}

	private final class ValueStreamPrintLn extends ValueStream.Default<JSON> {
		private final TypeReference<List<String>> typeReference = new TypeReference<List<String>>() {
		};

		@Override
		public void onValue(String topicPath, TopicSpecification specification, JSON oldValue, JSON newValue) {
			try {
				// Use the third-party Jackson library to parse the newValue's
				// binary representation and convert to a map
				final com.fasterxml.jackson.dataformat.cbor.CBORParser parser = cborFactory
						.createParser(newValue.asInputStream());
				
				final List<String> outputArray = mapper.readValue(parser, List.class);
				// final List<String> outputArray2 = mapper.readValue(newValue.toJsonString(),
				// List.class);
				System.out.println("output = " + newValue);
				System.out.println("output =1 " + outputArray);
				// System.out.println("output =2 " + outputArray2);
				System.out.println("last index" + outputArray.get(outputArray.size() - 1));
				//board.bingoStringsCalled.add(outputArray.get(outputArray.size() - 1));
				current = outputArray.get(outputArray.size() - 1);
				//.newValuboard.updateFields(current);
				if(current.length()>5) {
					if (current.substring(0,6).equals("winner")) {
						isValueBingo = true;
					}
				}
				// board.bingoStringsCalled.add(outputArray.get(outputArray.size()));

			} catch (IOException ex) {
				ex.printStackTrace();
			}

			// bingoStringsPlayed.add(newValue);

			// System.out.println(newValue);
			// System.out.println(board);
			// if( board!= null) {

			// System.out.println("newValue: " + newValue);
		}
	}

	public List<String> bingoStringsPlayed() {
		return bingoStringsPlayed;
	}

	public boolean getIsValueBingo() {
		return isValueBingo;
	}

	public String getCurrentValue() {
		return current;
	}
	
	public void restartSub() {
		this.bingoStringsPlayed = new ArrayList<>();
		this.isValueBingo = false;
		this.current = "";	
	}
}
