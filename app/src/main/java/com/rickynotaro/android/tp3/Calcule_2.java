package com.rickynotaro.android.tp3;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.res.Resources;
import android.content.res.Configuration;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;


public class Calcule_2 extends AppCompatActivity {

    private ListView listeNavigation;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private Resources res;
    private TextView infoMontant, infoTauxInteret, infoVersement, infoPretTotal, infoInteretTotal, infoDuree;
    private Button retour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        res = getResources();

        exempleDrawerLayout();

       afficher_info();
        retour = (Button)findViewById(R.id.BoutonRetourCalcul);
        retour.setOnClickListener(ecouterboutonretour);



    }
    // méthode qui ferme l'activite calcule 2 pour revenir a calcul acceuil
    private View.OnClickListener ecouterboutonretour = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
      finish();
        }
    };
// méthode qui permet d'afficher les information des données du montant de l'interet de la duré et du résultat des versement
    public void afficher_info(){
        Intent intent = getIntent();
        Pret pret = intent.getParcelableExtra("cle_pret");
        infoDuree = (TextView) findViewById(R.id.infoDure);
        infoMontant = (TextView) findViewById(R.id.infoMontant);
        infoTauxInteret = (TextView) findViewById(R.id.infoTaux);
        infoPretTotal = (TextView) findViewById(R.id.infoPretTotal);
        infoVersement = (TextView) findViewById(R.id.infoVersement);
        infoInteretTotal = (TextView) findViewById(R.id.infoInteret);




        infoDuree.setText(infoDuree.getText() + " "+Integer.toString(pret.getDuree()));
        infoMontant.setText(infoMontant.getText() + " "+String.format(Locale.getDefault(),"%1$.2f",pret.getMontant()));
        infoTauxInteret.setText(infoTauxInteret.getText() + " "+String.format(Locale.getDefault(),"%1$.2f",pret.getInteret()));
        infoVersement.setText(infoVersement.getText() + " "+String.format(Locale.getDefault(),"%1$.2f",pret.calculerVersement()));
       infoInteretTotal.setText(infoInteretTotal.getText() + " "+String.format(Locale.getDefault(),"%1$.2f",pret.calculerInteretTotal()));
        infoPretTotal.setText(infoPretTotal.getText() + " "+String.format(Locale.getDefault(),"%1$.2f",pret.calculerPretTotal()));
    }

// méthode qui crée la barre d'action
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_barre_actions, menu);
        return true;
    }

// méthode qui ecoute les boutons retour a la page d'acceuil et aide de la barre d'action
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean traiter = super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.menuAccueil:
                Intent intent = new Intent(Calcule_2.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);

                traiter = true;
                break;
            case R.id.menuAide:
                AlertDialog.Builder boiteAlert = new AlertDialog.Builder(Calcule_2.this);
                boiteAlert.setTitle(R.string.action_aide);
                boiteAlert.setIcon(R.drawable.ic_info_aide);
                boiteAlert.setMessage(R.string.aide_infos_pret);
                boiteAlert.setPositiveButton(R.string.txt_alertdialog_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), R.string.txt_alertdialog_ok, Toast.LENGTH_SHORT).show();

                    }
                });
                AlertDialog boiteAlertDialogue = boiteAlert.create();
                boiteAlertDialogue.show();
                traiter = true;
                break;


        }
        if (drawerToggle.onOptionsItemSelected(item)) {
            traiter = true;
        }
        return traiter;
    }

// méthode qui crée le drawerlayout
    private void exempleDrawerLayout() {
        setContentView(R.layout.calcule_2);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        listeNavigation = (ListView) findViewById(R.id.list_navigation);

        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.ouvrir_menu, R.string.fermer_menu);
        drawerToggle.setDrawerIndicatorEnabled(true);
        drawerLayout.addDrawerListener(drawerToggle);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.sous_titre_infos_pret);

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        listeNavigation.setOnItemClickListener(ecouterListeNavigation);


    }
// méthode qui écoute les differents choix du drawerlayout
    private AdapterView.OnItemClickListener ecouterListeNavigation = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = null;
            String itemChoisi = parent.getItemAtPosition(position).toString();

            switch (position) {
                case 0:
                    intent = new Intent(Calcule_2.this, Calculer_acceuil.class);
                    break;
                case 1:
                    intent = new Intent(Calcule_2.this, ConifereActivity.class);
            }
            if (intent != null){
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }

            drawerLayout.closeDrawers();
        }
    };

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

}