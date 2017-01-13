package automaton;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import blocks.AST;
import blocks.Defns;
import blocks.StateDefn;
import lexicalpkg.AnotherParser;
import lexicalpkg.Lexer;
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
		
		TextArea textArea = new TextArea(getCodeFromFile(slash+"test"+slash+"gol.txt"));
		textArea.setMinHeight(2 * HEIGHT / 3);
		textArea.setMinWidth(WIDTH / 3);
		
		btnNew.setOnMouseClicked(event -> {
			
			newWorld(Integer.parseInt(txfSize.getText()));
		});
		
		btnLoadRules.setOnMouseClicked(event -> {
			try {
				String slash=File.separator;
				Lexer l = new Lexer();
				AnotherParser p = new AnotherParser();
				loadRules(p.parse(textArea.getText()), world.getSize().x);
			} catch (Exception e) {
				e.printStackTrace();
			}
			slider.setMax(world.stateDefns.size() - 1);
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

	private void loadRules(AST ast, int size)
	{	
		Defns defns = (Defns) ast;
		this.world = new World(size, size, defns);
		draw();
	}

	private void draw()
	{
		g.clearRect(0, 0, WIDTH, HEIGHT);
		int gridSize = world.getSize().x;
		double cellSize = canvas.getHeight() / gridSize;
		for(int y = 0; y < gridSize; y++) {
			for(int x = 0; x < gridSize; x++) {
				g.setFill(Paint.valueOf(((StateDefn) world.stateDefns.get(world.getCell(x, y))).color.data));
				g.fillRect(Math.floor(x*cellSize), Math.floor(y*cellSize), Math.ceil(cellSize), Math.ceil(cellSize));
			}
		}
		for(int i = 0; i < world.stateDefns.size(); i++) {
			g.setFill(Paint.valueOf(((StateDefn) world.stateDefns.get(i)).color.data));
			g.fillRect(Math.floor(i*cellSize), 0, Math.ceil(cellSize), Math.ceil(cellSize));
		}
	}
	
	private void newWorld(int size)
	{
		try {
			Lexer l = new Lexer();
			AnotherParser p = new AnotherParser();
			loadRules(p.parse(getCodeFromFile(slash+"test"+slash+"gol.txt")), size);
			draw();
		} catch (Exception e) {
			e.printStackTrace();
		}
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