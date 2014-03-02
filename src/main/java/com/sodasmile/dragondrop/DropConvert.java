package com.sodasmile.dragondrop;

import java.io.File;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class DropConvert extends Application {

    private ToPdfConverter toPdfConverter;

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Drag files to be converted to PDF here");

        toPdfConverter = new ToPdfConverter();

        Group root = new Group();
        Scene scene = new Scene(root, 551, 400);
        scene.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                Dragboard dragboard = event.getDragboard();
                if (dragboard.hasFiles()) {
                    event.acceptTransferModes(TransferMode.COPY);
                } else {
                    event.consume();
                }
            }
        });

        final TextArea textArea = new TextArea();

        // Dropping over surface
        scene.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                Dragboard dragboard = event.getDragboard();
                boolean success = false;
                if (dragboard.hasFiles()) {
                    success = true;
                    for (File file : dragboard.getFiles()) {
                        ToPdfConverter.ConvertResult convertResult = toPdfConverter.convertToPdf(file);
                        System.out.println("Someone dropped the file: " + file);
                        textArea.setText(textArea.getText() + convertResult.getMessage() + "\n");
                    }
                }
                event.setDropCompleted(success);
                event.consume();
            }
        });

        final Text infotext = new Text(50, 100, "Drag DOC-files here");
        infotext.setScaleX(2.0);
        infotext.setScaleY(2.0);

        root.getChildren().add(infotext);
        root.getChildren().add(textArea);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
