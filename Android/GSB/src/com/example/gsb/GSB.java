package com.example.gsb;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;


import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class GSB extends Activity {
	
	//Login
	EditText loginText, passwordText;
	String login, resultat;
	Button bouton;
	
	//Praticien
	TextView visiteur;
	String nom_visiteur, prenom_visiteur, id_visiteur;
	
	//
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		// récupération de l'EditTextLogin grâce à son ID
		loginText = (EditText) findViewById(R.id.EditTextLogin);
		// récupération de l'EditTextPassword grâce à son ID
		passwordText = (EditText) findViewById(R.id.EditTextPassword);
		// récupération du boutton grâce à son ID
		bouton = (Button) findViewById(R.id.BoutonEnvoyer);
		
		//on applique un écouteur d'évenement au clique bouton
		bouton.setOnClickListener(
				new OnClickListener(){
					@Override
					public void onClick(View v){
						//On garde le login pour l'utiliser dans la vue praticien
						login = loginText.getText().toString();					
						
						
						try{
							//Création des paramétres à envoyer au serveur web
							ArrayList<NameValuePair> parametres = new ArrayList<NameValuePair>();
							parametres.add(new BasicNameValuePair("login", loginText.getText().toString()));
							parametres.add(new BasicNameValuePair("password", passwordText.getText().toString()));
							
							//Envoie des paramétres aux script PHP
							HttpClient client = new DefaultHttpClient();
							HttpPost post = new HttpPost("http://laurentlepee.com/bts/androidGSB/login_verify.php");
							
							//Envoie des données
							post.setEntity(new UrlEncodedFormEntity(parametres));
							
							//Réponse du serveur web
							HttpResponse response = client.execute(post);
							
							//Récupération de la réponse en JSON
							String jsonResult = inputStreamToString(
									response.getEntity().getContent()).toString();
							JSONObject jsonobject = new JSONObject(jsonResult);
							
							//On parcour l'objet JSON pour avoir la valeur de "result"
							resultat = jsonobject.getString("result");
						} catch(JSONException e){
							Log.e("log_tag", "Erreur JSON " + e.toString());
						} catch (Exception e){
							Log.e("log_tag", "Erreur connexion http " + e.toString());
						}
						//Si resultat JSON = ok on passe a la vue praticien sinon on affiche une erreur
						if(resultat.matches("ok")){
							Visiteur(v);
						}else{
							((TextView)findViewById(R.id.TextViewErreur)).setText("Login ou password incorrect");
						}
					}
				}
		);
	}
	
	public void Visiteur (View v){
		//Affichage de la vue
		setContentView(R.layout.activity_visiteur);
		((TextView)findViewById(R.id.TextViewVisiteur)).setText("Bienvenue " + login);
		try{
			//Création des paramétres à envoyer au serveur web
			ArrayList<NameValuePair> parametres = new ArrayList<NameValuePair>();
			parametres.add(new BasicNameValuePair("login", login));
			
			//Envoie des paramétres aux script PHP
			HttpClient client = new DefaultHttpClient();
			HttpPost post = new HttpPost("http://laurentlepee.com/bts/androidGSB/visiteur.php");
			
			//Envoie des données
			post.setEntity(new UrlEncodedFormEntity(parametres));
			
			//Réponse du serveur web
			HttpResponse response = client.execute(post);
			
			//Récupération de la réponse en JSON
			String jsonResult = inputStreamToString(
					response.getEntity().getContent()).toString();
			JSONObject jsonobject = new JSONObject(jsonResult);
			
			//On parcour l'objet JSON pour avoir la valeur de "result"
			id_visiteur = jsonobject.getString("id_vis");
			nom_visiteur = jsonobject.getString("nom_vis");
			prenom_visiteur = jsonobject.getString("prenom_vis");
			
		} catch(JSONException e){
			Log.e("log_tag", "Erreur JSON " + e.toString());
		} catch (Exception e){
			Log.e("log_tag", "Erreur connexion http " + e.toString());
		}
		if (nom_visiteur.contentEquals("null")) {
			
		} else {
			((TextView)findViewById(R.id.TextViewVisiteur)).setText("Vous êtes : " + nom_visiteur + " " + prenom_visiteur + " " + id_visiteur);
		}
		
	}
	
	
	
	
	private StringBuilder inputStreamToString(InputStream is) {
		String rLine = "";
		StringBuilder answer = new StringBuilder();
		BufferedReader rd = new BufferedReader(new InputStreamReader(is));

		try {
			while ((rLine = rd.readLine()) != null) {
				answer.append(rLine);
			}
		}

		catch (IOException e) {
			e.printStackTrace();
		}
		return answer;
	}
}
