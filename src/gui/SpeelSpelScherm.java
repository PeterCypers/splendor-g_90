package gui;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import domein.DomeinController;
import domein.Kleur;
import dto.SpelVoorwerpDTO;
import dto.SpelerDTO;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import resources.Taal;

public class SpeelSpelScherm extends BorderPane {
	private final DomeinController dc;
	private BorderPane spelbord;
	private HashSet<Kleur> kleurKeuze = new HashSet<>();

	public SpeelSpelScherm(DomeinController dc) {
		this.dc = dc;
		this.spelbord = new BorderPane();

		dc.startNieuwSpel();
		buildGui();
	}

	private void buildGui() {

		this.setStyle("-fx-background-color: #000000;");

		/*--------------CREATE THE BORD--------------*/

		spelbord.setStyle("-fx-background-color: #363200;");

		setAlignment(spelbord, Pos.CENTER);
		spelbord.setMaxWidth(875);
		spelbord.setMaxHeight(800);
		this.setCenter(spelbord);

		playerInfo();
		nobles();
		deckCards();
		developmentCards();
		gems();

		/*--------------RIGHT SIDE--------------*/
		HBox spelerAanBeurtInfo = new HBox();
		StackPane spacer = new StackPane();
		spacer.setMinWidth(256 + 32);
		spelerAanBeurtInfo.getChildren().add(spacer);
		spelerAanBeurtInfo.setStyle("-fx-background-color: #704e38");
		this.setRight(spelerAanBeurtInfo);

		/*--------------TOP SIDE--------------*/

		int rondeNummer = 0;

		StackPane topOfGameBorderPane = new StackPane();
		BorderPane.setAlignment(topOfGameBorderPane, Pos.CENTER);

		HBox topGameElements = new HBox();
		topGameElements.setAlignment(Pos.CENTER);

		Label ronde = new Label(String.format("%s: %d", Taal.getString("round"), rondeNummer));
		ronde.setStyle(" -fx-text-fill: white; -fx-font-size: 28px; -fx-font-weight: bold;");
		ronde.setAlignment(Pos.CENTER);
		ronde.setPadding(new Insets(15));
		topGameElements.getChildren().add(ronde);
		topOfGameBorderPane.getChildren().add(topGameElements);

		topOfGameBorderPane.setStyle("-fx-background-color: #4a2610;");
		this.setTop(topOfGameBorderPane);

		/*--------------BOTTOM SIDE--------------*/
		StackPane bottomOfGameBorderPane = new StackPane();
		BorderPane.setAlignment(bottomOfGameBorderPane, Pos.CENTER);

		HBox bottomGameElements = new HBox();
		bottomGameElements.setAlignment(Pos.CENTER);

		Button pasBeurt = new Button(Taal.getString("skipTurn"));
		pasBeurt.setStyle(
				"-fx-background-color: #ab4333; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold;");
		pasBeurt.setAlignment(Pos.CENTER);

		pasBeurt.setOnAction(e -> {
			kleurKeuze.clear();
			playerInfo();
			dc.volgendeSpeler();
		});

		bottomGameElements.getChildren().add(pasBeurt);
		bottomOfGameBorderPane.getChildren().add(bottomGameElements);

		bottomOfGameBorderPane.setStyle("-fx-background-color: #4a2610;");
		this.setBottom(bottomOfGameBorderPane);

	}

	private void playerInfo() {
		List<SpelerDTO> aangemeldeSpelers = dc.getAangemeldeSpelers();

		// Create a VBox to hold the player information labels
		VBox playerBoxes = new VBox();
		playerBoxes.setSpacing(10);

		// Loop through the list of players and create a label for each one
		for (SpelerDTO speler : aangemeldeSpelers) {
			// add the player info box to the player boxes
			playerBoxes.getChildren().add(new SpelerNode(speler));
		}

		playerBoxes.setAlignment(Pos.CENTER_RIGHT);
		playerBoxes.setStyle("-fx-background-color: #704e38;");
		this.setLeft(playerBoxes);
	}

	private void gems() {
		VBox gemsBox = new VBox();

		HashMap<Kleur, Integer> fichestapels = dc.getFicheStapels();

		int GEMS_WIDTH_AND_HEIGHT = 128;
		int GEMS_FONTSIZE = 56;
		int GEMS_STROKE = 2;

		for (Kleur kleur : Kleur.values()) {

			ImageView gemImageView = new ImageView();
			try {
				// Create a new gem image view
				File gemFile = new File(String.format("src/resources/img/tokens/token_%s.png", kleur.kind()));
				Image gemImage = new Image(gemFile.toURI().toString());
				gemImageView = new ImageView(gemImage);
			} catch (Exception e) {
				System.out.println("Fout bij het laden van de achtergrond foto voor edelsteenfiches");
				System.err.println("Unexpected error occurred: " + e.getMessage());
				e.printStackTrace();
			}

			Integer amount = fichestapels.get(kleur);

			if (amount != null && amount > 0) {
				// Set the size of the gem image view
				gemImageView.setFitWidth(GEMS_WIDTH_AND_HEIGHT);
				gemImageView.setFitHeight(GEMS_WIDTH_AND_HEIGHT);

				// Create a new VBox to contain the gem image view and the amount text
				StackPane gemBox = new StackPane();
				gemBox.setAlignment(Pos.CENTER);

				// Create the amount text and style it
				Text amountText = new Text(Integer.toString(amount));
				amountText.setFont(Font.font("Arial", FontWeight.BOLD, GEMS_FONTSIZE));
				amountText.setFill(Color.BLACK);
				amountText.setStroke(Color.WHITE);
				amountText.setStrokeWidth(GEMS_STROKE);

				// Add the gem image view and the amount text to the VBox
				gemBox.getChildren().addAll(gemImageView, amountText);

				// Add mouse click event handler to the node
				gemBox.setOnMouseClicked(event -> {
					gemClicked(kleur, fichestapels);
				});

				// Add the VBox containing the gem to the gemsBox
				gemsBox.getChildren().add(gemBox);
			}
		}

		gemsBox.setPadding(new Insets(10));

		// Display the gemsBox to the left of the development cards
		spelbord.setRight(gemsBox);
	}

	private void gemClicked(Kleur kleur, HashMap<Kleur, Integer> fichestapels) {
		int aantalFichesDieGenomenMogenWorden = Math.min(3, dc.geefAantalStapelsMeerDanNul());

		boolean succesvol = false;
		try {

			if (kleurKeuze.contains(kleur)) {
				dc.neemTweeFiches(kleur);
				succesvol = true;
			}

			if (fichestapels.get(kleur) != null && fichestapels.get(kleur) > 0)
				kleurKeuze.add(kleur);

			if (kleurKeuze.size() == aantalFichesDieGenomenMogenWorden) {
				Kleur[] kleurenArray = new Kleur[aantalFichesDieGenomenMogenWorden];
				kleurKeuze.toArray(kleurenArray);
				dc.neemDrieFiches(kleurenArray);
				succesvol = true;
			}

			if (succesvol) {
				if (dc.buitenVoorraad()) {
					System.out.println("BUITEN VOORRAAD");
					geefFichesTerug();
				}

				kleurKeuze.clear();
				gems();
				playerInfo();
				dc.volgendeSpeler();
			}
		} catch (Exception e) {
			errorAlert(e);
		}

	}

//	private void geefFichesTerug() {
//		// TODO moet een popup geven en moet ervoor zorgen dat het ander scherm niet
//		// geklikt kan worden, tot het probleem van buiten voorraad is opgelost
//
//		// toon overzicht van edelsteenfiches in speler zijn voorraad
//		playerInfo();
//
//		// vraag speler om edelsteenfiches terug te leggen naar spel voorraad
//		int aantalTerugTePlaatsen = dc.totaalAantalFichesVanSpelerAanBeurt() - 10;
//
//		for (int i = 0; i < aantalTerugTePlaatsen; i++) {
//			boolean isTerugGelegd = true;
//
//			while (isTerugGelegd) {
//				try {
//
//					System.out.printf("Plaats fiche terug uit eigen stapel (met nummer): ");
//
//					int stapelKeuze = input.nextInt();
//
//					dc.plaatsTerugInStapel(stapelKeuze - 1);
//
//					isTerugGelegd = false;
//				} catch (RuntimeException e) {
//					System.out.println("\nU probeert fiches terug te plaatsen van een lege stapel.\n");
//					isTerugGelegd = true;
//				}
//
//			}
//		}
//
//	}

	public void geefFichesTerug() {
		TextField stapelKeuzeTextField = new TextField();
		Label playerInfoLabel;
		Button plaatsTerugButton;

		Stage popup = new Stage();
		popup.initModality(Modality.APPLICATION_MODAL);
		popup.setTitle("Teruggeven edelsteenfiches");

		// Show player info
		playerInfoLabel = new Label();
//			playerInfoLabel.setText(getPlayerInfo());

		// Ask player to return gemstones
		int aantalTerugTePlaatsen = dc.totaalAantalFichesVanSpelerAanBeurt() - 10;

		VBox root = new VBox(10);

		for (int i = 0; i < aantalTerugTePlaatsen; i++) {
			HBox hbox = new HBox(10);

			stapelKeuzeTextField.setPromptText("Stapelnummer");

			plaatsTerugButton = new Button("Plaats terug");
			plaatsTerugButton.setOnAction(e -> {
				try {
					int stapelKeuze = Integer.parseInt(stapelKeuzeTextField.getText());
					// gameController.plaatsTerugInStapel(stapelKeuze - 1);
					popup.close();
				} catch (NumberFormatException ex) {
					Alert alert = new Alert(AlertType.ERROR, "Voer een geldig stapelnummer in.");
					alert.showAndWait();
				} catch (RuntimeException ex) {
					Alert alert = new Alert(AlertType.ERROR,
							"U probeert fiches terug te plaatsen van een lege stapel.");
					alert.showAndWait();
				}
			});

			hbox.getChildren().addAll(stapelKeuzeTextField, plaatsTerugButton);
			root.getChildren().add(hbox);
		}

		root.getChildren().add(playerInfoLabel);
		Scene popupScene = new Scene(root, 300, 300);
		popup.setScene(popupScene);
		popup.showAndWait();
	}

	// ------------------------------------------------------------------------------------------------------------
	private void nobles() {
		List<SpelVoorwerpDTO> edelen = dc.getEdelen();
		HBox noblesBox = new HBox();

		// loop through the nobles and create a NobleNode for each one
		for (SpelVoorwerpDTO edele : edelen) {
			EdeleNode edeleNode = new EdeleNode(edele);
			noblesBox.getChildren().add(edeleNode);
		}

		noblesBox.setSpacing(10);

//		if (edelen.size() < 5) {
//			noblesBox.setPadding(new Insets(10, 10, 10, 128 + 40));
//		} else {
		noblesBox.setPadding(new Insets(10));
//		}

		spelbord.setTop(noblesBox);
		noblesBox.setAlignment(Pos.TOP_CENTER);
	}

	private void deckCards() {

		VBox stapelFotosBox = new VBox();

		ImageView stapel1 = new ImageView();
		ImageView stapel2 = new ImageView();
		ImageView stapel3 = new ImageView();
		try {
			// Load the image of the deck
			File backgroundFile1 = new File("src/resources/img/card_stacks/level1.png");
			Image backgroundImage1 = new Image(backgroundFile1.toURI().toString());
			stapel1 = new ImageView(backgroundImage1);
			File backgroundFile2 = new File("src/resources/img/card_stacks/level2.png");
			Image backgroundImage2 = new Image(backgroundFile2.toURI().toString());
			stapel2 = new ImageView(backgroundImage2);
			File backgroundFile3 = new File("src/resources/img/card_stacks/level3.png");
			Image backgroundImage3 = new Image(backgroundFile3.toURI().toString());
			stapel3 = new ImageView(backgroundImage3);
		} catch (Exception e) {
			System.out.println("Fout bij het inladen van de fotos voor de stapels");
			System.err.println("Unexpected error occurred: " + e.getMessage());
			e.printStackTrace();
		}

		int stapelWidth = 128;
		int stapelHeight = 200;

		stapel1.setFitWidth(stapelWidth);
		stapel1.setFitHeight(stapelHeight);

		stapel2.setFitWidth(stapelWidth);
		stapel2.setFitHeight(stapelHeight);

		stapel3.setFitWidth(stapelWidth);
		stapel3.setFitHeight(stapelHeight);

		stapelFotosBox.getChildren().addAll(stapel3, stapel2, stapel1);

		stapelFotosBox.setSpacing(10);
		stapelFotosBox.setPadding(new Insets(10));

		spelbord.setLeft(stapelFotosBox);
	}

	private void developmentCards() {
		System.out.println("opnieuw!");
		// Add development cards
		SpelVoorwerpDTO[][] niveaus = { dc.getNiveau3Zichtbaar(), dc.getNiveau2Zichtbaar(), dc.getNiveau1Zichtbaar() };

		GridPane ontwikkelingskaartGridPane = new GridPane();

		// Define the number of columns and rows in the grid
		int numColumns = 4;
		int numRows = 3;

		// Add OntwikkelingskaartNodes to the grid
		for (int i = 0; i < numRows * numColumns; i++) {
			int row = i / numColumns;
			int col = i % numColumns;
			SpelVoorwerpDTO[] niveau = niveaus[row];
			OntwikkelingskaartNode devCardNode = new OntwikkelingskaartNode(niveau[col]);

			// Add mouse click event handler to the node

			devCardNode.setOnMouseClicked(event -> {
				System.out.printf("r:%d,c:%d%n", row, col);
				boolean succesvol = false;
				try {
					dc.kiesOntwikkelingskaart(3 - row, col + 1);
					succesvol = true;
					// kleurKeuze.clear();
				} catch (Exception e) {
					errorAlert(e);
					succesvol = false;
				}

				if (succesvol) {
					gems();
					developmentCards();
					playerInfo();
					dc.volgendeSpeler();
				}

			});

			ontwikkelingskaartGridPane.add(devCardNode, col, row);

		}

		// Set some padding and gaps between cells in the grid
		ontwikkelingskaartGridPane.setPadding(new Insets(10));
		ontwikkelingskaartGridPane.setHgap(10);
		ontwikkelingskaartGridPane.setVgap(10);

		ontwikkelingskaartGridPane.setAlignment(Pos.TOP_CENTER);

		spelbord.setCenter(ontwikkelingskaartGridPane);
	}

	private void errorAlert(Exception e) {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle(Taal.getString("error"));
		alert.setHeaderText(Taal.getString("errorOccured"));
		alert.setContentText(String.format("%s:%n%n", Taal.getString("exceptionThrown")) + e.getMessage());
		alert.showAndWait();
		kleurKeuze.clear();
	}
}
