package net.pistonmaster.pistonmute.utils;

import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.net.URL;

@SuppressWarnings("deprecation")
public class MojangUtil {

    private static final String UUID_URL = "https://api.mojang.com/users/profiles/minecraft/%s";
    private static final String NAME_URL = "https://api.mojang.com/user/profiles/%s/names";

    @SneakyThrows
    public static String getUUID(String name) {
        String UUIDJson = IOUtils.toString(new URL(String.format(UUID_URL, name)));

        if(UUIDJson.isEmpty()) {
            return "invalid name";
        }

        JSONObject UUIDObject = (JSONObject) JSONValue.parseWithException(UUIDJson);

        return UUIDObject.get("id").toString();
    }

    @SneakyThrows
    public static String getName(String uuid) {
        String nameJson = IOUtils.toString(new URL(String.format(NAME_URL, uuid.replace("-", ""))));

        JSONArray nameValue = (JSONArray) JSONValue.parseWithException(nameJson);

        String playerSlot = nameValue.get(nameValue.size()-1).toString();

        JSONObject nameObject = (JSONObject) JSONValue.parseWithException(playerSlot);

        return nameObject.get("name").toString();
    }
}
