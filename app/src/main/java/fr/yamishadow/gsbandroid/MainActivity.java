package fr.yamishadow.gsbandroid;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Set;

//import org.apache.http.client.ClientProtocolException;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.impl.client.DefaultHttpClient;
//import org.json.JSONObject;

import android.content.DialogInterface;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;

public class MainActivity extends AppCompatActivity {

    // propriétés de la classe
     private AccesDistant accesdistant ;
     private  static Visiteur visiteur ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // récupération des informations sérialisées
        recupSerialize() ;
        // chargement des méthodes événementielles
        cmdMenu_clic(((Button)findViewById(R.id.cmdKm)), KmActivity.class) ;
        cmdMenu_clic(((Button)findViewById(R.id.cmdHf)), HfActivity.class) ;
        cmdMenu_clic(((Button)findViewById(R.id.cmdHfRecap)), HfRecapActivity.class) ;
        cmdMenu_clic(((Button)findViewById(R.id.cmdNuitee)), NuiteActivity.class) ;
        cmdMenu_clic(((Button)findViewById(R.id.cmdRepas)), RepasActivity.class) ;
        cmdMenu_clic(((Button)findViewById(R.id.cmdEtape)), EtapeActivity.class) ;
        accesdistant=new AccesDistant(MainActivity.this);

        cmdTransfert_clic() ;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    /**
     * Récupére la sérialisation si elle existe
     */
    private void recupSerialize() {
    	Global.listFraisMois = (Hashtable<Integer, FraisMois>) Serializer.deSerialize(Global.filename, MainActivity.this) ;
    	// si rien n'a été récupéré, il faut créer la liste
    	if (Global.listFraisMois==null) {
    		Global.listFraisMois = new Hashtable<Integer, FraisMois>() ;
    	}
    }

    /**
     * Sur la sélection d'un bouton dans l'activité principale ouverture de l'activité correspondante
     */
    private void cmdMenu_clic(Button button, final Class classe) {
    	button.setOnClickListener(new Button.OnClickListener() {
    		public void onClick(View v) {
    			// ouvre l'activité
    			Intent intent = new Intent(MainActivity.this, classe) ;
    			startActivity(intent) ;  			
    		}
    	}) ;
    }
    
    /**
     * Cas particulier du bouton pour le transfert d'informations vers le serveur
     */
    private void cmdTransfert_clic() {
    	((Button)findViewById(R.id.cmdTransfert)).setOnClickListener(new Button.OnClickListener() {
    		public void onClick(View v) {
    			// envoi les informations sérialisées vers le serveur
    			// en construction
                LayoutInflater  inflater= getLayoutInflater();
                View alertlayout = inflater.inflate(R.layout.layout_login_dialog,null);
                final EditText login2 =(EditText)alertlayout.findViewById(R.id.txtUsername2);
                final EditText mdp2 =(EditText)alertlayout.findViewById(R.id.txtPassword2);
                AlertDialog.Builder alert=new AlertDialog.Builder(MainActivity.this);
                alert.setTitle("Veuillez vous authentifiez !");
                alert.setView(alertlayout);
                alert.setCancelable(false);//permet de ne pas fermer l'alert dialog
                alert.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getBaseContext(),"Connexion Annulée",Toast.LENGTH_SHORT).show();


                    }
                });
                alert.setPositiveButton("Connexion", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ArrayList liste = new ArrayList();
                        String contenu=login2.getText().toString();
                        String contenu2=mdp2.getText().toString();
                        liste.add(contenu);
                        liste.add(contenu2);
                        accesdistant.envoi("connexion",new JSONArray(liste));
                    }
                });
                AlertDialog dialog=alert.create();
                dialog.show();


            }
    	}) ;    	
    }

    // getter et setter sur Visiteur
    public static Visiteur getVisiteur() {
        return visiteur;
    }

    public static void setVisiteur(Visiteur visiteur) {
        MainActivity.visiteur = visiteur;
    }

    /**
     * envoie les frais forfaitaires dans la bdd
     */
    public void envoiFrais(){
         Set lesClefs= Global.listFraisMois.keySet(); // recup les clefs
         for(Object uneclef:lesClefs){

           FraisMois lesFrais= Global.listFraisMois.get((int)uneclef);

           ArrayList liste=new ArrayList();
             liste.add(visiteur.getId());
             liste.add(lesFrais.getAnnee());
             liste.add(lesFrais.getMois());
             liste.add(lesFrais.getNuitee());
             liste.add(lesFrais.getEtape());
             liste.add(lesFrais.getKm());
             liste.add(lesFrais.getRepas());
             Log.d("fraismois","*******************"+liste);
             accesdistant.envoi("fraismois",new JSONArray(liste));


         }
     }

    /**
     * permet l'envoi des frais hf à la bdd
     */
    public void envoiFraisHf(){
        Set lesClefs= Global.listFraisMois.keySet(); // recup les clefs
        for(Object uneclef:lesClefs){

            FraisMois lesFrais= Global.listFraisMois.get((int)uneclef);



             int annee=lesFrais.getAnnee();
             int mois =lesFrais.getMois();
            for(FraisHf unFraishf:lesFrais.getLesFraisHf()){
                ArrayList listehf=new ArrayList();
                listehf.add(visiteur.getId());
                listehf.add(annee);
                listehf.add(mois);
                listehf.add(unFraishf.getJour());
                listehf.add(unFraishf.getMontant());
                listehf.add(unFraishf.getMotif());
                Log.d("fraismois","*******************"+listehf);
                accesdistant.envoi("fraishf",new JSONArray(listehf));


            }



        }
        Global.listFraisMois.clear();
        Serializer.serialize(Global.filename,Global.listFraisMois,MainActivity.this);

    }

}
