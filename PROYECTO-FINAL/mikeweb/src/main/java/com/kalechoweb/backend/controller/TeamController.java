package com.kalechoweb.backend.controller;

import com.kalechoweb.backend.model.Team;
import com.kalechoweb.backend.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.lang.NonNull;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class TeamController {

    @Autowired
    private TeamService teamService;

    @GetMapping("/deportes/{sportId}/equipos")
    public List<Team> getTeamsBySport(@PathVariable @NonNull String sportId) {
        return teamService.getTeamsBySport(sportId);
    }

    @PostMapping("/deportes/{sportId}/equipos")
    public ResponseEntity<Team> createTeam(@PathVariable @NonNull String sportId,
                                           @RequestParam("adminUserId") @NonNull String adminUserId,
                                           @RequestBody @NonNull Team team) {
        Team created = teamService.createTeam(adminUserId, sportId, team);
        if (created == null) {
            return ResponseEntity.status(403).build();
        }
        return ResponseEntity.ok(created);
    }

    @DeleteMapping("/deportes/{sportId}/equipos/{teamId}")
    public ResponseEntity<Void> deleteTeam(@PathVariable @NonNull String sportId,
                                           @PathVariable @NonNull String teamId,
                                           @RequestParam("adminUserId") @NonNull String adminUserId) {
        boolean ok = teamService.deleteTeam(adminUserId, sportId, teamId);
        if (!ok) {
            return ResponseEntity.status(403).build();
        }
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/equipos/{teamId}/miembros/{studentId}")
    public ResponseEntity<Void> joinTeam(@PathVariable @NonNull String teamId,
                                         @PathVariable @NonNull String studentId) {
        boolean ok = teamService.addMember(teamId, studentId);
        if (!ok) {
            return ResponseEntity.status(403).build();
        }
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/equipos/{teamId}/miembros/{studentId}")
    public ResponseEntity<Void> leaveTeam(@PathVariable @NonNull String teamId,
                                          @PathVariable @NonNull String studentId) {
        boolean ok = teamService.removeMember(teamId, studentId);
        if (!ok) {
            return ResponseEntity.status(403).build();
        }
        return ResponseEntity.noContent().build();
    }
}
