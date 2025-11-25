package com.kalechoweb.backend.service;

import com.kalechoweb.backend.model.Team;
import com.kalechoweb.backend.model.User;
import com.kalechoweb.backend.repository.TeamRepository;
import com.kalechoweb.backend.repository.SportRepository;
import com.kalechoweb.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;

@Service
public class TeamService {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private SportRepository sportRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Team> getTeamsBySport(@NonNull String sportId) {
        return teamRepository.findBySportId(sportId);
    }

    public Optional<Team> getTeamById(@NonNull String teamId) {
        return teamRepository.findById(teamId);
    }

    public Team createTeam(@NonNull String adminUserId, @NonNull String sportId, @NonNull Team team) {
        Optional<User> adminOpt = userRepository.findById(adminUserId);
        if (adminOpt.isEmpty() || adminOpt.get().getRole() == null || !adminOpt.get().getRole().equals("ADMIN")) {
            return null;
        }
        if (sportRepository.findById(sportId).isEmpty()) {
            return null;
        }
        team.setSportId(sportId);
        return teamRepository.save(team);
    }

    public boolean deleteTeam(@NonNull String adminUserId, @NonNull String sportId, @NonNull String teamId) {
        Optional<User> adminOpt = userRepository.findById(adminUserId);
        if (adminOpt.isEmpty() || adminOpt.get().getRole() == null || !adminOpt.get().getRole().equals("ADMIN")) {
            return false;
        }
        Optional<Team> teamOpt = teamRepository.findById(teamId);
        if (teamOpt.isEmpty() || !sportId.equals(teamOpt.get().getSportId())) {
            return false;
        }
        teamRepository.deleteById(teamId);
        return true;
    }

    public boolean addMember(@NonNull String teamId, @NonNull String studentId) {
        Optional<User> userOpt = userRepository.findById(studentId);
        if (userOpt.isEmpty() || userOpt.get().getRole() == null || !userOpt.get().getRole().equals("STUDENT")) {
            return false;
        }
        return teamRepository.findById(teamId).map(team -> {
            List<String> members = team.getMemberIds();
            if (!members.contains(studentId)) {
                members.add(studentId);
                team.setMemberIds(members);
                teamRepository.save(team);
            }
            return true;
        }).orElse(false);
    }

    public boolean removeMember(@NonNull String teamId, @NonNull String studentId) {
        Optional<User> userOpt = userRepository.findById(studentId);
        if (userOpt.isEmpty() || userOpt.get().getRole() == null || !userOpt.get().getRole().equals("STUDENT")) {
            return false;
        }
        return teamRepository.findById(teamId).map(team -> {
            List<String> members = team.getMemberIds();
            if (members.contains(studentId)) {
                members.remove(studentId);
                team.setMemberIds(members);
                teamRepository.save(team);
            }
            return true;
        }).orElse(false);
    }
}
