package be.uantwerpen.fti.ei.geavanceerde.towerdefence.game.ecs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Registre ECS — contient toutes les entités et leurs composants.
 * Une entité est simplement un ID (int).
 */
public class World {

    private int nextEntityId = 0;

    // Map : entityId -> (componentType -> component)
    private final Map<Integer, Map<Class<?>, Object>> entities = new HashMap<>();

    // ── Création / suppression ─────────────────────
    public int createEntity() {
        int id = nextEntityId++;
        entities.put(id, new HashMap<>());
        return id;
    }

    public void destroyEntity(int id) {
        entities.remove(id);
    }

    // ── Composants ─────────────────────────────────
    public <T> void addComponent(int entityId, T component) {
        entities.get(entityId).put(component.getClass(), component);
    }

    @SuppressWarnings("unchecked")
    public <T> Optional<T> getComponent(int entityId, Class<T> type) {
        Map<Class<?>, Object> comps = entities.get(entityId);
        if (comps == null) return Optional.empty();
        return Optional.ofNullable((T) comps.get(type));
    }

    public boolean hasComponent(int entityId, Class<?> type) {
        Map<Class<?>, Object> comps = entities.get(entityId);
        return comps != null && comps.containsKey(type);
    }

    // ── Requêtes Streams ───────────────────────────
    /**
     * Retourne tous les IDs d'entités possédant un composant donné.
     * Utilisation de Java Streams API.
     */
    public List<Integer> getEntitiesWith(Class<?> type) {
        return entities.entrySet().stream()
                .filter(e -> e.getValue().containsKey(type))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    public List<Integer> getAllEntities() {
        return new ArrayList<>(entities.keySet());
    }
}
