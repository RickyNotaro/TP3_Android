package com.rickynotaro.android.tp3;

/**
 * Auteurs : Ricky Notaro-Garcia
 * Fichier : ConifereActivity.java
 * Date    : 12 décembre 2016
 * Cours   : 420-254-MO (TP3 Android)
 */

/**
 * Classe Activity gérant la WebView des conifères".
 **
 */
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;


public class ConifereWikiActivity extends AppCompatActivity {

    private ListView listeNavigation;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;

    private String sousTitre;
    private String url;

    private Resources res;
    private Intent intent;

    private WebView vueWeb;
    private WebSettings param;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_coniferewiki);

        //Début Récupération composants

        recupererComposants();
        preparerDrawer();
        gestionWebview();
    }

    private void gestionWebview() {
        param = vueWeb.getSettings();
        param.setJavaScriptEnabled(true);
        param.setBuiltInZoomControls(true);
        vueWeb.setWebChromeClient(ecouterPandantChargementWeb);
        vueWeb.setWebViewClient(ecouterFinChargementWeb);
        vueWeb.loadUrl(url);
    }

    WebChromeClient ecouterPandantChargementWeb = new WebChromeClient() {
        @Override
        public void onProgressChanged (WebView view, int progress){
            Toast.makeText(getApplicationContext(), Integer.toString(progress) + "%",
                    Toast.LENGTH_SHORT).show();
        }
    };

    WebViewClient ecouterFinChargementWeb = new WebViewClient() {
        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error){
            String messErreur = res.getString(R.string.erreur_chargement_web);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                messErreur = messErreur.concat(error.getDescription().toString());
            }

            Toast.makeText(getApplicationContext(), messErreur, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onPageFinished(WebView view, String url){
            Toast.makeText(getApplicationContext(),url,Toast.LENGTH_SHORT).show();
        }
    };


    private void preparerDrawer() {
        // Debut Drawer
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        listeNavigation = (ListView) findViewById(R.id.list_navigation);

        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                R.string.ouvrir_menu, R.string.fermer_menu);

        drawerToggle.setDrawerIndicatorEnabled(true);

        drawerLayout.addDrawerListener(drawerToggle);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setSubtitle(sousTitre);

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        listeNavigation.setOnItemClickListener(ecouterListeNavigation);
        // Fin Drawer
    }


    private void recupererComposants() {
        intent = getIntent();
        sousTitre = intent.getStringExtra(ConifereActivity.CLE_NOM);
        url = intent.getStringExtra(ConifereActivity.CLE_LIENWEB);
        res = getResources();
        vueWeb = (WebView) findViewById(R.id.vueWeb);
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
                            intent = new Intent(ConifereWikiActivity.this, Calculer_acceuil.class);
                            break;

                        case 1:
                            intent = new Intent(ConifereWikiActivity.this, ConifereActivity.class);
                            break;

                        case 2:
                            intent = new Intent(ConifereWikiActivity.this, Activite_3.class);
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
                Intent intent = new Intent(ConifereWikiActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);


                traiter = true;
                break;

            case R.id.menuAide:
                AlertDialog.Builder boiteAlerte = new AlertDialog.Builder(ConifereWikiActivity.this);

                boiteAlerte.setTitle(R.string.action_aide);
                boiteAlerte.setIcon(R.drawable.ic_info_aide);
                boiteAlerte.setMessage(R.string.aide_web_conifere);
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
