package fr.yamishadow.gsbandroid;

import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.DatePicker.OnDateChangedListener;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;

public class KmActivity extends AppCompatActivity {

	// informations affichées dans l'activité
	private int annee ;
	private int mois ;
	private int qte ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_km);
		// modification de l'affichage du DatePicker
		Global.changeAfficheDate((DatePicker) findViewById(R.id.datKm)) ;
		// valorisation des propriétés
		valoriseProprietes() ;
        // chargement des méthodes événementielles
		imgReturn_clic() ;
		cmdValider_clic() ;
		cmdPlus_clic() ;
		cmdMoins_clic() ;
		dat_clic() ;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.frais_km, menu);
		return true;
	}

	/**
	 * Valorisation des propriétés avec les informations affichées
	 */
	private void valoriseProprietes() {
		if((DatePicker)findViewById(R.id.datKm)!=null){
			annee = ((DatePicker) findViewById(R.id.datKm)).getYear();
			mois = ((DatePicker) findViewById(R.id.datKm)).getMonth() + 1;
		}
		// récupération de la qte correspondant au mois actuel
		qte = 0 ;
		int key = annee*100+mois ;
		if (Global.listFraisMois.containsKey(key)) {
			qte = Global.listFraisMois.get(key).getKm() ;
		}
		((EditText)findViewById(R.id.txtKm)).setText(String.valueOf(qte)) ;
	}
	
	/**
	 * Sur la selection de l'image : retour au menu principal
	 */
    private void imgReturn_clic() {
    	((ImageView)findViewById(R.id.imgKmReturn)).setOnClickListener(new ImageView.OnClickListener() {
    		public void onClick(View v) {
    			retourActivityPrincipale() ;    		
    		}
    	}) ;
    }

    /**
     * Sur le clic du bouton valider : sérialisation
     */
    private void cmdValider_clic() {
    	((Button)findViewById(R.id.cmdKmValider)).setOnClickListener(new Button.OnClickListener() {
    		public void onClick(View v) {
    			Serializer.serialize(Global.filename, Global.listFraisMois, KmActivity.this) ;

    			retourActivityPrincipale() ;    		
    		}
    	}) ;    	
    }
    
    /**
     * Sur le clic du bouton plus : ajout de 10 dans la quantité
     */
    private void cmdPlus_clic() {
    	((Button)findViewById(R.id.cmdKmPlus)).setOnClickListener(new Button.OnClickListener() {
    		public void onClick(View v) {
    			qte+=10 ;
    			enregNewQte() ;
    		}
    	}) ;    	
    }
    
    /**
     * Sur le clic du bouton moins : enléve 10 dans la quantité si c'est possible
     */
    private void cmdMoins_clic() {
    	((Button)findViewById(R.id.cmdKmMoins)).setOnClickListener(new Button.OnClickListener() {
    		public void onClick(View v) {
   				qte = Math.max(0, qte-10) ; // suppression de 10 si possible
    			enregNewQte() ;
     		}
    	}) ;    	
    }
    
    /**
     * Sur le changement de date : mise à jour de l'affichage de la qte
     */
    private void dat_clic() {   	
    	final DatePicker uneDate = (DatePicker)findViewById(R.id.datKm) ;
    	uneDate.init(uneDate.getYear(), uneDate.getMonth(), uneDate.getDayOfMonth(), new OnDateChangedListener(){
			@Override
			public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
				valoriseProprietes() ;				
			}
    	});       	
    }

	/**
	 * Enregistrement dans la zone de texte et dans la liste de la nouvelle qte, à la date choisie
	 */
	private void enregNewQte() {
		// enregistrement dans la zone de texte
		((EditText)findViewById(R.id.txtKm)).setText(String.valueOf(qte)) ;
		// enregistrement dans la liste
		int key = annee*100+mois ;
		if (!Global.listFraisMois.containsKey(key)) {
			// creation du mois et de l'annee s'ils n'existent pas déjà
			Global.listFraisMois.put(key, new FraisMois(annee, mois)) ;
		}
		Global.listFraisMois.get(key).setKm(qte) ;

		if(Global.listFraisMois.get(key)!=null){
		Log.d("KM:","*****************"+Global.listFraisMois.get(key).getKm());
			Log.d("DATEKM:","*****************"+mois);
			Log.d("DATEKM2:","*****************"+annee);
	}}

	/**
	 * Retour à l'activité principale (le menu)
	 */
	private void retourActivityPrincipale() {
		Intent intent = new Intent(KmActivity.this, MainActivity.class) ;
		startActivity(intent) ;   					
	}
}
