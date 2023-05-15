package gui;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import domein.DomeinController;
import domein.Kleur;
import domein.Speler;
import dto.SpelVoorwerpDTO;
import dto.SpelerDTO;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import resources.Taal;

public class SpeelSpelScherm extends BorderPane {
	private final DomeinController dc;
	private BorderPane spelbord;
	private HashSet<Kleur> kleurKeuze = new HashSet<>();
	private int vorigeRonde = 0;

	public SpeelSpelScherm(DomeinController dc) {
		this.dc = dc;
		this.spelbord = new BorderPane();

		dc.startNieuwSpel();

		// [TEST]
		// (1): 100fiches voor elke speler
//		dc.testGeeftVeelEdelsteenfichesAanSpelers();

		// (2): 2 O.K. elke kleur + 10fiches
//		dc.testGeeftOntwikkelingskaartenAanSpelerAanBeurt();

		// (3): speler aan beurt krijgt 2 O.K. van elke kleur, alle spelers krijgen 15
		// prestige
//		dc.testGeeftEvenVeelWinnendePrestigepuntenMaarVerschillendAantalOntwikkelinkgskaarten();

		// (4): random[15-18] prestige-punten voor elke speler
//		dc.testMaaktWinnaarAan();

		buildGui();
	}

	private void buildGui() {

		Image backgroundImage = null;
		File backgroundFile = new File("src/resources/img/background_misc/bg_wood.jpg");
		if (backgroundFile.exists()) {
			try (InputStream inputStream = new FileInputStream(backgroundFile)) {
				backgroundImage = new Image(inputStream);
			} catch (Exception e) {
				System.out.println("Fout bij het inladen van de achtergrond foto van het spelbord");
				System.err.println("Unexpected error occurred: " + e.getMessage());
				e.printStackTrace();
			}
		} else {
			System.err.println("Achtergrond van het spelbord bestaat niet: " + backgroundFile.getAbsolutePath());
		}

		Region backgroundRegion = new Region();
		backgroundRegion.setBackground(new Background(
				new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
						BackgroundPosition.CENTER, new BackgroundSize(1.0, 1.0, true, true, false, false))));

		this.setBackground(backgroundRegion.getBackground());

		/*--------------CREATE THE BORD--------------*/

		// spelbord.setStyle("-fx-background-color: #363200;");

		setAlignment(spelbord, Pos.CENTER);
		spelbord.setMaxWidth(875);
		spelbord.setMaxHeight(800);
		this.setCenter(spelbord);

		playerInfo();
		nobles();
		deckCards();
		developmentCards();
		gems();

		legeRechterkant();

		ronde();

		pasBeurt();
	}

	private void bepaalWinnaar() {
		if (dc.getRonde() != vorigeRonde) {
			vorigeRonde = dc.getRonde();
			List<SpelerDTO> winnaars = dc.bepaalWinnaar();
			try {
				if (winnaars.size() > 0) {
					WinnaarScherm winnaarsScherm = new WinnaarScherm(dc);
					Stage stage = (Stage) this.getScene().getWindow();
					Scene scene = new Scene(winnaarsScherm, stage.getWidth(), stage.getHeight());
					stage.setTitle(Taal.getString("winner"));
					stage.setScene(scene);
				}
			} catch (Exception e) {
				System.out.println("PROBLEEM BIJ BEPAAL WINNAAR");
			}
		}
	}

	private void legeRechterkant() {
		HBox spelerAanBeurtInfo = new HBox();
		StackPane spacer = new StackPane();
		spacer.setMinWidth(256 + 32);
		spelerAanBeurtInfo.getChildren().add(spacer);
		spelerAanBeurtInfo.setStyle("-fx-background-color: #704e38");
		this.setRight(spelerAanBeurtInfo);
	}

	private void ronde() {
		StackPane topOfGameBorderPane = new StackPane();
		BorderPane.setAlignment(topOfGameBorderPane, Pos.CENTER);

		HBox topGameElements = new HBox();
		topGameElements.setAlignment(Pos.CENTER);

		Label ronde = new Label(String.format("%s: %d", Taal.getString("round"), dc.getRonde()));
		ronde.setStyle(
				" -fx-text-fill: white; -fx-font-size: 28px; -fx-font-weight: bold; -fx-font-family: \"Lucida Calligraphy\", cursive;");
		ronde.setAlignment(Pos.CENTER);
		ronde.setPadding(new Insets(10));
		topGameElements.getChildren().add(ronde);
		topOfGameBorderPane.getChildren().add(topGameElements);

		topOfGameBorderPane.setStyle("-fx-background-color: #4a2610;");
		this.setTop(topOfGameBorderPane);
	}

	private void pasBeurt() {
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
			dc.volgendeSpeler();
			playerInfo();
			ronde();
			bepaalWinnaar();
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

					int aantalTerugTePlaatsen = dc.totaalAantalFichesVanSpelerAanBeurt() - 10;
					for (int i = 0; i < aantalTerugTePlaatsen; i++) {
						geefFichesTerug();
					}
				}

				kleurKeuze.clear();
				gems();
				dc.volgendeSpeler();
				playerInfo();
				ronde();
				bepaalWinnaar();
			}
		} catch (Exception e) {
			errorAlert(e);
		}

	}

	private void geefFichesTerug() {
		BooleanProperty popupCloseFlag = new SimpleBooleanProperty(false);

		int REQUIREMENTS_SIZE = 40 * 3;
		int GEM_FONTSIZE = 28 * 3;

		VBox geefFichesTerugElementen = new VBox();
		geefFichesTerugElementen.setSpacing(10);
		geefFichesTerugElementen.setAlignment(Pos.CENTER);
		geefFichesTerugElementen.setStyle("-fx-background-color: #4a2610; ");
		// -fx-background-radius: 20 20 20 20;

		List<SpelerDTO> spelers = dc.getAangemeldeSpelers();
		SpelerDTO speler = null;

		for (SpelerDTO s : spelers) {
			if (s.aanDeBeurt()) {
				speler = s;
			}
		}

		Text titel = new Text(Taal.getString("giveBack"));
		titel.setFont(Font.font("Arial", FontWeight.BOLD, 28));
		titel.setFill(Color.RED);
		titel.setTextAlignment(TextAlignment.CENTER);

		Text subtitel = new Text(String.format("%s%n%s %d.", Taal.getString("giveBackSubText"),
				Taal.getString("giveBackSubText2"), Speler.getMaxEdelsteenfichesInVoorraad()));
		subtitel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		subtitel.setFill(Color.YELLOW);
		subtitel.setTextAlignment(TextAlignment.CENTER);

		HBox gemsInHand = new HBox();
		StackPane.setAlignment(gemsInHand, Pos.CENTER);
		HashMap<Kleur, Integer> edelsteenfichesInHand = speler.edelsteenfichesInHand();

		for (Kleur kleur : Kleur.values()) {
			ImageView gemTokenImageView = new ImageView();
			try {
				File gemTokenFile = new File("src/resources/img/tokens/token_" + kleur.kind() + ".png");
				Image gemTokenImage = new Image(gemTokenFile.toURI().toString());
				gemTokenImageView = new ImageView(gemTokenImage);
			} catch (Exception e) {
				System.out.println("Fout bij het laden van de edelsteenfiche fotos voor Speler");
				System.err.println("Unexpected error occurred: " + e.getMessage());
				e.printStackTrace();
			}

			gemTokenImageView.setFitWidth(REQUIREMENTS_SIZE / 1.5);
			gemTokenImageView.setFitHeight(REQUIREMENTS_SIZE / 1.5);

			Integer amount = edelsteenfichesInHand.get(kleur);
			if (amount == null) {
				amount = 0;
			}

			Text amountText = new Text(Integer.toString(amount));
			amountText.setFont(Font.font("Arial", FontWeight.BOLD, GEM_FONTSIZE / 1.5));
			amountText.setFill(Color.WHITE);
			amountText.setStroke(Color.BLACK);
			amountText.setStrokeWidth(1);

			StackPane gemStackPane = null;

			if (amount != 0) {
				gemStackPane = new StackPane(gemTokenImageView, amountText);
				gemStackPane.setPrefSize(REQUIREMENTS_SIZE, REQUIREMENTS_SIZE);

				gemStackPane.setOnMouseClicked(event -> {
					dc.plaatsTerugInStapel(kleur.getKleur());
					popupCloseFlag.set(true);
				});

				gemsInHand.getChildren().add(gemStackPane);
			}
		}

		Stage popup = new Stage();
		popup.initModality(Modality.APPLICATION_MODAL);
		popup.initStyle(StageStyle.UNDECORATED);
		popup.setTitle(Taal.getString("giveBackTitle"));

		geefFichesTerugElementen.getChildren().addAll(titel, subtitel, gemsInHand);

		Scene popupScene = new Scene(geefFichesTerugElementen, 512, 256);
		popup.setScene(popupScene);

		popupCloseFlag.addListener((observable, oldValue, newValue) -> {
			if (newValue) {
				popup.close();
			}
		});

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

		// if (edelen.size() < 5) {
		// noblesBox.setPadding(new Insets(10, 10, 10, 128 + 40));
		// } else {
		noblesBox.setPadding(new Insets(10));
		// }

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

					try {
						List<SpelVoorwerpDTO> edelDTOs = dc.krijgEdeleGui();
						int aantalTeVerkrijgenEdelen = edelDTOs.size();

						if (aantalTeVerkrijgenEdelen > 1) {
							kiesEdele();
						} else if (aantalTeVerkrijgenEdelen == 1) {
							dc.kiesEdele(edelDTOs.get(0));
							// dc.krijgEdele(); werkt ook
						}

					} catch (Exception e) {
						System.err
								.print(Taal.getString("splendorApplicatieKoopOntwikkelingskaartGetNobleWentWrongMsg"));
					}

					nobles();

					dc.volgendeSpeler();
					playerInfo();
					ronde();
					bepaalWinnaar();
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

	private void kiesEdele() {
		BooleanProperty popupCloseFlag = new SimpleBooleanProperty(false);

		Text titel = new Text(Taal.getString("chooseNoble"));
		titel.setFont(Font.font("Arial", FontWeight.BOLD, 28));
		titel.setFill(Color.RED);
		titel.setTextAlignment(TextAlignment.CENTER);

		Text subtitel = new Text(String.format("%s%n%s %d.", Taal.getString("chooseNobleSubText"),
				Taal.getString("chooseNobleSubText2"), Speler.getMaxEdelsteenfichesInVoorraad()));
		subtitel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		subtitel.setFill(Color.YELLOW);
		subtitel.setTextAlignment(TextAlignment.CENTER);

		List<SpelVoorwerpDTO> krijgbareEdelen = dc.krijgEdeleGui();

		HBox krijgbareEdeleHBox = new HBox();
		StackPane.setAlignment(krijgbareEdeleHBox, Pos.CENTER);
		krijgbareEdeleHBox.setAlignment(Pos.CENTER);
		krijgbareEdeleHBox.setSpacing(20);

		for (SpelVoorwerpDTO edele : krijgbareEdelen) {
			EdeleNode edeleNode = new EdeleNode(edele);

			edeleNode.setOnMouseClicked(event -> {
				dc.kiesEdele(edele);
				popupCloseFlag.set(true);
			});

			krijgbareEdeleHBox.getChildren().add(edeleNode);
		}

		Stage popup = new Stage();
		popup.initModality(Modality.APPLICATION_MODAL);
		popup.initStyle(StageStyle.UNDECORATED);
		popup.setTitle(Taal.getString("giveBackTitle"));

		VBox kiesEdeleElementen = new VBox();
		kiesEdeleElementen.setSpacing(10);
		kiesEdeleElementen.setAlignment(Pos.CENTER);
		kiesEdeleElementen.setStyle("-fx-background-color: #4a2610; ");
		// -fx-background-radius: 20 20 20 20;

		kiesEdeleElementen.getChildren().addAll(titel, subtitel, krijgbareEdeleHBox);

		Scene popupScene = new Scene(kiesEdeleElementen, 512, 256 + 32);
		popup.setScene(popupScene);

		popupCloseFlag.addListener((observable, oldValue, newValue) -> {
			if (newValue) {
				popup.close();
			}
		});

		popup.showAndWait();
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
