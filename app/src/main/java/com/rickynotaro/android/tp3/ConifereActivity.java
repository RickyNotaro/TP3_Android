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

    public static final String CLE_CHOIX = NOM_PACKAGE + ".CHOIX";
    public static final int REQUETE_CHOIX_CONIFERE = 1;

    private ListView listeNavigation;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private Resources res;

    private ListView idListeChoix;
    private TextView idResultat;

    private String conifereChoisi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_conifere);


        //Début Récupération composants
        preparerDrawer();
        recupererComposants();
    }

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
                            intent = new Intent(ConifereActivity.this, PretActivity.class);
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
            if (resultCode == Activity.RESULT_OK){
            //ConifereDeuxActivity.CLE_NOM
                idResultat.setText(R.string.mess_res_arbre + getIntent().getStringExtra(ConifereDeuxActivity.CLE_NOM));
               // idResultat.setCompoundDrawablesWithIntrinsicBounds( getIntent().getStringExtra(ConifereDeuxActivity.CLE_IMAGE), 0, 0, 0 );
                // TODO: 2016-12-09 Document 14 page 34 && PDF page 21. (Récupérer les données et les afficher dans le textview.
            }else {

            }

        }
    }

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
