package com.gcunha.pagminecash.key;

import com.gcunha.pagminecash.PagMineCash;
import com.gcunha.pagminecash.bank.Bank;
import com.gcunha.pagminecash.utils.RandomString;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Random;

public class KeyManager {

    private final HashMap<String, Key> keys;
    private final PagMineCash plugin;

    public KeyManager() {
        this.plugin = PagMineCash.getInstance();
        this.keys = new HashMap<>();
    }

    public void useKey(Player player, String key){
        Bank playerBank = plugin.getBankManager().getBank(player.getUniqueId());
        playerBank.addCash(plugin.getKeyManager().getKey(key).getCash());

        deleteKey(key);
    }

    public Boolean existKey(String key){
        return keys.containsKey(key);
    }
    
    public Key getKey(String key){
        return keys.get(key);
    }
    
    public Key createKey(int cash){
        //TODO: ADICIONA NO BANCO DE DADOS
        String randomKey = RandomString.generateRandomString(8);

        Key key = new Key(randomKey,cash);

        this.keys.put(randomKey,key);

        return key;
    }

    public Key addKey(String key, int cash){
        if(existKey(key)) return getKey(key);

        Key keyObject = new Key(key,cash);
        this.keys.put(key, keyObject);
        return keyObject;
    }

    public void deleteKey(String key){
        if(existKey(key)) this.keys.remove(key);
        //TODO: APAGAR NO BANCO DE DADOS
    }

    public HashMap<String, Key> getKeys() {
        return keys;
    }
}
