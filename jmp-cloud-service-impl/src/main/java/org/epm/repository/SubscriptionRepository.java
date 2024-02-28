package org.epm.repository;

import org.epm.Subscription;
import org.epm.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    void deleteAllByUser(User user);
}