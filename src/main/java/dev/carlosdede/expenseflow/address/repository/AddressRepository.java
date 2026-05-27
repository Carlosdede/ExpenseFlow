package dev.carlosdede.expenseflow.address.repository;

import dev.carlosdede.expenseflow.address.entity.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AddressRepository extends JpaRepository<AddressEntity, UUID> {
    Optional<AddressEntity> findByUserById(UUID userId);

}
