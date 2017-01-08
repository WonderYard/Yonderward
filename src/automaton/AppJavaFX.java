package automaton;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lexicalpkg.Lexer;
import lexicalpkg.Parser;
import automaton.Rule;
import automaton.State;
import automaton.World.Neighborhood;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

public class AppJavaFX extends Application
{
	private List<State> states;
	private World world;
	
	private final int WIDTH = 1280;
	private final int HEIGHT = 700;
	private final Canvas canvas = new Canvas(HEIGHT, HEIGHT);
	private GraphicsContext g = canvas.getGraphicsContext2D();
	
	String slash=File.separator;

	@Override
	public void start(Stage mainStage) throws IOException
	{
		BorderPane root = new BorderPane();
		
		Button btnNew = new Button("New");
		TextField txfSize = new TextField("40");
		Button btnLoadRules = new Button("LoadRules");
		Button btnTick = new Button("Tick");
		
		Slider slider = new Slider();
		slider.setMin(0);
		slider.setMax(0);
		slider.setValue(0);
		slider.setMajorTickUnit(1);
		slider.setMinorTickCount(0);
		slider.setSnapToTicks(true);
		slider.setShowTickMarks(true);
		slider.setShowTickLabels(true);
		
		TextArea textArea = new TextArea(getCodeFromFile(slash+"test"+slash+"neon.txt"));
		textArea.setMinHeight(2 * HEIGHT / 3);
		textArea.setMinWidth(WIDTH / 3);
		
		btnNew.setOnMouseClicked(event -> {
			
			newWorld(Integer.parseInt(txfSize.getText()));
			
			try {
				Lexer l = new Lexer(getCodeFromFile(slash+"test"+slash+"neon.txt"));
				Parser p = new Parser(l);
				loadRules(p.body());
			} catch (Exception e) {
				e.printStackTrace();
			}
			slider.setMax(states.size() - 1);
			draw();
		});
		
		btnLoadRules.setOnMouseClicked(event -> {
			try {
				String slash=File.separator;
				Lexer l = new Lexer(textArea.getText());
				Parser p = new Parser(l);
				loadRules(p.body());
			} catch (Exception e) {
				e.printStackTrace();
			}
			slider.setMax(states.size() - 1);
		});
		
		btnTick.setOnMouseClicked(event -> {
			evolve();
			draw();
		});
		
		canvas.setOnMouseClicked(event -> {
			drawCell(event.getX(), event.getY(), slider);
		});
		
		canvas.setOnMouseDragged(event -> {
			drawCell(event.getX(), event.getY(), slider);
		});;
		
		VBox vb = new VBox();
		vb.setPadding(new Insets(10));
		
		HBox hbNew = new HBox();
		hbNew.getChildren().add(btnNew);
		hbNew.getChildren().add(txfSize);
		
		vb.getChildren().add(hbNew);
		vb.getChildren().add(btnTick);
		vb.getChildren().add(slider);
		vb.getChildren().add(textArea);
		vb.getChildren().add(btnLoadRules);
		vb.setMaxWidth(WIDTH / 4);
		root.setLeft(vb);
		root.setCenter(canvas);

		Scene scene = new Scene(root, WIDTH, HEIGHT);
        mainStage.setTitle("Cellular Automata");
        mainStage.setScene(scene);
        mainStage.show();
    }
	
	private void drawCell(double x, double y, Slider slider)
	{
		int gridX = (int) Math.floor(x / canvas.getHeight() * world.getSize().x);
		int gridY = (int) Math.floor(y / canvas.getHeight() * world.getSize().y);
		if(gridY == 0) {
			slider.setValue(gridX);
			return;
		}
		world.setCell(gridX, gridY, (int) slider.getValue());
		draw();
	}

	private void loadRules(List<State> states)
	{
		this.states = states;
		
		this.world = new World(40, 40, states, World.Neighborhood.MOORE.points);
		draw();
	}

	private void draw()
	{
		g.clearRect(0, 0, WIDTH, HEIGHT);
		int gridSize = world.getSize().x;
		double cellSize = canvas.getHeight() / gridSize;
		for(int y = 0; y < gridSize; y++) {
			for(int x = 0; x < gridSize; x++) {
				g.setFill(Paint.valueOf(states.get(world.getCell(x, y)).getColor()));
				g.fillRect(Math.floor(x*cellSize), Math.floor(y*cellSize), Math.ceil(cellSize), Math.ceil(cellSize));
			}
		}
		for(int i = 0; i < states.size(); i++) {
			g.setFill(Paint.valueOf(states.get(i).getColor()));
			g.fillRect(Math.floor(i*cellSize), 0, Math.ceil(cellSize), Math.ceil(cellSize));
		}
	}
	
	private void newWorld(int size)
	{
		world = new World(size, size, new ArrayList<>(), Neighborhood.MOORE.points);
		states = new ArrayList<>();
	}
	
	private void evolve()
	{
		world.evolve();
	}
	
	private String getCodeFromFile(String filename)
	{
		StringBuilder str = new StringBuilder();
		try {
			Files.newBufferedReader(Paths.get(new File(".").getCanonicalPath() + "/"+filename)).lines().forEach(l -> {
				str.append(l);
				str.append("\n");
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return str.toString();
	}
	
	public static void main(String[] args)
	{
		launch(args);
	}
}