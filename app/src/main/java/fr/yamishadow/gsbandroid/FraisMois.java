package fr.yamishadow.gsbandroid;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Classe métier contenant les informations des frais d'un mois
 *
 */
public class FraisMois implements Serializable {

	private Integer mois ; // mois concerné
	private Integer annee ; // année concernée
	private Integer etape ; // nombre d'étapes du mois
	private Integer km ; // nombre de km du mois
	private Integer nuitee ; // nombre de nuitées du mois
	private Integer repas ; // nombre de repas du mois
	private ArrayList<FraisHf> lesFraisHf ; // liste des frais hors forfait du mois
	
	public FraisMois(Integer annee, Integer mois) {
		this.annee = annee ;
		this.mois = mois ;
		this.etape = 0 ;
		this.km = 0 ;
		this.nuitee = 0 ;
		this.repas = 0 ;
		lesFraisHf = new ArrayList<FraisHf>() ;
	}

	/**
	 * Ajout d'un frais hors forfait
	 * @param montant
	 * @param motif
	 */
	public void addFraisHf(Integer montant, String motif, Integer jour) {
		lesFraisHf.add(new FraisHf(montant, motif, jour)) ;
	}
	
	/**
	 * Suppression d'un frais hors forfait
	 * @param index
	 */
	public void supprFraisHf(Integer index) {
		lesFraisHf.remove(index) ;
	}

	/**
	 * getter mois du fraismois
	 * @return
     */
	public Integer getMois() {
		return mois;
	}

	/**
	 * setter sur le mois
	 * @param mois
     */
	public void setMois(Integer mois) {
		this.mois = mois;
	}

	/**
	 * getter sur l'annee
	 * @return
     */
	public Integer getAnnee() {
		return annee;
	}

	/**
	 * setter sur l'annee
	 * @param annee
     */
	public void setAnnee(Integer annee) {
		this.annee = annee;
	}

	/**
	 * getter sur l'etape
	 * @return
     */
	public Integer getEtape() {
		return etape;
	}

	/**
	 * setter sur l'etape
	 * @param etape
     */
	public void setEtape(Integer etape) {
		this.etape = etape;
	}

	/**
	 * getter sur le km
	 * @return
     */
	public Integer getKm() {
		return km;
	}

	/**
	 * setter sur le km
	 * @param km
     */
	public void setKm(Integer km) {
		this.km = km;
	}

	/**
	 * getter sur la nuitee
	 * @return
     */
	public Integer getNuitee() {
		return nuitee;
	}

	/**
	 * setter sur la nuitee
	 * @param nuitee
     */
	public void setNuitee(Integer nuitee) {
		this.nuitee = nuitee;
	}

	/**
	 * getter sur le repas
	 * @return
     */
	public Integer getRepas() {
		return repas;
	}

	/**
	 * setter sur le repas
	 * @param repas
     */
	public void setRepas(Integer repas) {
		this.repas = repas;
	}

	/**
	 * getter sur les fraishf
	 * @return
     */
	public ArrayList<FraisHf> getLesFraisHf() {
		return lesFraisHf ;
	}
	
}
