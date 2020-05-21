package com.shopp.repositories;

import com.shopp.domain.Credential;
import com.shopp.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CredentialRepository extends JpaRepository<Credential, Long> {

    Credential findByUsername(String username);

}
