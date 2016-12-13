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
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
 
 
public class Calculer_acceuil extends AppCompatActivity implements View.OnClickListener {
 
    private ListView listeNavigation;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private Resources res;
    private Spinner spinner_duree;
    private EditText hint_pret;
    private EditText hint_taux;
    private Button calculer;
    private Button effacer;
    private Pret pret;
    private PopupMenu popupMenu;

    // recupere les différentes composantes
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        res = getResources();
        exempleDrawerLayout();
        hint_pret = (EditText) findViewById(R.id.entrerMontant);
        hint_taux = (EditText) findViewById(R.id.entrerTaux);
        calculer = (Button) findViewById(R.id.boutonCalculer);
        effacer = (Button) findViewById(R.id.bouttonEffacer);
        pret = new Pret();

        spinner_duree = (Spinner) findViewById(R.id.spinner_dure);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.tab_duree, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_duree.setAdapter(adapter);

        spinner_duree.setOnItemSelectedListener(ecouterSpinner);
        pret.setDuree(Integer.parseInt(spinner_duree.getSelectedItem().toString().substring(0, 2)));

        calculer.setOnClickListener(this);
        effacer.setOnClickListener(this);


    }

    // methode qui ecoute les boutons calculer et effacer
    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.boutonCalculer:

                if (hint_pret.getText().toString().isEmpty() || hint_taux.getText().toString().isEmpty()
                        || Double.parseDouble(hint_pret.getText().toString()) <= 0 || Double.parseDouble(hint_taux.getText().toString()) <= 0) {
                    Toast.makeText(getApplicationContext(), R.string.warning_infos_pret, Toast.LENGTH_SHORT).show();
                } else {
                    pret.setMontant(Double.parseDouble(hint_pret.getText().toString()));
                    pret.setInteret(Double.parseDouble(hint_taux.getText().toString()));
                    Intent intent = new Intent(Calculer_acceuil.this, Calcule_2.class);
                    intent.putExtra("cle_pret", pret);
                    startActivity(intent);
                }

                break;
            case R.id.bouttonEffacer:
                popupMenu = new PopupMenu(this, effacer);
                popupMenu.getMenuInflater().inflate(R.menu.menue_contextuelle, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(ecouterPopupMenu);
                effacer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view1) {
                        popupMenu.show();
                    }
                });

        }

    }
// methode qui crée le menu popup
    PopupMenu.OnMenuItemClickListener ecouterPopupMenu = new PopupMenu.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            boolean traiter = false;
            switch (item.getItemId()) {
                case R.id.Effacer_montant:
                    hint_pret.setText("");
                    hint_pret.setHint(R.string.hint_montant);
                    traiter = true;
                    break;
                case R.id.Effacer_interet:
                    hint_taux.setText("");
                    hint_taux.setHint(R.string.hint_interet);
                    traiter = true;
                    break;
                case R.id.Effacer_montant_interet:
                    hint_pret.setText("");
                    hint_pret.setHint(R.string.hint_montant);
                    hint_taux.setText("");
                    hint_taux.setHint(R.string.hint_interet);
                    traiter = true;
                    break;

            }

            return traiter;
        }
    };

// méthode qui ecoute le spinner de la durée pour le calcul
    private AdapterView.OnItemSelectedListener ecouterSpinner = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            String itemChoisie = parent.getItemAtPosition(position).toString().substring(0, 2);
            pret.setDuree(Integer.parseInt(itemChoisie));

        }
            // méthode pour si aucune durée n'est sélectionné
        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            Toast.makeText(getApplicationContext(), "disparaît",
                    Toast.LENGTH_SHORT).show();
        }
    };
// methode qui crée les icone pour les action du menue
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_barre_actions, menu);
        return true;
    }

// méthode qui écoute les bouton retour a la page d'acceuil et aide du menue
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean traiter = super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.menuAccueil:
                Intent intent = new Intent(Calculer_acceuil.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);

                traiter = true;
                break;
            case R.id.menuAide:
                AlertDialog.Builder boiteAlert = new AlertDialog.Builder(Calculer_acceuil.this);
                boiteAlert.setTitle(R.string.action_aide);
                boiteAlert.setIcon(R.drawable.ic_info_aide);
                boiteAlert.setMessage(R.string.aide_pret);
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

            default:
                break;
        }
        if (drawerToggle.onOptionsItemSelected(item)) {
            traiter = true;
        }
        return traiter;
    }

    // méthode qui crée le drawerlayout
    private void exempleDrawerLayout() {
        setContentView(R.layout.calculer_un_pret);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        listeNavigation = (ListView) findViewById(R.id.list_navigation);

        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.ouvrir_menu, R.string.fermer_menu);
        drawerToggle.setDrawerIndicatorEnabled(true);
        drawerLayout.addDrawerListener(drawerToggle);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.titre_pret);

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        listeNavigation.setOnItemClickListener(ecouterListeNavigation);


    }
// méthode qui écoute la selection de l'activité dans le drawerlayout
private AdapterView.OnItemClickListener ecouterListeNavigation = new AdapterView.OnItemClickListener() {
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = null;

        switch (position) {
            case 0:
                intent = new Intent(Calculer_acceuil.this, Calculer_acceuil.class);
                break;
            case 1:
                intent = new Intent(Calculer_acceuil.this, ConifereActivity.class);
            case 2:
                intent = new Intent(Calculer_acceuil.this, Activite_3.class);
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