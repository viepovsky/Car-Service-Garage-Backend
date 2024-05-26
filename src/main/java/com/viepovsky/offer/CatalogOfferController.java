package com.viepovsky.offer;

import com.viepovsky.offer.dto.CatalogOfferCreateRequest;
import com.viepovsky.offer.dto.CatalogOfferDto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import lombok.RequiredArgsConstructor;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/v1/catalog-offer")
@RequiredArgsConstructor
@Validated
class CatalogOfferController {

    private final CatalogOfferFacade availableCarRepairFacade;

    @GetMapping(path = "/{garageId}")
    ResponseEntity<List<CatalogOfferDto>> getAllCatalogOffers(@PathVariable @Min(1) Long garageId) {
        return ResponseEntity.ok(availableCarRepairFacade.getAllCatalogOffers(garageId));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<CatalogOfferDto> createCatalogOffer(
            @Valid @RequestBody CatalogOfferCreateRequest request,
            @RequestParam(name = "garage-id") @NotNull @Min(1) Long garageId) {
        availableCarRepairFacade.createCatalogOffer(request, garageId);
        return ResponseEntity.created(URI.create("/v1/catalog-offer/" + garageId)).build();
    }

    @DeleteMapping(path = "/{catalogOfferId}")
    ResponseEntity<Void> deleteCatalogOffer(@PathVariable @Min(1) Long catalogOfferId) {
        availableCarRepairFacade.deleteCatalogOffer(catalogOfferId);
        return ResponseEntity.ok().build();
    }
}
