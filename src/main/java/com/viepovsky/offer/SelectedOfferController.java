package com.viepovsky.offer;

import com.viepovsky.offer.dto.SelectedOfferCreateRequest;
import com.viepovsky.offer.dto.SelectedOfferDto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/v1/selected-offer")
@RequiredArgsConstructor
@Validated
class SelectedOfferController {
    private final SelectedOfferFacade selectedOfferFacade;

    @GetMapping
    ResponseEntity<List<SelectedOfferDto>> getAllSelectedOffers(
            @RequestParam @NotBlank String username) {
        return ResponseEntity.ok(selectedOfferFacade.getAllSelectedOffers(username));
    }

    @PostMapping
    ResponseEntity<SelectedOfferDto> createSelectedOffer(
            @Valid @RequestBody SelectedOfferCreateRequest request,
            @RequestParam(name = "username") @NotBlank String username) {
        var createdSelectedOffer = selectedOfferFacade.createSelectedOffer(request, username);
        return ResponseEntity.created(URI.create("/v1/selected-offer?username=" + username))
                .body(createdSelectedOffer);
    }

    @DeleteMapping(path = "/{selectedOfferId}")
    ResponseEntity<Void> deleteSelectedOffer(@PathVariable @Min(1) Long selectedOfferId) {
        selectedOfferFacade.deleteSelectedOffer(selectedOfferId);
        return ResponseEntity.ok().build();
    }
}
