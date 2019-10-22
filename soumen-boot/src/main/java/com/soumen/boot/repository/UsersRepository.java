package com.soumen.boot.repository;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

import com.soumen.boot.model.User;

@Transactional
public interface UsersRepository extends CrudRepository<User, Long> {

  /**
   * Return the user having the passed email or null if no user is found.
   * 
   * @param email the user email.
   */
  public User findByEmail(String email);

}