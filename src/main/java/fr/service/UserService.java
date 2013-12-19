package fr.service;

import java.util.List;

import fr.persistence.domain.User;

public interface UserService {

	/**
	 * recuperler le nombre d'utilisateurs
	 * @return
	 */
	long getNumberOfUsers();

	/**
	 * recuperer un utilisateur a partir de son login
	 * @param login
	 * @return l'objet utilisateur
	 */
    User findUser(String login);

    /**
     * recuperer une liste d'utiliseurs qui commence par "loginstart"
     * @param loginStart
     * @return une liste d'utilisateur
     */
    List<User> findUsersByLogin(String loginStart);
    
	/**
	 * recuperer l'utilisateur connecté à partir du context
	 * @return l'utilisateur connecté
	 * @throws UserNonAuthentifierException 
	 */
    User getCurrentUser();

    /**
     * maj d'un utilisateur
     * @param user
     */
    void updateUser(User user);
    
	/**
	 * creer un nouveau utilisateur
	 * @param user
	 */
    void createUser(User user);
}
