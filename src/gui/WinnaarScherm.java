package gui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import domein.DomeinController;
import domein.Speler;
import dto.SpelerDTO;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import resources.Taal;

public class WinnaarScherm extends VBox {
	private final DomeinController dc;
	private List<SpelerDTO> spelerDTOs;
	private List<String> spelersRang;
	private List<String> winnaarsLijst;
	private boolean isWinnaar;
	private Text user;
	private Text winner;

	public WinnaarScherm(DomeinController dc)
	{
		this.dc = dc;
		buildGui();
	}

	private void buildGui()
	{

		setSpacing(20);
		setPadding(new Insets(40));
		setAlignment(Pos.CENTER);
		//titel
		Label lblTitel = new Label(String.format("%s", Taal.getString("victory")));
		lblTitel.setFont(Font.font("Helvetica", FontWeight.BOLD, 50));


		//		//aangemelde speler DTOS ophalen
		//		spelerDTOs = dc.getAangemeldeSpelers();
		//
		//		//lijst storteren
		//		Collections.sort(spelerDTOs, new Comparator<SpelerDTO>()
		//		{
		//			public int compare(SpelerDTO s1, SpelerDTO s2)
		//			{
		//				return s2.aantalPrestigepunten() - s1.aantalPrestigepunten();
		//			}
		//		});
		//		
		//		//namen van gebruikers gesorteerd
		//		spelersRang = new ArrayList<>();
		//		for(SpelerDTO s:spelerDTOs)
		//		{
		//			spelersRang.add(s.gebruikersnaam());
		//		}
		//
		//
		//		//winaars lijst
		//		winnaarsLijst = new ArrayList<>();
		//		for(Speler w: dc.bepaalWinnaar())
		//		{
		//			winnaarsLijst.add(w.getGebruikersnaam());
		//		}


		//winnaars grid
		
		//TEST TEST TEST TEST TEST TEST TEST TEST TEST TEST
		GridPane gridWinnaars = new GridPane();
		gridWinnaars.setAlignment(Pos.CENTER);
		gridWinnaars.setHgap(10);
		gridWinnaars.setVgap(10);
		gridWinnaars.setPadding(new Insets(20));

		for(int i=0;i<=3;i++) {
			user = new Text(String.format("%d. user%d :", i+1,i+1));
			user.setFont(Font.font("Helvetica", 15));
			
			gridWinnaars.add(user, 0, i);			
		}
		
		winner = new Text("15 points !!! WINNER !!!");
		winner.setFont(Font.font("Helvetica",FontWeight.BOLD ,23));
		
		
		Text notwinner1 = new Text("10 points");
		notwinner1.setFont(Font.font("Helvetica",FontWeight.BOLD ,20));
		
		Text notwinner2 = new Text("8 points");
		notwinner2.setFont(Font.font("Helvetica",FontWeight.BOLD ,17));
		
		Text notwinner3 = new Text("6 points");
		notwinner3.setFont(Font.font("Helvetica",FontWeight.BOLD ,15));
		
		gridWinnaars.add(winner, 1, 0);
		gridWinnaars.add(notwinner1, 1, 1);
		gridWinnaars.add(notwinner2, 1, 2);
		gridWinnaars.add(notwinner3, 1, 3);
	
		


		//
		//		for(int i=0;i<spelersRang.size();i++) 
		//		{
		//			Text winaars = new Text(spelersRang.get(i));
		//			gridWinnaars.add(winaars, 0, i);;
		//			Text txtWinaar = new Text(isWinnaar?Taal.getString("winner"):"");
		//			gridWinnaars.add(txtWinaar, 1, i);
		//		}
		//
		//		//vergelijk de List<String> spelersRang met de List<String> winnaarsLijst
		//
		//		for(int i = 0;i<winnaarsLijst.size();i++)
		//		{
		//			isWinnaar = spelersRang.get(i).equalsIgnoreCase(winnaarsLijst.get(i));
		//		}

		//toevoegen aan VBox
		getChildren().addAll(lblTitel,gridWinnaars);
	}
}
