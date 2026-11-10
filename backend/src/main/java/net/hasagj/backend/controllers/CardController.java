package net.hasagj.backend.controllers;

import net.hasagj.backend.models.Card;
import net.hasagj.backend.services.CardService;
import net.hasagj.backend.services.PhotoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/collections")
public class CardController {
    private final CardService cardService;
    private final PhotoService photoService;

    private CardController(CardService cardService, PhotoService photoService) {
        this.cardService = cardService;
        this.photoService = photoService;
    }

    @GetMapping("/{collectionId}/cards")
    public ResponseEntity<List<Card>> getCards(@PathVariable Long collectionId) {
        List<Card> cards = cardService.getAll(collectionId);
        return ResponseEntity.ok(cards);
    }


}
