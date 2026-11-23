package net.hasagj.backend.controllers;

import net.hasagj.backend.dto.PhotoDto;
import net.hasagj.backend.services.PhotoService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/collections/{collectionId}/photos")
public class PhotoController {
    private final PhotoService photoService;

    public PhotoController(PhotoService photoService) {
        this.photoService = photoService;
    }

    @GetMapping
    public List<PhotoDto.SimpleResponse> getPhotos(@PathVariable Long collectionId) {
        return photoService.getAll(collectionId);
    }

    @GetMapping(params = "path")
    public PhotoDto.Response getPhoto(@RequestParam String path, @PathVariable Long collectionId) {
        return photoService.get(path, collectionId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PhotoDto.SimpleResponse createPhoto(@RequestParam String path, @PathVariable Long collectionId) {
        return photoService.create(path, collectionId);
    }

    @PatchMapping
    public PhotoDto.SimpleResponse updatePhoto(
            @RequestParam String oldPath,
            @PathVariable Long collectionId,
            @RequestParam String newPath
    ) {
        return photoService.update(oldPath, collectionId, newPath);
    }

    @DeleteMapping
    public PhotoDto.SimpleResponse deletePhoto(
            @RequestParam String path,
            @PathVariable Long collectionId
    ) {
        return photoService.delete(path, collectionId);
    }

}
