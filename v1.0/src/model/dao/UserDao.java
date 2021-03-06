package model.dao;


import model.entities.User;

public interface UserDao {
    /**
     * @param username that will be checked in database
     * @param password that will be checked in database
     *
     * @return return the current User with these username
     */
    User login(String username, String password);

    /**
     * @param user that will be registered
     *
     * @return return true if cadastre was sucefull
     */
    boolean cadastrar(User user);

    void alterarUsername(User user);

    void update(User user);

    String getSenhaUser(User user);
}
