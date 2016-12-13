package com.rickynotaro.android.tp3;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ConifereDeuxActivity extends AppCompatActivity {

    private ListView listeNavigation;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;

    private TextView idTitreConifere;
    private Button idBtnRetour;
    private RecyclerView idRecylerView;

    private String[] tabTitres;
    private String[] tabSousTitres;

    private Resources res;
    private Intent intent;
    private int posChoix;

    public static final String NOM_PACKAGE = ConifereDeuxActivity.class.getPackage().getName();


    private ArrayList<Conifere> listeConifere;

    // Ressources array des différents textes et images des items de la liste
    // pour aider à l'identification.
    private static final int[] TAB_AIGUILLES_CONIFERES = {
            R.array.tab_aiguilles_une,
            R.array.tab_aiguilles_faisceaux,
            R.array.tab_ecailles };
    private static final int[] TAB_IMAGES_AIGUILLES_CONIFERES = {
            R.array.tab_images_aiguilles_une,
            R.array.tab_images_aiguilles_faisceaux,
            R.array.tab_images_ecailles };
    // Ressources array des différents noms, images et pages web des conifères.
    private static final int[] TAB_CONIFERES = {
            R.array.tab_coniferes_aiguilles_une,
            R.array.tab_coniferes_aiguilles_faisceaux,
            R.array.tab_coniferes_ecailles };
    private static final int[] TAB_IMAGES_CONIFERES = {
            R.array.tab_images_coniferes_aiguilles_une,
            R.array.tab_images_coniferes_aiguilles_faisceaux,
            R.array.tab_images_coniferes_ecailles };
    private static final int[] TAB_WEB_CONIFERES = {
            R.array.tab_web_coniferes_aiguilles_une,
            R.array.tab_web_coniferes_aiguilles_faisceaux,
            R.array.tab_web_coniferes_ecailles };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_coniferechoix);

        //Début Récupération composants

        recupererComposants();
        preparerDrawer();
        EcouterBtnRetour();
        idTitreConifere.setText(tabTitres[posChoix]);
        afficherEcouteListeChoix();
    }

    private void preparerDrawer() {
        // Debut Drawer
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        listeNavigation = (ListView) findViewById(R.id.list_navigation);

        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                R.string.ouvrir_menu, R.string.fermer_menu);

        drawerToggle.setDrawerIndicatorEnabled(true);

        drawerLayout.addDrawerListener(drawerToggle);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setSubtitle(tabSousTitres[posChoix]);

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        listeNavigation.setOnItemClickListener(ecouterListeNavigation);
        // Fin Drawer
    }

    private void afficherEcouteListeChoix() {
        idRecylerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        idRecylerView.setLayoutManager(linearLayoutManager);

        listeConifere = creerListeConifere();

        ConifereAdapterRV conifereAdapterRV = new ConifereAdapterRV(this, R.layout.item_liste_conifere,listeConifere);

        idRecylerView.setAdapter(conifereAdapterRV);

        conifereAdapterRV.setOnItemClickListener(new ConifereAdapterRV.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                String tabNom[] = res.getStringArray(TAB_CONIFERES[posChoix]);
                String tabImages[] = res.getStringArray(TAB_IMAGES_CONIFERES[posChoix]);
                String tabWeb[] = res.getStringArray(TAB_WEB_CONIFERES[posChoix]);



                Intent intent = new Intent(ConifereDeuxActivity.this, ConifereDeuxActivity.class);
                intent.putExtra(ConifereActivity.CLE_NOM, tabNom[position]);
                intent.putExtra(ConifereActivity.CLE_IMAGE, tabImages[position]);
                intent.putExtra(ConifereActivity.CLE_LIENWEB, tabWeb[position]);
                setResult(ConifereActivity.RESULT_OK, intent);
                finish();

            }
        });
    }

    private ArrayList<Conifere> creerListeConifere(){
        String[] tabNomsConiferes;
        String[] tabResImagesConiferes;
        ArrayList<Conifere> listeConiferes = new ArrayList<>();

        tabNomsConiferes = res.getStringArray(TAB_AIGUILLES_CONIFERES[posChoix]);
        tabResImagesConiferes = res.getStringArray(TAB_IMAGES_AIGUILLES_CONIFERES[posChoix]);
        for( int i = 0; i < tabNomsConiferes.length; i++){
            int idImage = res.getIdentifier(tabResImagesConiferes[i], "drawable", this.getPackageName());

            Conifere conifere = new Conifere(tabNomsConiferes[i], idImage);

            listeConiferes.add(conifere);
        }

        return listeConiferes;
    }

    private void recupererComposants() {
        intent = getIntent();
        posChoix = intent.getIntExtra( ConifereActivity.CLE_CHOIX,-1);

        res = getResources();
        idTitreConifere = (TextView) findViewById(R.id.textViewTitreChoix);
        idBtnRetour = (Button) findViewById(R.id.buttonRetour);
        tabTitres = res.getStringArray( R.array.tab_choix_aiguilles);
        tabSousTitres = res.getStringArray( R.array.tab_choix_sous_titres);
        idRecylerView = (RecyclerView) findViewById(R.id.recyViewConi);
    }



    private void EcouterBtnRetour() {
        // Écouter le bouton de retour.

        idBtnRetour.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick( View vue ) {
                // Rendre la main à l'activité précédente. Fin normale.
                // Ici, pas besoin de modifier ou d'ajouter des données dans l'intent.
                // Voir pages 9 à 11 du document 14.

               // setResult( Activity.RESULT_OK, intent );
                // Termine l'activité en cours.
                finish();
            }
        } );
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
                            intent = new Intent(ConifereDeuxActivity.this, Calculer_acceuil.class);
                            break;

                        case 1:
                            intent = new Intent(ConifereDeuxActivity.this, ConifereActivity.class);
                            break;

                        case 2:
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
                Intent intent = new Intent(ConifereDeuxActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);


                traiter = true;
                break;

            case R.id.menuAide:
                AlertDialog.Builder boiteAlerte = new AlertDialog.Builder(ConifereDeuxActivity.this);

                boiteAlerte.setTitle(R.string.action_aide);
                boiteAlerte.setIcon(R.drawable.ic_info_aide);
                boiteAlerte.setMessage(R.string.aide_choix_conifere);
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
