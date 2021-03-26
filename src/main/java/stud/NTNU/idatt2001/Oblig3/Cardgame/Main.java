package stud.NTNU.idatt2001.Oblig3.Cardgame;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import stud.NTNU.idatt2001.Oblig3.Cardgame.exception.WrongInputException;

import java.io.IOException;
import java.util.HashSet;
import java.util.stream.Collectors;

/**
 * JavaFX Main
 */
public class Main extends Application {

    HashSet<PlayingCard> cards;

    //Text format
    private Text styleText (String text, int x, int y, String font, int fontSize){
        Text t = new Text (text);
        t.setFont(Font.font(font, fontSize));
        t.setTranslateX(x);
        t.setTranslateY(y);
        return t;
    }

    //Label format
    private Label styleLabel (String text, int x, int y, String font, int fontSize){
        Label l = new Label (text);
        l.setFont(Font.font(font, fontSize));
        l.setTranslateX(x);
        l.setTranslateY(y);
        return l;
    }

    @Override
    public void start(Stage stage) throws WrongInputException, IOException {
        stage.setTitle("Cardgame");

        GridPane firstPage = new GridPane();

        //box
        FlowPane flowPane = new FlowPane();
        flowPane.setAlignment(Pos.TOP_LEFT);
        flowPane.setStyle("-fx-background-color: lightgrey;");
        flowPane.setTranslateX(100);
        flowPane.setTranslateY(90);
        flowPane.setPrefSize(300,200);

        GridPane gridPane = new GridPane();

        //headline label
        Label headline = styleLabel("Cardgame",170,-50,"Cambay",30);

        //amount of cards title
        Label amountOfCardsTitle = styleLabel("Amount of cards:",450,0,"Cambay",14);


        //amount of cards input
        DeckOfCards deckOfCards = new DeckOfCards();
        TextField amountOfCardsInput = new TextField();
        amountOfCardsInput.setMaxWidth(40);
        amountOfCardsInput.setTranslateX(450);
        amountOfCardsInput.setTranslateY(35);

        ChangeListener<String> changeListener = new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("[\\s]*|[1-9]|[1-4][0-9]|5[0-2]")) {
                    amountOfCardsInput.setText(oldValue);
                }
            }
        };
        amountOfCardsInput.textProperty().addListener(changeListener);

        //check hand button
        Button dealHandButton = new Button("Deal Hand");
        Button checkHandButton = new Button("Check Hand");
        checkHandButton.setTranslateX(450);
        checkHandButton.setTranslateY(120);
        checkHandButton.setVisible(false);
        checkHandButton.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent actionEvent) {
                checkHandButton.setVisible(false);
                CheckHand checkHand = new CheckHand(cards);
                Text hasSpadesOfQueen = new Text ("");
                Text hasNotSpadesOfQueen = new Text("");
                if (checkHand.checkSpadesOfQueen()){
                    hasSpadesOfQueen = styleText("Yes",410,372,"Cambay",13);
                }
                else {
                    hasNotSpadesOfQueen = styleText("No",410,372,"Cambay",13);
                }
                Text hasFlush = new Text ("");
                Text hasNotFlush = new Text("");
                if (checkHand.flush()){
                    hasFlush = styleText("Yes",200,372,"Cambay",13);
                }
                else {
                    hasNotFlush = styleText("No",200,372,"Cambay",13);
                }

                Text getAllHearts = styleText(checkHand.getAllHearts(),410,342,"Cambay",13);

                Text sumOfAllDeckOfHands = styleText(String.valueOf(checkHand.sumOfAllDeckOfHands()),200,342,"Cambay",13);
                gridPane.getChildren().clear();
                gridPane.getChildren().addAll(hasFlush,hasSpadesOfQueen,getAllHearts,sumOfAllDeckOfHands,hasNotFlush,hasNotSpadesOfQueen);
            }
        });

        //deal hand button
        Text text = new Text("");
        dealHandButton.setTranslateX(450);
        dealHandButton.setTranslateY(80);
        dealHandButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(!amountOfCardsInput.getText().equals("")){
                    checkHandButton.setVisible(true);
                    try {
                        cards = deckOfCards.dealHand(Integer.parseInt(amountOfCardsInput.getText()));
                        String result = cards.stream().map(n -> n.getAsString()).collect(Collectors.joining(", "));
                        text.setText(result);
                        text.setFont(Font.font("Cambay",20));
                        text.setFill(Color.WHITE);
                        text.wrappingWidthProperty().bind(flowPane.widthProperty());
                        flowPane.getChildren().add(text);
                    } catch (WrongInputException e) {
                        e.printStackTrace();
                    } catch (Exception exception){
                        exception.getMessage();
                    }
                }
            }
        });

        //sum of the faces title
        Label sumOfTheFacesTitle = styleLabel("Sum of the faces:",100,250,"Cambay",13);

        //cards of hearts title
        Label cardsOfHeartsTitle = styleLabel("Cards of hearts:",318,250,"Cambay",13);

        //flush title
        Label flushTitle = styleLabel("Flush:",161,280,"Cambay",13);

        //queens of spades title
        Label queenOfSpadesTitle = styleLabel("Queens of spades (S12):",270,280,"Cambay",13);

        //window settings
        firstPage.getChildren().addAll(headline,amountOfCardsTitle,amountOfCardsInput,dealHandButton,checkHandButton,sumOfTheFacesTitle,cardsOfHeartsTitle,flushTitle,queenOfSpadesTitle,flowPane, gridPane);
        Scene scene = new Scene(firstPage,670,480);
        stage.setScene(scene);
        stage.show();

    }
    public static void main(String[] args) {
         launch(args);
     }

}