package net.hasagj.backend.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CollectionsController {

    @GetMapping("/collections")
    public String getCollections() {
        return "all collections";
    }

    @GetMapping("/collections/{id}")
    public String getCollectionById(@PathVariable Integer id) {
        return "collection №" + id;
    }
}
