package com.rickynotaro.android.tp3;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

public class Activite_3 extends AppCompatActivity {

    private ListView listeNavigation;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private Button boutonOuvrire;
    private Spinner spinnerReseaux;
    // Récupérer le nom du package.
    public static final String NOM_PACKAGE = ConifereActivity.class.getPackage().getName();

    // Contient les différents clé de l'intent.
    public static final String CLE_RESEAU = NOM_PACKAGE + ".RESEAU";
    public static final String CLE_POSITION = NOM_PACKAGE + ".POSITION";

    private String reseaux_Choisi;
    private Resources res;
    private String nomReseau;
    private int positions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activite_3);

        // Debut Drawer
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        listeNavigation = (ListView) findViewById(R.id.list_navigation);

        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                R.string.ouvrir_menu, R.string.fermer_menu);

        drawerToggle.setDrawerIndicatorEnabled(true);

        drawerLayout.addDrawerListener(drawerToggle);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.sous_titre_activite3);

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        listeNavigation.setOnItemClickListener(ecouterListeNavigation);

        // Fin Drawer

        boutonOuvrire = (Button)findViewById(R.id.bouton_ouvrir_page);
        spinnerReseaux = (Spinner)findViewById(R.id.spinner_reseaux);
        spinnerReseaux.setOnItemSelectedListener(ecouterSpinner);
        boutonOuvrire.setOnClickListener(ecouterBoutonOuvrir);

    }

    private AdapterView.OnClickListener ecouterBoutonOuvrir = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(Activite_3.this,Activite_3_2.class);
            intent.putExtra(CLE_RESEAU,nomReseau);
            intent.putExtra(CLE_POSITION,positions);
            res = getResources();
            startActivity(intent);

        }
    };
    private AdapterView.OnItemSelectedListener ecouterSpinner = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            nomReseau = parent.getItemAtPosition(position).toString();
            positions = position;

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };
    // méthode qui écoute la selection de l'activité dans le drawerlayout
    private AdapterView.OnItemClickListener ecouterListeNavigation = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = null;

            switch (position) {
                case 0:
                    intent = new Intent(Activite_3.this, Calculer_acceuil.class);
                    break;
                case 1:
                    intent = new Intent(Activite_3.this, ConifereActivity.class);
                case 2:
                    intent = new Intent(Activite_3.this, Activite_3.class);
            }
            if (intent != null){
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }

            drawerLayout.closeDrawers();
        }
    };

    @Override
    protected void onPostCreate(Bundle savedInstanceState){
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        boolean traiter = super.onOptionsItemSelected(item);



        switch (item.getItemId()){
            case R.id.menuAccueil:
                Intent intent = new Intent(Activite_3.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                traiter = true;
                break;

            case R.id.menuAide:
                AlertDialog.Builder boiteAlerte = new AlertDialog.Builder(Activite_3.this);

                boiteAlerte.setTitle(R.string.action_aide);
                boiteAlerte.setIcon(R.drawable.ic_info_aide);
                boiteAlerte.setMessage(R.string.aide_accueil);
                boiteAlerte.setPositiveButton(R.string.txt_alertdialog_ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }
                );
                AlertDialog boiteAlertDialog = boiteAlerte.create();
                boiteAlertDialog.show();

                traiter = true;
                break;
            default:
                break;

        }

        if(drawerToggle.onOptionsItemSelected(item)){
            traiter = true;
        }

        return traiter;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_barre_actions, menu);
        return true;
    }


}