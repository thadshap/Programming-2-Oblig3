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
        Label headline = new Label("Cardgame");
        headline.setFont(Font.font("Cambay",30));
        headline.setTranslateX(170);
        headline.setTranslateY(-50);

        //amount of cards title
        Label amountOfCardsTitle = new Label("Amount of cards:");
        amountOfCardsTitle.setFont(Font.font("Cambay",14));
        amountOfCardsTitle.setTranslateX(450);

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
        Label sumOfTheFacesTitle = new Label("Sum of the faces:");
        sumOfTheFacesTitle.setFont(Font.font("Cambay",13));
        sumOfTheFacesTitle.setTranslateX(100);
        sumOfTheFacesTitle.setTranslateY(250);

        //cards of hearts title
        Label cardsOfHeartsTitle = new Label("Cards of hearts:");
        cardsOfHeartsTitle.setFont(Font.font("Cambay",13));
        cardsOfHeartsTitle.setTranslateX(318);
        cardsOfHeartsTitle.setTranslateY(250);

        //flush title
        Label flushTitle = new Label("Flush:");
        flushTitle.setFont(Font.font("Cambay",13));
        flushTitle.setTranslateX(161);
        flushTitle.setTranslateY(280);

        //queens of spades title
        Label queenOfSpadesTitle = new Label("Queens of spades (S12):");
        queenOfSpadesTitle.setFont(Font.font("Cambay",13));
        queenOfSpadesTitle.setTranslateX(270);
        queenOfSpadesTitle.setTranslateY(280);

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