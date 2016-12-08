/**
 * Auteure : Soti
 * Fichier : ConifereAdapterRV.java
 * Date    : 8 décembre 2016
 * Cours   : 420-254-MO (TP3 Android)
 *
 * Classe représentant l'adaptateur personnalisé de la liste des conifères avec RecyclerView.
 * L'adaptateur va gérer les items de la RecyclerView.
 * Cette classe hérite de la classe RecyclerView.Adapter, on lui passe notre ViewHolder maison.
 * Le remplissage des données sera effectué à l'aide d'un tableau.
 * Chaque élément de la liste représente un objet Conifere.
 */

package com.rickynotaro.android.tp3;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class ConifereAdapterRV extends RecyclerView.Adapter<ConifereAdapterRV.ConifereViewHolder> {

    private Context context;
    private int layoutPersoId;
    private ArrayList<Conifere> tabConiferes;

    // Écouteur de type de l'interface interne.
    private OnItemClickListener monItemClickListener;

    // Interface interne maison pour l'écouteur.
    // La méthode abstraite onItemClick sera implémentée dans l'activité.

    public static interface OnItemClickListener {
        public abstract void onItemClick(View v, int position);
    }

    // Méthode maison pour affecter l'écouteur.
    public void setOnItemClickListener( OnItemClickListener listener ) {
        this.monItemClickListener = listener;
    }

    /**
     * Classe interne qui contient les références sur les vues qu'un objet de la
     * classe Conifere a besoin (TextView). Voir le layout item_liste_conifere.
     * Cette classe hérite de la classe RecyclerView.ViewHolder.
     */
    public static class ConifereViewHolder extends RecyclerView.ViewHolder {

        // Éléments graphiques d'un objet Conifere (un item de la liste).
        protected TextView vueConifere;

        /**
         * Constructeur de la classe interne qui initialise les éléments graphiques.
         *
         * @param itemView Un item de la liste.
         */
        public ConifereViewHolder( View itemView ) {
            super( itemView );

            // Récupérer l'élément graphique du layout de l'item.

            vueConifere = (TextView) itemView.findViewById( R.id.id_item_conifere );
        }
    }

    /**
     * Constructeur de la classe pour instancier l'adaptateur.
     *
     * @param context        Contexte de la vue.
     * @param layoutPersoId  Layout personnalisé de chaque item de la liste.
     * @param tabConiferes   Tableau des données à insérer dans la liste.
     */
    public ConifereAdapterRV( Context context, int layoutPersoId, ArrayList<Conifere> tabConiferes ) {

        this.tabConiferes = tabConiferes;
        this.layoutPersoId = layoutPersoId;
        this.context = context;
    }

    /**
     * Implémentation de la méthode qui permet de charger le layout pour un item de la liste et
     * d'initialiser un ViewHolder.
     * Invoquée par le LayoutManager pour créer une nouvelle instance de ViewHolder.
     *
     * @param parent    Vue parente dans laquelle la nouvelle vue va être ajoutée.
     * @param viewType  Le type de la nouvelle vue.
     * @return          Une nouvelle vue (ViewHolder).
     */
    @Override
    public ConifereViewHolder onCreateViewHolder( ViewGroup parent, int viewType ) {
        // Charger le layout pour un item de la liste.
        View itemView = LayoutInflater.from( parent.getContext() )
                                                    .inflate( layoutPersoId, parent, false );

        // Initialiser le ViewHolder.
        return new ConifereViewHolder( itemView );
    }

    /**
     * Implémentation de la méthode qui retourne le nombre d'éléments à afficher.
     * Ici, le nombre de conifères représente le nombre d'items à afficher.
     * Invoquée par le LayoutManager.
     */
    @Override
    public int getItemCount() {
        return tabConiferes.size();
    }

    /**
     * Implémentation de la méthode qui récupère la vue à appliquer à une position.
     * C'est ici que l'on met à jour les données.
     * Invoquée par le LayoutManager pour remplacer une vue.
     *
     * @param conifereViewHolder La vue qui est récupérée.
     * @param position           La position dans la liste.
     */
    @Override
    public void onBindViewHolder( ConifereViewHolder conifereViewHolder, final int position ) {

        // Quel item de la liste est cliqué.

        // Récupérer l'objet correspondant à la position.
        final Conifere conifereChoisi = this.tabConiferes.get( position );

        // Mettre à jour le nom et l'image du conifère.
        // Fait le lien entre le ViewHolder et les données.

        conifereViewHolder.vueConifere.setText(conifereChoisi.getNom());
        // Mettre une image dans le TextView (à gauche du texte).
        conifereViewHolder.vueConifere.setCompoundDrawablesWithIntrinsicBounds(
                conifereChoisi.getIdImage(), 0, 0, 0);

        // Écouteur quand l'utilisateur clique sur l'item.
        conifereViewHolder.itemView.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick( View v ) {
                if ( monItemClickListener != null ) {
                    // La méthode onItemClick est implémentée dans l'activité.
                    monItemClickListener.onItemClick( v, position );
                }
            }
        });

    }
}
