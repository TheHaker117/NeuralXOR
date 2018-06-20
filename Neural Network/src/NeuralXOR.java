import java.util.List;

import javax.swing.JTextArea;
import javax.swing.SwingWorker;

import com.heatonresearch.book.introneuralnet.neural.feedforward.FeedforwardLayer;
import com.heatonresearch.book.introneuralnet.neural.feedforward.FeedforwardNetwork;
import com.heatonresearch.book.introneuralnet.neural.feedforward.train.Train;
import com.heatonresearch.book.introneuralnet.neural.feedforward.train.backpropagation.Backpropagation;

public class NeuralXOR {

	private double XOR_INPUT[][] = {{0.0, 0.0}, {1.0, 0.0}, {0.0, 1.0}, {1.0, 1.0}};
	private double XOR_IDEAL[][] = {{0.0}, {1.0}, {1.0}, {0.0}};

	private JTextArea ta_log = null;
	private SwingWorker worker = null;

	private FeedforwardNetwork network;

	public NeuralXOR(JTextArea ta_log){
		this.ta_log = ta_log;
	}

	public void train(int epochs, double error){

		worker = new SwingWorker<Void, String>(){

			@Override
			protected Void doInBackground() throws Exception{

				network = new FeedforwardNetwork();
				network.addLayer(new FeedforwardLayer(2));
				network.addLayer(new FeedforwardLayer(3));
				network.addLayer(new FeedforwardLayer(1));
				network.reset();

				// Train the neural network
				Train train = new Backpropagation(network, XOR_INPUT, XOR_IDEAL, 0.7, 0.9);

				int epoch = 1;

				do{
					train.iteration();
					publish("\nEpoch #" + epoch + " Error:" + train.getError());
					epoch++;
				} while((epoch <= epochs) && (train.getError() > error));

				return null;
			}

			@Override
			protected void process(List<String> chunks){

				for(String chunk : chunks)
					ta_log.append(chunk);
			}
		};

		worker.execute();

	}

	public void test(int val00, int val01){

		worker = new SwingWorker<Void, String>(){

			@Override
			protected Void doInBackground() throws Exception{
				// test the neural network
				publish("\n\n>>> Neural Network Results:");

				double actual[] = network.computeOutputs(new double[] {val00, val01});
				publish("\n\n" + val00 + " XOR " + val01 + " = " + actual[0] + "\nCorrect = "
						+ logicalXOR(val00, val01));

				return null;
			}

			@Override
			protected void process(List<String> chunks){

				for(String chunk : chunks)
					ta_log.append(chunk);
			}
		};

		worker.execute();
	}

	private int logicalXOR(int val00, int val01){
		boolean x, y;

		if(val00 == 1)
			x = true;
		else
			x = false;

		if(val01 == 1)
			y = true;
		else
			y = false;

		return ((x || y) && !(x && y)) ? 1 : 0;
	}

}
