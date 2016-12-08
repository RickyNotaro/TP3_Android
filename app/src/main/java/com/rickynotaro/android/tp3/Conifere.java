/**
 * Auteure : Soti
 * Fichier : Conifere.java
 * Date    : 8 décembre 2016
 * Cours   : 420-254-MO (TP3 Android)
 */

/**
 * Classe contenant les données et les méthodes pour un conifère.
 */

package com.rickynotaro.android.tp3;

public class Conifere {

	// Champs d'instance pour un conifère.

	private String nom;  // Nom du conifère avec une description de ses aiguilles.
	private int idImage; // L'identifiant drawable pour l'image des aiguilles du conifère.

	// Constructeur de base.
	public Conifere() {
		this( "", 0 );
	}

	// Constructeur avec des valeurs.
	public Conifere( String nom, int idImage ) {
		this.setNom( nom );
		this.setIdImage( idImage );
	}

	// Accesseur et mutateur pour le nom du conifère avec une description de ses aiguilles.

	public String getNom() {
		return nom;
	}

	public void setNom( String nom ) {
		this.nom = nom;
	}

	// Accesseur et mutateur pour l'identifiant de l'image des aiguilles du conifère.

	public int getIdImage() {
		return idImage;
	}

	public void setIdImage( int idImage ) {
		this.idImage = idImage;
	}

	// Redéfinition de la méthode toString.
	@Override
	public String toString() {
		return "[" + this.getNom() + ", " + this.getIdImage() + "]";
	}
}
