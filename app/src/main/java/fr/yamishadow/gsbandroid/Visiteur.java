package fr.yamishadow.gsbandroid;

/**
 * Created by chett on 06/03/2017.
 */

public class Visiteur {

    // propriété de la classe Visiteur

    private String id ;

    private String login;
    private String mdp;


    /**
     * Constructeur de visiteur
     * @param id
     * @param login
     * @param mdp

     */
    public Visiteur(String id, String login, String mdp) {
        this.id = id;
        this.login = login;
        this.mdp = mdp;

    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

}
