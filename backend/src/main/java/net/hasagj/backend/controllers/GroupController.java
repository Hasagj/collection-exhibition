package net.hasagj.backend.controllers;

import jakarta.validation.Valid;
import net.hasagj.backend.dto.CardDto;
import net.hasagj.backend.dto.GroupDto;
import net.hasagj.backend.dto.PhotoDto;
import net.hasagj.backend.services.GroupService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/collections/{collectionId}/groups")
public class GroupController {
    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping
    public List<GroupDto.SimpleResponse> getGroups(@PathVariable Long collectionId) {
        return groupService.getAll(collectionId);
    }

    @GetMapping("/{id}")
    public GroupDto.Response getGroup(@PathVariable Long id, @PathVariable Long collectionId) {
        return groupService.get(id, collectionId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GroupDto.Response createGroup(
            @PathVariable Long collectionId,
            @Valid @RequestBody GroupDto.CreateRequest createRequest
    ) {
        return groupService.create(collectionId, createRequest);
    }

    @PatchMapping("/{id}")
    public GroupDto.Response updateGroup(
            @PathVariable Long id,
            @PathVariable Long collectionId,
            @Valid @RequestBody GroupDto.UpdateRequest updateRequest
    ) {
        return groupService.update(id, collectionId, updateRequest);
    }

    @DeleteMapping("/{id}")
    public GroupDto.SimpleResponse deleteGroup(@PathVariable Long id, @PathVariable Long collectionId) {
        return groupService.delete(id, collectionId);
    }

    @PostMapping("/{id}/photos")
    public PhotoDto.Response addPhotoToGroup(
            @PathVariable Long id,
            @PathVariable Long collectionId,
            @RequestParam String path
    ) {
        return groupService.addPhoto(id, collectionId, path);
    }

    @DeleteMapping("/{id}/photos")
    public PhotoDto.Response removePhotoFromGroup(
            @PathVariable Long id,
            @PathVariable Long collectionId
    ) {
        return groupService.removePhoto(id, collectionId);
    }

    @PostMapping("/{id}/cards/{cardId}")
    public CardDto.SimpleResponse addCardToGroup(
            @PathVariable Long id,
            @PathVariable Long collectionId,
            @PathVariable Long cardId,
            @RequestParam(required = false, defaultValue = "0") Integer position
    ) {
        return groupService.addCard(id, collectionId, cardId, position);
    }

    @PutMapping("/{id}/cards/{cardId}")
    public CardDto.SimpleResponse moveCardInsideGroup(
            @PathVariable Long id,
            @PathVariable Long collectionId,
            @PathVariable Long cardId,
            @RequestParam(required = false, defaultValue = "0") Integer position
    ) {
        return groupService.moveCard(id, collectionId, cardId, position);
    }

    @DeleteMapping("/{id}/cards/{cardId}")
    public CardDto.SimpleResponse removeCardFromGroup(
            @PathVariable Long id,
            @PathVariable Long collectionId,
            @PathVariable Long cardId
    ) {
        return groupService.removeCard(id, collectionId, cardId);
    }
}
