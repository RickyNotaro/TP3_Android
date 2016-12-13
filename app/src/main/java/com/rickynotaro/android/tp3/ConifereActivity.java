/**
 * Auteurs : Ricky Notaro-Garcia
 * Fichier : ConifereActivity.java
 * Date    : 12 décembre 2016
 * Cours   : 420-254-MO (TP3 Android)
 */

/**
 * Classe contenant la gestion de la première activité "Conifère".
 *
 * Cette activité permet à l'utilisateur de sélectionné une forme d'aiguille pour passer à
 * la deuxième activité du Conifere.
 *
 */


package com.rickynotaro.android.tp3;

import android.app.Activity;
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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ConifereActivity extends AppCompatActivity {

    // Récupérer le nom du package.
    public static final String NOM_PACKAGE = ConifereActivity.class.getPackage().getName();

    // Contient les différents clé de l'intent.
    public static final String CLE_CHOIX = NOM_PACKAGE + ".CHOIX";
    public static final String CLE_NOM = NOM_PACKAGE + ".NOM";
    public static final String CLE_IMAGE = NOM_PACKAGE + ".IMAGE";
    public static final String CLE_LIENWEB = NOM_PACKAGE + ".LIENWEB";

    // Variantes des codes de requêtes
    public static final int REQUETE_CHOIX_CONIFERE = 1;

    // Contiendra les valeurs retounées par l'activitée conifèreDeux.
    private String nomConifere;
    private String nomResConifere;
    private String lienConifere;

    // Contient les composants du drawer.
    private ListView listeNavigation;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private Resources res;

    // Contient différentes composants de l'interface Graphique
    private ListView idListeChoix;
    private TextView idResultat;

    // P
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conifere);
        preparerDrawer();
        recupererComposants();
    }

    // Récupére les différents composant ayant besoin de traitement.
    private void recupererComposants() {
        res = getResources();
        idListeChoix = (ListView) findViewById( R.id.listview_Choix );
        idResultat = (TextView) findViewById( R.id.item_conifere );
        idListeChoix.setOnItemClickListener(ecouterListViewConifere);
    }

    private void preparerDrawer() {

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        listeNavigation = (ListView) findViewById(R.id.list_navigation);

        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                R.string.ouvrir_menu, R.string.fermer_menu);

        drawerToggle.setDrawerIndicatorEnabled(true);

        drawerLayout.addDrawerListener(drawerToggle);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setSubtitle(R.string.titre_conifere);
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        listeNavigation.setOnItemClickListener(ecouterListeNavigation);

    }



    //Définition de la variable qui écoute la liste des coniferes
    private AdapterView.OnItemClickListener ecouterListViewConifere =
            new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView parent, View view, int position, long id) {
                    //parent.getItemAtPosition(position) permet de
                    //recuperer l'item qui a été sélectionné.
                    //String itemChoisi = parent.getItemAtPosition(position).toString();

                    //Exemple d'intention explicite.
                    //La nouvelle intention contient le contexte de l'activité
                    // appelant et le nom de l'activité.
                    Intent intent = new Intent(ConifereActivity.this, ConifereDeuxActivity.class);
                    intent.putExtra(CLE_CHOIX, position);
                    intent.putExtra(CLE_NOM, nomConifere);
                    intent.putExtra(CLE_IMAGE, nomResConifere);
                    intent.putExtra(CLE_LIENWEB, lienConifere);
                    startActivityForResult(intent,REQUETE_CHOIX_CONIFERE);
                }
            };

    private AdapterView.OnItemClickListener ecouterListeNavigation =
            new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String itemChoisi = parent.getItemAtPosition(position).toString();
                    String message = "Item : " + itemChoisi;

                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                    Intent intent = null;
                    switch (position){
                        case 0:
                            intent = new Intent(ConifereActivity.this, Calculer_acceuil.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            break;

                        case 1:
                            intent = new Intent(ConifereActivity.this, ConifereActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);

                            break;

                        case 2:

                            break;

                        default:
                            break;
                    }

                    drawerLayout.closeDrawers();
                }

            };


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data ){
        if (requestCode == REQUETE_CHOIX_CONIFERE){
            if (resultCode == Activity.RESULT_OK) {
                nomConifere = data.getStringExtra(CLE_NOM);
                nomResConifere = data.getStringExtra(CLE_IMAGE);
                lienConifere = data.getStringExtra(CLE_LIENWEB);
                String texte = (res.getString(R.string.mess_res_arbre) + "\n\n" + nomConifere);
                int resId = res.getIdentifier(nomResConifere, "drawable", getPackageName());
                idResultat.setText(texte);
                idResultat.setCompoundDrawablesWithIntrinsicBounds(resId, 0, 0, 0);
                idResultat.setOnClickListener(ecouterTextViewConifere);
            } else {
                idResultat.setText("");
                idResultat.setCompoundDrawablesWithIntrinsicBounds(
                        android.R.color.transparent, 0, 0, 0);
            }

        }
    }


    private View.OnClickListener ecouterTextViewConifere = new View.OnClickListener() {
        @Override
        public void onClick(View vue) {
            Intent intent = new Intent(ConifereActivity.this, ConifereWikiActivity.class);
            intent.putExtra(CLE_NOM, nomConifere);
            intent.putExtra(CLE_LIENWEB, lienConifere);
            startActivity(intent);
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
                Intent intent = new Intent(ConifereActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);


                traiter = true;
                break;

            case R.id.menuAide:
                AlertDialog.Builder boiteAlerte = new AlertDialog.Builder(ConifereActivity.this);

                boiteAlerte.setTitle(R.string.action_aide);
                boiteAlerte.setIcon(R.drawable.ic_info_aide);
                boiteAlerte.setMessage(R.string.aide_conifere);
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
