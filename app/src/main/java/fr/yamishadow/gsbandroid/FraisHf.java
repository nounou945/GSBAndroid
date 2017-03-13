package fr.yamishadow.gsbandroid;

import java.io.Serializable;

/**
 * Classe métier contenant la description d'un frais hors forfait
 *
 */
public class FraisHf  implements Serializable {

	private Integer montant ;
	private String motif ;
	private Integer jour ;
	
	public FraisHf(Integer montant, String motif, Integer jour) {
		this.montant = montant ;
		this.motif = motif ;
		this.jour = jour ;
	}


	/**
	 * getter du montant des frais hf
	 * @return
     */
	public Integer getMontant() {
		return montant;
	}

	/**
	 * getter du motif du frais hf
	 * @return
     */
	public String getMotif() {
		return motif;
	}

	/**
	 * getter sur le jour du frais hf
	 * @return
     */
	public Integer getJour() {
		return jour;
	}

}
