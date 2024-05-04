package com.viepovsky.offer;

import com.viepovsky.offer.model.SelectedOffer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface SelectedOfferRepository extends JpaRepository<SelectedOffer, Long> {
//    List<CarRepair> findCarServicesByUserId(Long userId);
//    List<OfferSelected> findCarServicesByName(Long valueToChangeTODO);
    //TODO fix it
//    List<OfferSelected> findAllOfferSelected(Long valueToChangeTODO);

}
