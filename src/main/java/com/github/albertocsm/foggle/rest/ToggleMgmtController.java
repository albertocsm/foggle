package com.github.albertocsm.foggle.rest;


import com.github.albertocsm.foggle.bll.dto.Toggle;
import com.github.albertocsm.foggle.bll.dto.ToggleCriteria;
import com.github.albertocsm.foggle.bll.dto.ToggleReference;
import com.github.albertocsm.foggle.bll.ToggleReferenceService;
import com.github.albertocsm.foggle.bll.ToggleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping("/dashboard/toggle")
public class ToggleMgmtController {

    @Autowired
    private ToggleService toggleSrv;
    @Autowired
    private ToggleReferenceService toggleRefSrv;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> createToggle(@RequestBody @Valid final Toggle request) {

        UUID id = toggleSrv.createToggle(request);
        URI link = linkTo(methodOn(ToggleMgmtController.class).findToggle(id)).toUri();

        return ResponseEntity.status(HttpStatus.CREATED).body(link);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "{toggleId}")
    public ResponseEntity<?> updateToggle(@PathVariable("toggleId") final UUID toggleId, @RequestBody @Valid final Toggle request) {

        toggleSrv.updateToggle(toggleId, request);

        URI link = linkTo(methodOn(ToggleMgmtController.class).findToggle(toggleId)).toUri();

        return ResponseEntity.ok(link);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "{toggleId}")
    public ResponseEntity<Void> deleteToggle(@PathVariable("toggleId") final UUID toggleId) {

        toggleSrv.deleteToggle(toggleId);

        return ResponseEntity.noContent().build();
    }

    @RequestMapping(method = RequestMethod.GET, value = "{toggleId}")
    public ResponseEntity<Toggle> findToggle(@PathVariable("toggleId") final UUID toggleId) {

        Toggle result = toggleSrv.findToggle(toggleId);

        result.add(linkTo(methodOn(ToggleMgmtController.class).findToggle(result.getResourceId())).withSelfRel());
        result.add(linkTo(methodOn(ToggleMgmtController.class).filterToggleRef(result.getResourceId())).withRel("references"));

        return ResponseEntity.ok(result);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Collection<Toggle>> filterToggle(@Valid final ToggleCriteria criteria) {

        Collection<Toggle> toggleCollection = toggleSrv.filterToggle(criteria);
        toggleCollection
            .stream()
            .forEach(e -> {
                e.add(linkTo(methodOn(ToggleMgmtController.class).findToggle(e.getResourceId())).withSelfRel());
                e.add(linkTo(methodOn(ToggleMgmtController.class).filterToggleRef(e.getResourceId())).withRel("references"));
            });

        return ResponseEntity.ok(toggleCollection);
    }

    @RequestMapping(method = RequestMethod.POST, value = "{toggleId}/reference")
    public ResponseEntity<?> createToggleRef(
        @PathVariable("toggleId") final UUID toggleId,
        @RequestBody @Valid final Collection<ToggleReference> request) {

        Collection<UUID> toggleRef = toggleRefSrv.createToggleRef(toggleId, request);

        Collection<URI> links = new ArrayList<>();
        toggleRef.forEach(e -> links.add(linkTo(methodOn(ToggleMgmtController.class).findToggleRef(toggleId, e)).toUri()));

        return ResponseEntity.status(HttpStatus.CREATED).body(links);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "{toggleId}/reference")
    public ResponseEntity<?> updateToggleRef(
        @PathVariable("toggleId") final UUID toggleId,
        @RequestBody @Valid final Collection<ToggleReference> request) {

        toggleRefSrv.updateToggleRef(toggleId, request);

        Collection<URI> links = new ArrayList<>();
        request.forEach(e -> links.add(linkTo(methodOn(ToggleMgmtController.class).findToggleRef(toggleId, e.getResourceId())).toUri()));

        return ResponseEntity.ok(links);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "{toggleId}/reference")
    public ResponseEntity<Void> deleteToggleRef(
        @PathVariable("toggleId") final UUID toggleId,
        @RequestBody @Valid final Collection<UUID> request) {

        toggleRefSrv.deleteToggleRef(toggleId, request);

        return ResponseEntity.noContent().build();
    }

    @RequestMapping(method = RequestMethod.GET, value = "{toggleId}/reference/{referenceId}")
    public ResponseEntity<ToggleReference> findToggleRef(
        @PathVariable("toggleId") final UUID toggleId,
        @PathVariable("referenceId") final UUID referenceId) {

        ToggleReference result = toggleRefSrv.findToggleRef(toggleId, referenceId);

        result.add(linkTo(methodOn(ToggleMgmtController.class).findToggleRef(result.getToggleId(), result.getResourceId())).withSelfRel());

        return ResponseEntity.ok(result);
    }

    @RequestMapping(method = RequestMethod.GET, value = "{toggleId}/reference")
    public ResponseEntity<Collection<ToggleReference>> filterToggleRef(@PathVariable("toggleId") final UUID toggleId) {

        Collection<ToggleReference> toggleReferenceCollection = toggleRefSrv.filterToggleRef(toggleId);
        toggleReferenceCollection
            .stream()
            .forEach(e -> e.add(linkTo(methodOn(ToggleMgmtController.class).findToggleRef(e.getToggleId(), e.getResourceId())).withSelfRel()));

        return ResponseEntity.ok(toggleReferenceCollection);
    }
}
