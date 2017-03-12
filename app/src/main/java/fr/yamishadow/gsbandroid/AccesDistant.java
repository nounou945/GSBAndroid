package fr.yamishadow.gsbandroid;

import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by chett on 04/03/2017.
 */

public class AccesDistant implements AsyncResponse {
    final String SERVERADDR="http://192.168.0.21/gsbandroid/serveurgsb.php/";
    private MainActivity mainActivity;
    public AccesDistant(MainActivity main) {
    this.mainActivity=main;
    }

    @Override
    public void processFinish(String output) {
        Log.d("serveur","********"+output);
        String[] message = output.split("%");
        if(message.length>1){
            if (message[0].equals("connexion")){

                try {
                    JSONObject info=new JSONObject(message[1]);
                    String id= info.getString("id");
                    String login=info.getString("login");
                    String mdp=info.getString("mdp");
                    mainActivity.setVisiteur(new Visiteur(id,login,mdp));
                    Toast.makeText(mainActivity,"Connexion effectuée",Toast.LENGTH_LONG).show();
                    mainActivity.envoiFrais();
                    mainActivity.envoiFraisHf();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            if(message[0].equals("erreurconn")){
                Toast.makeText(mainActivity,message[1],Toast.LENGTH_LONG).show();
            }
        }




    }
    public void envoi(String operation, JSONArray lesDonneesJSON){
        AccesHTTP accesDonnees= new AccesHTTP();
        accesDonnees.delegate = this; // le lien de délégation entre AccesDistant et AccesHTTP
        accesDonnees.addParam("operation", operation);
        accesDonnees.addParam("lesdonnees", lesDonneesJSON.toString());
        accesDonnees.execute(SERVERADDR);
    }
}
