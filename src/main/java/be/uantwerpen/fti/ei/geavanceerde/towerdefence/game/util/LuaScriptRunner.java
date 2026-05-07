package be.uantwerpen.fti.ei.geavanceerde.towerdefence.game.util;

import org.luaj.vm2.*;
import org.luaj.vm2.lib.jse.JsePlatform;

import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Exécute des scripts Lua depuis Java via LuaJ.
 * Permet de modifier le comportement du jeu sans recompiler.
 */
public class LuaScriptRunner {

    private final Globals globals;

    public LuaScriptRunner(String scriptPath) {
        globals = JsePlatform.standardGlobals();
        loadScript(scriptPath);
    }

    private void loadScript(String path) {
        try (InputStream in = getClass()
                .getClassLoader()
                .getResourceAsStream(path)) {
            if (in != null) {
                globals.load(new InputStreamReader(in), path).call();
            }
        } catch (Exception e) {
            System.err.println("Erreur chargement script Lua : "
                    + e.getMessage());
        }
    }

    /**
     * Appelle une fonction Lua avec des arguments doubles.
     * Retourne 0.0 si la fonction n'existe pas ou plante.
     */
    public double callDouble(String functionName, double... args) {
        try {
            LuaValue func = globals.get(functionName);
            if (func.isnil()) return 0.0;

            LuaValue[] luaArgs = new LuaValue[args.length];
            for (int i = 0; i < args.length; i++) {
                luaArgs[i] = LuaValue.valueOf(args[i]);
            }
            return func.invoke(luaArgs).tojstring(1) != null
                    ? func.call(luaArgs.length > 0 ? luaArgs[0] : LuaValue.NIL)
                      .todouble()
                    : 0.0;
        } catch (Exception e) {
            return 0.0;
        }
    }

    /**
     * Appelle une fonction Lua qui retourne un boolean.
     */
    public boolean callBoolean(String functionName, double... args) {
        try {
            LuaValue func = globals.get(functionName);
            if (func.isnil()) return false;

            LuaValue[] luaArgs = new LuaValue[args.length];
            for (int i = 0; i < args.length; i++) {
                luaArgs[i] = LuaValue.valueOf(args[i]);
            }
            return func.invoke(luaArgs).toboolean(1);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Appelle une fonction Lua void (pas de retour).
     */
    public void call(String functionName, Object... args) {
        try {
            LuaValue func = globals.get(functionName);
            if (func.isnil()) return;

            LuaValue[] luaArgs = new LuaValue[args.length];
            for (int i = 0; i < args.length; i++) {
                if (args[i] instanceof Double d)
                    luaArgs[i] = LuaValue.valueOf(d);
                else if (args[i] instanceof Integer n)
                    luaArgs[i] = LuaValue.valueOf(n);
                else
                    luaArgs[i] = LuaValue.valueOf(args[i].toString());
            }
            func.invoke(luaArgs);
        } catch (Exception e) {
            System.err.println("Erreur appel Lua " + functionName
                    + " : " + e.getMessage());
        }
    }
}