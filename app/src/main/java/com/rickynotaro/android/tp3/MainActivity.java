package com.rickynotaro.android.tp3;

import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.Toast;

/**
 * Auteurs : Ricky Notaro-Garcia
 * Fichier : ConifereActivity.java
 * Date    : 12 décembre 2016
 * Cours   : 420-254-MO (TP3 Android)
 */

/**
 * Classe MainActivity contenant la page d'accueil de l'application".
 *
 */

public class MainActivity extends AppCompatActivity {

    private ListView listeNavigation;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        // Debut Drawer
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        listeNavigation = (ListView) findViewById(R.id.list_navigation);

        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                R.string.ouvrir_menu, R.string.fermer_menu);

        drawerToggle.setDrawerIndicatorEnabled(true);

        drawerLayout.addDrawerListener(drawerToggle);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        listeNavigation.setOnItemClickListener(ecouterListeNavigation);

        // Fin Drawer
    }


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
                            intent = new Intent(MainActivity.this, Calculer_acceuil.class);
                            break;

                        case 1:
                            intent = new Intent(MainActivity.this, ConifereActivity.class);
                            break;

                        case 2:
                            intent = new Intent(MainActivity.this, Activite_3.class);
                            break;

                        default:
                            break;
                    }
                    if (intent != null) {
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
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
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                traiter = true;
                break;

            case R.id.menuAide:
                AlertDialog.Builder boiteAlerte = new AlertDialog.Builder(MainActivity.this);

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
