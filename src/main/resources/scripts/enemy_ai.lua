-- Script Lua : comportement IA des ennemis
-- Appelé par le MovementSystem à chaque tick

-- Calcule un modificateur de vitesse selon la vague
function getSpeedMultiplier(waveNumber)
    if waveNumber <= 1 then
        return 1.0
    elseif waveNumber <= 3 then
        return 1.2
    else
        return 1.5
    end
end

-- Retourne true si l'ennemi doit accélérer
-- (quand sa vie est inférieure à 30%)
function shouldRush(currentHp, maxHp)
    return (currentHp / maxHp) < 0.3
end

-- Calcule le bonus de vitesse en cas de rush
function getRushSpeedBonus(baseSpeed)
    return baseSpeed * 1.4
end

-- Log simple pour le debug
function onEnemyMove(entityId, x, y)
    -- print("Enemy #" .. entityId .. " at (" .. x .. ", " .. y .. ")")
end