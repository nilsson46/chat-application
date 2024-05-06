package com.example.application;

import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
@Component
public class SessionManager {
    private Map<String, Set<String>> sessions = new ConcurrentHashMap<>();

    public void addUserToSession(String sessionId, String userId) {
        sessions.computeIfAbsent(sessionId, k -> new HashSet<>()).add(userId);
    }

    public void removeUserFromSession(String sessionId, String userId) {
        sessions.getOrDefault(sessionId, Collections.emptySet()).remove(userId);
        if (sessions.containsKey(sessionId) && sessions.get(sessionId).isEmpty()) {
            sessions.remove(sessionId);
        }
    }

}
