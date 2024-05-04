package com.viepovsky.offer;

import com.viepovsky.offer.model.CatalogOffer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface CatalogOfferRepository extends JpaRepository<CatalogOffer, Long> {
    List<CatalogOffer> findAllByGarageId(Long garageId);
}
